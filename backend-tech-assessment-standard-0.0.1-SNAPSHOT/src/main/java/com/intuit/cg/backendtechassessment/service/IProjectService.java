package com.intuit.cg.backendtechassessment.service;

import java.util.List;

import com.intuit.cg.backendtechassessment.exception.InvalidInputException;
import com.intuit.cg.backendtechassessment.model.Project;
import com.intuit.cg.backendtechassessment.model.WinningBid;

public interface IProjectService {

	public List<Project> findAllProjects();

	public Project findProjectById(Long projectId);

	public Project createProject(Project project) throws Exception;

	public WinningBid setWinningProjectBid(Project project);

	public Project validateProject(Project project) throws InvalidInputException;

	public List<Project> findClosedProjectsWithBidsToApprove();

	public WinningBid findProjectWinningBid(Long projectId);
	
	public boolean checkIfProjectIsOpenForBidding(Project project);
}
