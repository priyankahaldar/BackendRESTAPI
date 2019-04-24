package com.intuit.cg.backendtechassessment.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "projectId")
public class ProjectDTO {
	
	private Long projectId;
	private String projectDescription;
	private BidDTO lowestBid;
	//private String sellerName;	
	private Date deadlineForBids;
	private Double maxBudget;
	
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getProjectDescription() {
		return projectDescription;
	}
	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}
	
	public Double getMaxBudget() {
		return maxBudget;
	}
	public BidDTO getLowestBid() {
		return lowestBid;
	}
	public void setLowestBid(BidDTO lowestBid) {
		this.lowestBid = lowestBid;
	}
	public void setMaxBudget(Double maxBudget) {
		this.maxBudget = maxBudget;
	}

	public Date getDeadlineForBids() {
		return deadlineForBids;
	}
	public void setDeadlineForBids(Date deadlineForBids) {
		this.deadlineForBids = deadlineForBids;
	}	
}
