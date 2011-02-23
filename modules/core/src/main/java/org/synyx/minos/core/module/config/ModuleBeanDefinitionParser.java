package org.synyx.minos.core.module.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;

import org.springframework.util.StringUtils;

import org.synyx.minos.core.Core;
import org.synyx.minos.core.message.ModuleMessageSourceImpl;
import static org.synyx.minos.core.module.config.BeanDefinitionParserUtils.addPropertyIfSet;
import org.synyx.minos.core.module.internal.MinosModule;
import org.synyx.minos.core.security.ModulePermissionAware;

import org.w3c.dom.Element;


/**
 * {@link BeanDefinitionParser} to parse Minos module element.
 *
 * @author  Oliver Gierke - gierke@synyx.de
 */
public class ModuleBeanDefinitionParser implements BeanDefinitionParser {

    private static final String AUTO_CONFIG = "auto-config";

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {

        new MinosModuleBeanDefinitionParser().parse(element, parserContext);

        String autoConfig = element.getAttribute(AUTO_CONFIG);

        if (Boolean.parseBoolean(autoConfig)) {
            parseAutoConfig(element, parserContext);
        }

        return null;
    }


    private void parseAutoConfig(Element element, ParserContext context) {

        String moduleId = element.getAttribute("id");

        new ModuleMessageSourceBeanDefinitionParser(moduleId).parse(element, context);
        new ModuleAwareBeanDefinitionParser(ModulePermissionAware.class, moduleId, true).parse(element, context);
    }

    /**
     * {@link BeanDefinitionParser} to declare {@link ModuleMessageSourceImpl} beans. Sets the
     * {@link ModuleMessageSourceImpl} to be lenient for the core module.
     *
     * @author  Oliver Gierke - gierke@synyx.de
     */
    private static class ModuleMessageSourceBeanDefinitionParser extends ModuleAwareBeanDefinitionParser {

        public ModuleMessageSourceBeanDefinitionParser(String moduleId) {

            super(ModuleMessageSourceImpl.class, moduleId, true);
        }

        @Override
        protected void parseAdditionalAttributes(Element element, BeanDefinitionBuilder builder) {

            if (Core.IDENTIFIER.equals(getModuleId())) {
                builder.addPropertyValue("lenient", true);
            }
        }
    }

    /**
     * {@link BeanDefinitionParser} to create a {@link MinosModule} instance.
     *
     * @author  Oliver Gierke - gierke@synyx.de
     */
    private static class MinosModuleBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

        private static final PropertyAttribute LIFECYCLE_REF = new PropertyAttribute("lifecycle-ref");
        private static final PropertyAttribute BASE_PACKAGE = new PropertyAttribute("base-package");
        private static final PropertyAttribute DEPENDS_ON = new PropertyAttribute("depends-on");

        @Override
        protected Class<?> getBeanClass(Element element) {

            return MinosModule.class;
        }


        @Override
        protected void doParse(Element element, BeanDefinitionBuilder builder) {

            builder.addConstructorArgValue(element.getAttribute(ID_ATTRIBUTE));

            addPropertyIfSet(builder, element, LIFECYCLE_REF);
            addPropertyIfSet(builder, element, BASE_PACKAGE);

            String dependsOn = element.getAttribute(DEPENDS_ON.toString());

            if (StringUtils.hasText(dependsOn)) {
                String[] parts = dependsOn.split(",");
                ManagedList<RuntimeBeanReference> references = new ManagedList<RuntimeBeanReference>(parts.length);

                for (String dependant : parts) {
                    references.add(new RuntimeBeanReference(dependant.trim()));
                }

                builder.addPropertyValue(DEPENDS_ON.asCamelCase(), references);
            }
        }
    }
}
