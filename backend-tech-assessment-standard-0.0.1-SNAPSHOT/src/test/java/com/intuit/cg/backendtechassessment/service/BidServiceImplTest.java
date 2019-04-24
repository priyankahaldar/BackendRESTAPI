package com.intuit.cg.backendtechassessment.service;

import static org.junit.Assert.assertEquals;

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
import com.intuit.cg.backendtechassessment.model.Bid;
import com.intuit.cg.backendtechassessment.model.Buyer;
import com.intuit.cg.backendtechassessment.model.Project;
import com.intuit.cg.backendtechassessment.repository.IBidRepository;

@RunWith(SpringRunner.class)
public class BidServiceImplTest {

	@MockBean
	IBidRepository bidRepository;
	
	@MockBean
	IProjectService projectService;
	
	@MockBean
	IBuyerService buyerService;	

	@TestConfiguration
	static class BidServiceImplTestContextConfiguration {

		@Bean
		public IBidService bidService() {
			return Mockito.spy(BidServiceImpl.class);
		}
	}

	@Autowired
	IBidService bidService;
	
	@Test(expected=InvalidInputException.class)
	public void validateBidInput_InvalidProject() throws Exception {
	
		final Long projectId = 100L;		
		final Project project = generateProjectEntity(projectId);
		
		final Bid bid = new Bid();
		bid.setProject(project);
		
		Mockito.doThrow(new InvalidInputException("Project not found")).when(projectService).validateProject(project);

		bidService.save(bid);

		Mockito.verify(projectService, Mockito.times(1)).validateProject(project);
		Mockito.verify(bidRepository, Mockito.never()).save(bid);
	}
	
	@Test(expected=InvalidInputException.class)
	public void validateBidInput_InvalidBuyer() throws Exception {
	
		final Long projectId = 100L;		
		final Project project = generateProjectEntity(projectId);
		
		final Bid bid = new Bid();
		bid.setProject(project);
		
		final Buyer buyer = new Buyer();
		bid.setBuyer(buyer);
		
		Mockito.doReturn(project).when(projectService).validateProject(project);
		Mockito.doThrow(new InvalidInputException("Buyer not found")).when(buyerService).validateBuyer(buyer);

		bidService.save(bid);

		Mockito.verify(projectService, Mockito.times(1)).validateProject(project);
		Mockito.verify(buyerService, Mockito.times(1)).validateBuyer(buyer);
		Mockito.verify(bidRepository, Mockito.never()).save(bid);
	}
		
	@Test(expected=InvalidInputException.class)
	public void validateBidInput_InvalidBidAmount() throws Exception {
	
		final Long projectId = 100L;
		final Double bidAmount = 600.0;
		final Project project = generateProjectEntity(projectId);

		final Buyer buyer = new Buyer();
		final Bid bid = new Bid(project, buyer, bidAmount);		
		
		Mockito.doReturn(project).when(projectService).validateProject(project);
		Mockito.doReturn(buyer).when(buyerService).validateBuyer(buyer);

		bidService.save(bid);

		Mockito.verify(projectService, Mockito.times(1)).validateProject(project);
		Mockito.verify(buyerService, Mockito.times(1)).validateBuyer(buyer);
		Mockito.verify(bidRepository, Mockito.never()).save(bid);
	}
	
	@Test
	public void validateBidInput_BidIsAdded() throws Exception {
	
		final Long projectId = 100L;		
		final Project project = generateProjectEntity(projectId);		
		final Buyer buyer = new Buyer();

		final Bid bid = new Bid(project, buyer, 400.0);			
		Bid bidExpected = new Bid(project, buyer, 400.0);		
		
		Mockito.doReturn(project).when(projectService).validateProject(project);
		Mockito.doReturn(buyer).when(buyerService).validateBuyer(buyer);
		Mockito.doReturn(bidExpected).when(bidRepository).save(bid);

		final Bid bidSaved = bidService.save(bid);
		
		assertEquals(bidExpected, bidSaved);

		Mockito.verify(projectService, Mockito.times(1)).validateProject(project);
		Mockito.verify(buyerService, Mockito.times(1)).validateBuyer(buyer);
		Mockito.verify(bidRepository, Mockito.times(1)).save(bid);
	}
	
	Project generateProjectEntity(final Long projectId) {

		final Project project = new Project();
		project.setProjectId(projectId);
		project.setProjectDescription("Test Project");
		project.setDeadlineForBids(DateTime.now().plusDays(1).toDate());
		project.setWinningBid(null);
		project.setMaxBudget(500.0);
		return project;
	}	
}
