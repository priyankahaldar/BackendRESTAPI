package com.intuit.cg.backendtechassessment.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.intuit.cg.backendtechassessment.model.Project;

/** This class will check for projects that have passed 
*   the bidding deadline and assign a bid as a winner
**/
@Service
public class ProcessWinningBidService implements IProjectWinningBidService {

	final Logger LOG = LoggerFactory.getLogger(ProcessWinningBidService.class);

	@Autowired
	IProjectService projectService;
		
	List<Project> fetchProjectsThatAreClosedForBidding(){		
		return projectService.findClosedProjectsWithBidsToApprove();	
	}
	
	@Transactional
	@Override
	public void processProjectsToAssignWinner(){		
			
		final List<Project> projectList  = fetchProjectsThatAreClosedForBidding();		
		LOG.debug("Total projects to process: {}",projectList.size());
		
		for(final Project tempProject:projectList){
			
			try{
				LOG.debug("Processing : {}",tempProject.getProjectDescription());						
				projectService.setWinningProjectBid(tempProject);		
			}
			catch(Exception e){
				LOG.error("Error Assigning WInning Bid for Project:" + tempProject.getProjectDescription() , e);
			}
		}
	}	
}
