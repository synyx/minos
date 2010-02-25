package org.synyx.minos.skillz.web.validation;

import static org.mockito.Mockito.when;
import static org.synyx.minos.core.web.WebTestUtils.assertContainsFieldErrorWithCode;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.synyx.minos.skillz.domain.Project;
import org.synyx.minos.skillz.service.SkillManagement;

/**
 * Unit test for {@code ProjectValidator}.
 * 
 * @author Michael Herbold - herbold@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class ProjectValidatorUnitTest {

	private ProjectValidator validator;
	@Mock
	private SkillManagement skillManagement;

	private Errors errors;

	private Project project;

	private String name = "ProjectName";
	private String description = "ProjectDescription";
	private String customer = "ProjectCustomer";
	private String industry = "ProjectIndustry";
	private String platform = "ProjectPlatform";

	/**
	 * Sets up mocks, configures the validator and creates a default {@code
	 * Project} instance.
	 */
	@Before
	public void setUp() {

		validator = new ProjectValidator(skillManagement);

		project = BeanUtils.instantiateClass(Project.class);
		project.setId(0L);
		project.setName(name);
		project.setDescription(description);
		project.setCustomer(customer);
		project.setIndustry(industry);
		project.setPlatform(platform);

	}

	/**
	 * Tests that the validator does not raise errors on a valid project.
	 */
	@Test
	public void passesValidProject() {

		prepareErrorsAndExecute();

		skillManagement.save(project);
		Assert.assertFalse("Found errors: " + errors, errors.hasErrors());
	}

	/**
	 * Asserts that the validator rejects projects with an empty name.
	 */
	@Test
	public void rejectEmptyProjectName() {

		// change name
		project.setName("");

		prepareErrorsAndExecute();

		skillManagement.save(project);
		Assert.assertTrue("Found errors: " + errors, errors.hasErrors());
		assertContainsFieldErrorWithCode(errors, "name",
				ProjectValidator.PROJECT_NAME_EMPTY);
	}

	/**
	 * Asserts that the validator rejects projects with an invalid name.
	 */
	@Test
	public void rejectInvalidProjectName() {

		// change name
		project.setName(ProjectValidator.INVALID_NAME_VALUE);

		prepareErrorsAndExecute();

		skillManagement.save(project);
		Assert.assertTrue("Found errors: " + errors, errors.hasErrors());
		assertContainsFieldErrorWithCode(errors, "name",
				ProjectValidator.PROJECT_NAME_INVALID);
	}

	/**
	 * Checks, that a new project with an existing projectname is rejected.
	 */
	@Test
	public void rejectsNewProjectsWithExistingProjectsname() {

		// Mark new project
		project.setId(null);

		// Expect lookup for project name
		when(skillManagement.getProject(name)).thenReturn(new Project(name));

		prepareErrorsAndExecute();

		skillManagement.save(project);
		assertContainsFieldErrorWithCode(errors, "name",
				ProjectValidator.PROJECT_NAME_ALREADY_EXISTS);
	}

	/**
	 * Creates a new {@code Errors} instance that binds the {@code Project}
	 * instance and triggers validation.
	 */
	private void prepareErrorsAndExecute() {

		errors = new BeanPropertyBindingResult(project, "project");
		validator.validate(project, errors);
	}

}
