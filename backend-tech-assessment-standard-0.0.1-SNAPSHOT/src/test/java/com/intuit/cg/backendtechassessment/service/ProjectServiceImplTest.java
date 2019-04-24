package com.intuit.cg.backendtechassessment.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Optional;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.intuit.cg.backendtechassessment.exception.InvalidInputException;
import com.intuit.cg.backendtechassessment.model.Project;
import com.intuit.cg.backendtechassessment.model.Seller;
import com.intuit.cg.backendtechassessment.repository.IProjectRepository;
import com.intuit.cg.backendtechassessment.repository.IWinningBidRepository;

@RunWith(SpringRunner.class)
public class ProjectServiceImplTest {

	@MockBean
	IProjectRepository projectRepository;

	@MockBean
	ISellerService sellerService;

	@MockBean
	IWinningBidRepository winningRepository;

	@TestConfiguration
	static class ProjectServiceImplTestContextConfiguration {

		@Bean
		public IProjectService projectService() {
			return Mockito.spy(ProjectServiceImpl.class);
		}
	}

	@Autowired
	IProjectService projectService;	

	@Test
	public void findProjectById_ProjectReturned() {
		Long projectId = 100L;

		Project projectExpected = generateProjectEntity(projectId);
		Mockito.doReturn(Optional.of(projectExpected)).when(projectRepository).findById(projectId);

		final Project projectReturned = projectService.findProjectById(projectId);
		assertEquals(projectExpected, projectReturned);

		Mockito.verify(projectRepository, Mockito.times(1)).findById(projectId);
	}

	@Test
	public void findProjectById_ProjectNotFound() {
		Long projectId = 100L;
		Mockito.doReturn(Optional.empty()).when(projectRepository).findById(projectId);

		final Project projectReturned = projectService.findProjectById(projectId);
		assertNull(projectReturned);

		Mockito.verify(projectRepository, Mockito.times(1)).findById(projectId);
	}

	@Test
	public void validateProject_WhenProjectReturnedIsNull() {

		Long projectId = 1L;
		try {
			final Project project = generateProjectEntity(projectId);
			Mockito.doReturn(null).when(projectService).findProjectById(projectId);

			projectService.validateProject(project);
			fail("Should have thrown InvalidInputException");
		} catch (final InvalidInputException iie) {
			assertEquals("Project not found", iie.getMessage());			
		}
	}

	@Test
	public void validateProject_WhenProjectIsClosedForBidding() {

		final Long projectId = 1L;
		try {

			final Project project = generateProjectEntity(projectId);

			Mockito.doReturn(project).when(projectService).findProjectById(projectId);
			Mockito.doReturn(false).when(projectService).checkIfProjectIsOpenForBidding(project);

			projectService.validateProject(project);
			fail("Should have thrown InvalidInputException");
		} catch (final InvalidInputException iie) {
			assertEquals("Project is closed for bidding", iie.getMessage());

			Mockito.verify(projectService, Mockito.times(1)).findProjectById(projectId);
		}
	}

	@Test
	public void validateProject_WhenInputProjectIdIsNull() {

		try {
			final Project project = new Project();

			projectService.validateProject(project);
			fail("Should have thrown InvalidInputException");
		} catch (final InvalidInputException iie) {
			assertEquals("Invalid Project Input", iie.getMessage());
		}
	}

	@Test
	public void createProject_WhenSellerIsInvalid() throws Exception {

		final Long sellerId = 1L;
		final Long projectId = 2L;

		try {
			final Project project = generateProjectEntity(projectId);

			final Seller seller = new Seller(sellerId, "Sam", "Smith");
			project.setSeller(seller);

			Mockito.doReturn(null).when(sellerService).findById(seller.getSellerId());

			projectService.createProject(project);
			fail("Should have thrown InvalidInputException");
		} catch (final InvalidInputException iie) {
			assertEquals("Invalid Seller Information", iie.getMessage());

			Mockito.verify(sellerService, Mockito.times(1)).findById(sellerId);
		}
	}

	@Test
	public void createProject_WhenProjectIsSaved() throws Exception {
		final Long projectId = 1L;
		final Project project = generateProjectEntity(projectId);

		final Seller seller = new Seller(1L, "Sam", "Smith");
		project.setSeller(seller);

		Mockito.doReturn(seller).when(sellerService).findById(seller.getSellerId());
		Mockito.doReturn(project).when(projectRepository).save(project);

		Project projectSaved = projectService.createProject(project);

		assertEquals(project, projectSaved);

		Mockito.verify(projectRepository, Mockito.times(1)).save(project);
		Mockito.verify(sellerService, Mockito.times(1)).findById(seller.getSellerId());
	}
		
	@Test
	public void checkIfProjectIsOpenForBidding_DateIsInThePast(){
		
		final Project project = new Project();
		project.setProjectId(1L);
		project.setProjectDescription("Test Project");
		project.setDeadlineForBids(DateTime.now().minusDays(1).toDate());
		
		assertFalse(projectService.checkIfProjectIsOpenForBidding(project));
	}	
	
	
	Project generateProjectEntity(Long projectId) {

		final Project project = new Project();
		project.setProjectId(projectId);
		project.setProjectDescription("Test Project");
		project.setDeadlineForBids(DateTime.now().plusDays(1).toDate());
		project.setWinningBid(null);
		return project;
	}
	
}
