package com.intuit.cg.backendtechassessment.service;

import java.util.List;
import java.util.Optional;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.intuit.cg.backendtechassessment.exception.InvalidInputException;
import com.intuit.cg.backendtechassessment.model.Bid;
import com.intuit.cg.backendtechassessment.model.Project;
import com.intuit.cg.backendtechassessment.model.Seller;
import com.intuit.cg.backendtechassessment.model.WinningBid;
import com.intuit.cg.backendtechassessment.repository.IProjectRepository;
import com.intuit.cg.backendtechassessment.repository.IWinningBidRepository;

/**
 ** Service Layer for Project related operations like saving a project,
 * validating project attributes before saving, fetching one or more projects,
 * fetching projects that are close for bidding and assigning a winning bid to a
 * project
 */
@Service
public class ProjectServiceImpl implements IProjectService {

	final Logger LOG = LoggerFactory.getLogger(ProjectServiceImpl.class);

	@Autowired
	private IProjectRepository projectRepository;

	@Autowired
	private ISellerService sellerService;

	@Autowired
	private IWinningBidRepository winningRepository;

	@Override
	public List<Project> findAllProjects() {
		return (List<Project>) projectRepository.findAll();
	}

	@Override
	public WinningBid findProjectWinningBid(final Long projectId) {
		WinningBid winningBid=null;
		final Project project = findProjectById(projectId);
		if(project!=null){
			winningBid = project.getWinningBid();
		}		
		return winningBid;
	}

	public Project validateProject(final Project project) throws InvalidInputException {

		if (project == null || (project != null && project.getProjectId() == null)) {
			throw new InvalidInputException("Invalid Project Input");
		}

		final Project projectRetrieved = findProjectById(project.getProjectId());

		if (projectRetrieved == null) {
			throw new InvalidInputException("Project not found");
		}
		
		if (!checkIfProjectIsOpenForBidding(projectRetrieved)) {

			throw new InvalidInputException("Project is closed for bidding");
		}
		return projectRetrieved;
	}

	@Override
	public boolean checkIfProjectIsOpenForBidding(final Project project) {
		
		if (project!=null && project.getDeadlineForBids() != null ) { 
			
			final DateTime projectBidDeadline = new DateTime(project.getDeadlineForBids());
			//LOG.debug("projectBidDeadline :{}", projectBidDeadline.toDate());
			//LOG.debug("DateTime.Now :{}", DateTime.now().toDate());
			
			if( DateTime.now().isBefore(projectBidDeadline)){	
				return true;
			}
		}
		return false;
	}

	@Override
	public Project findProjectById(final Long projectId) {

		Project returnObj = null;
		final Optional<Project> project = projectRepository.findById(projectId);

		if (project.isPresent()) {
			returnObj = project.get();
		}
		//LOG.debug("Project fetched:"+project);
		return returnObj;
	}

	boolean checkIfProjectLowestBidNeedsToBeApproved(Project project) {

		if (!checkIfProjectIsOpenForBidding(project) && project.getWinningBid() == null) {
			return true;
		}
		return false;
	}

	@Override
	public Project createProject(final Project project) throws Exception {

		validateProjectAttributes(project);

		final Seller sellerObj = sellerService.findById(project.getSeller().getSellerId());

		if (sellerObj != null) {
			project.setSeller(sellerObj);
			return projectRepository.save(project);
		} else {
			throw new InvalidInputException("Invalid Seller Information");
		}
	}

	void validateProjectAttributes(final Project project) throws InvalidInputException {

		final DateTime bidDeadline = new DateTime(project.getDeadlineForBids());

		if (bidDeadline.isEqualNow() || bidDeadline.isBeforeNow()) {
			throw new InvalidInputException("Project deadline for adding bids should be in the future.");
		} 
		else if (project.getSeller() == null
				|| (project.getSeller() != null && project.getSeller().getSellerId() == null)) {

			throw new InvalidInputException("No Seller Input");
		}
	}

	@Override
	public List<Project> findClosedProjectsWithBidsToApprove() {
		return projectRepository.fetchProjectsByDeadLine(DateTime.now().toDate());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public WinningBid setWinningProjectBid(final Project project) {

		WinningBid winningBid = null;

		try { // approve Lowest Bid for Project
			final Bid lowestBid = project.getLowestBid();
			LOG.debug("lowestBid to close: {}",lowestBid);

			if (lowestBid != null) {
				winningBid = new WinningBid(project, lowestBid, DateTime.now().toDate());

				LOG.debug("winningBid: {}", winningBid);
				winningRepository.save(winningBid);

			} else {
				LOG.error("No bid to assign winner for projectId {}",project.getProjectId());
			}
		} catch (Exception e) {
			LOG.error("Error assigning Winning Bid for project:", e);
		}
		return winningBid;
	}
}
