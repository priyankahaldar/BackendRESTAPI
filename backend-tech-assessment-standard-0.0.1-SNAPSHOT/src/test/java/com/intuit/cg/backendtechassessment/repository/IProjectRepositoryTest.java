package com.intuit.cg.backendtechassessment.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.intuit.cg.backendtechassessment.model.Project;
import com.intuit.cg.backendtechassessment.model.Seller;

@RunWith(SpringRunner.class)
@DataJpaTest
public class IProjectRepositoryTest {

	@Autowired
	private TestEntityManager entityManager; 

	@Autowired
	private IProjectRepository projectRepository;

	@Test
	public void findAll_ReturnProject() {

		final Seller seller = createSeller();
		final Project project = createProject(seller, "Test Desc", 500.0, DateTime.now().plusDays(1).toDate());		
		
		final List<Project> projectList  = (List<Project>) projectRepository.findAll();
		
		assertNotNull(projectList);
		assertEquals(1, projectList.size());
		
		final Project projectReturnedByAll = projectList.get(0);
		
		assertEquals(project.getProjectDescription(), projectReturnedByAll.getProjectDescription());
		assertEquals(project.getDeadlineForBids(), projectReturnedByAll.getDeadlineForBids());
		assertEquals(project.getSeller().getFirstName(), projectReturnedByAll.getSeller().getFirstName());
		assertEquals(project.getSeller().getLastName(), projectReturnedByAll.getSeller().getLastName());	
				
		final Optional<Project> found =projectRepository.findById(projectReturnedByAll.getProjectId());

		if (found.isPresent()) {
			final Project foundProjectById = found.get();			
			assertEquals(projectReturnedByAll, foundProjectById);	
			
		} else {
			Assert.fail("Failed to find the project by Id");
		}
	}
	
	@Test
	public void fetchProjectsByDeadLine_returnsProject() {

		final Seller seller = createSeller();		
		final Project project = new Project(null, seller, "Test Desc", 500.0, DateTime.now().minusDays(1).toDate());

		entityManager.persist(project);
		entityManager.flush();

		final List<Project> projectList = projectRepository.fetchProjectsByDeadLine(DateTime.now().toDate());

		assertNotNull(projectList);
		assertEquals(1, projectList.size());
		
		final Project projectReturned = projectList.get(0);
		
		assertEquals(project.getProjectDescription(), projectReturned.getProjectDescription());
		assertEquals(project.getDeadlineForBids(), projectReturned.getDeadlineForBids());
		assertEquals(project.getSeller().getFirstName(), projectReturned.getSeller().getFirstName());
		assertEquals(project.getSeller().getLastName(), projectReturned.getSeller().getLastName());				
	}
	
	Project createProject(final Seller seller,final String projectDesc, final Double maxBudget,final Date deadlineForBids){		
		
		final Project project = new Project(null, seller, projectDesc, maxBudget, deadlineForBids);		
		persistData(project);	
		
		return project;
	}
	
	Seller createSeller(){		
		final Seller seller = new Seller(null, "Sam", "Smith");
		persistData(seller);
		
		return seller;
	}
	
	void persistData(final Object entity){
		
		entityManager.persist(entity);
		entityManager.flush();		
	}
}