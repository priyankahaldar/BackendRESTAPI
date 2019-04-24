package com.intuit.cg.backendtechassessment.scheduler;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.intuit.cg.backendtechassessment.service.ProcessWinningBidService;
/**
 * Scheduled method that will run with a fixed delay *
 * This class another a service that will check for projects that have passed 
 *  the bidding deadline and assign a bid as a winner
 */
@Component
public class ProjectBidApprovalScheduler {	
		
	final Logger LOG = LoggerFactory.getLogger(ProjectBidApprovalScheduler.class);

	@Autowired
	ProcessWinningBidService processWinningBidService;
			
	//Scheduled to run after 1 min of every run...
	@Scheduled(fixedDelay = 60000)/// Needs to be tweaked in prod/prod-like environments depending on load/performance factors
	public void fixedRateSch(){ 	
		
		LOG.debug("Running JOB @: {}",DateTime.now().toDate()); 				
		processWinningBidService.processProjectsToAssignWinner();
	}	
}
