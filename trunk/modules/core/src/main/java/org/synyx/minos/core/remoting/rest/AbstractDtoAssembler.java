package org.synyx.minos.core.remoting.rest;

import static org.synyx.minos.umt.web.UmtUrls.*;

import javax.servlet.http.HttpServletRequest;

import org.synyx.hades.domain.Persistable;
import org.synyx.hades.domain.auditing.AbstractAuditable;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.remoting.rest.dto.AbstractEntityDto;
import org.synyx.minos.core.remoting.rest.dto.IdentifyableDto;
import org.synyx.minos.core.web.UrlUtils;
import org.synyx.minos.umt.service.UserManagement;


/**
 * Abstract base class to implement DTO assemblers. Provides basic functionality to map core domain abstraction classes
 * to DTOs and vice versa.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class AbstractDtoAssembler {

    private final UserManagement userManagement;


    /**
     * Creates a new {@link AbstractDtoAssembler} with acces to the {@link UserManagement}.
     * 
     * @param userManagement
     */
    protected AbstractDtoAssembler(UserManagement userManagement) {

        this.userManagement = userManagement;
    }


    /**
     * Creates an {@link IdentifyableDto} for any {@link Persistable}. Useful to create reference objects by omitting to
     * render the detailed object state.
     * 
     * @param persistable
     * @return
     */
    public IdentifyableDto toIdentifyable(Persistable<Long> persistable) {

        return toIdentifyable(persistable, null, null);
    }


    /**
     * Creates an {@link IdentifyableDto} for any {@link Persistable} additionally adding a link to the resource. If you
     * provide {@code null} for {@code moduleUrl} or {@code request} no link will be created.
     * 
     * @param persistable
     * @param moduleUrls
     * @param request
     * @return
     */
    public IdentifyableDto toIdentifyable(Persistable<Long> persistable, String moduleUrls, HttpServletRequest request) {

        if (null == persistable) {
            return null;
        }

        IdentifyableDto identifyable = new IdentifyableDto();
        identifyable.setId(persistable.getId());

        if (null != moduleUrls && null != request) {
            identifyable.setHref(UrlUtils.toUrl(moduleUrls, request, persistable));
        }

        return identifyable;
    }


    /**
     * Maps the basic properties of {@link AbstractAuditable} into the given {@code dto}. This will map created and
     * modified date of the {@code entity}.
     * 
     * @param <T>
     * @param dto
     * @param entity
     * @return
     */
    public <T extends AbstractEntityDto> T mapBasicProperties(T dto, AbstractAuditable<User, Long> entity) {

        return mapBasicProperties(entity, dto, null);
    }


    /**
     * Maps the basic properties of {@link AbstractAuditable} into the given {@code dto} also mapping the link to the
     * creation and last modification user.
     * 
     * @param entity
     * @param dto
     * @param request
     * @param <T>
     * @return
     */
    public <T extends AbstractEntityDto> T mapBasicProperties(AbstractAuditable<User, Long> entity, T dto,
            HttpServletRequest request) {

        return mapBasicProperties(entity, dto, request, null);
    }


    /**
     * Maps the basic properties of {@link AbstractAuditable} into the given {@code dto}. Works pretty much like
     * {@link #mapBasicProperties(AbstractAuditable, AbstractEntityDto, HttpServletRequest, String)} but additionally
     * creates the self href if a module URL is provided.
     * 
     * @param <T>
     * @param entity
     * @param dto
     * @param request
     * @param moduleUrl
     * @return
     */
    public <T extends AbstractEntityDto> T mapBasicProperties(AbstractAuditable<User, Long> entity, T dto,
            HttpServletRequest request, String moduleUrl) {

        if (null == dto || null == entity) {
            return dto;
        }

        if (null != moduleUrl) {
            dto.setHref(UrlUtils.toUrl(moduleUrl, request, entity));
        }

        dto.setId(entity.getId());

        dto.setCreated(entity.getCreatedDate());
        dto.setCreatedBy(toIdentifyable(entity.getCreatedBy(), USER, request));

        dto.setLastModified(entity.getLastModifiedDate());
        dto.setModifiedBy(toIdentifyable(entity.getLastModifiedBy(), USER, request));

        return dto;
    }


    /**
     * Maps the basic properties of an {@link AbstractAuditable} from the DTO. Resolves user instances for modifying and
     * creating user of it.
     * 
     * @param dto The DTO to extract the properties from
     * @param entity The entity to map the DTOs properties to. Can be prepopulated to update an existing entity or an
     *            empty one to create a new entity. Must not be {@code null}.
     * @param mapId whether to map the DTOs id property or not. Should be {@code false} if you intend to create a new
     *            entity, as the DTO might contain an id, which would result in updating an already existing one.
     * @param <T> The actual entity type
     * @return The mappend entity
     * @throws IllegalArgumentException if the given entity is {@code null}.
     */
    public <T extends AbstractAuditable<User, Long>> T mapBasicProperties(AbstractEntityDto dto, T entity, boolean mapId)
            throws IllegalArgumentException {

        if (null == entity) {
            throw new IllegalArgumentException("Entity must not be null!");
        }

        if (mapId) {
            entity.setId(dto.getId());
        }

        entity.setCreated(dto.getCreated());
        entity.setCreatedBy(toDomainObject(dto.getCreatedBy()));
        entity.setLastModified(dto.getLastModified());
        entity.setLastModifiedBy(toDomainObject(dto.getModifiedBy()));

        return entity;
    }


    /**
     * Looks up a user defined by the given identifyable. Useful to lookup domain objects from any basit DTO instance
     * but mostly to extract auditing entities.
     * 
     * @param identifyable
     * @return
     */
    public User toDomainObject(IdentifyableDto identifyable) {

        if (null == identifyable) {
            return null;
        }

        Long id = identifyable.getId();

        return null == id ? null : userManagement.getUser(id);
    }
}
