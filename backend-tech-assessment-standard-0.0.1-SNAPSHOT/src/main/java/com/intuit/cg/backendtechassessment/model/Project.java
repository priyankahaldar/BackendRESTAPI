package com.intuit.cg.backendtechassessment.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Entity class for Projects that will be posted by Sellers, 
 * with attributes project description, maximum budget and deadline for submitting bids
 **/
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "projectId")
/**
 * Added @JsonIdentityInfo for the Infinite Recursion issue with
 * bidirectional-relationships when we serialize Java objects
 **/
public class Project implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long projectId;

	@NotBlank(message = "Project Description is required")
	private String projectDescription;

	@NotNull(message = "Maximum Budget Value is required")
	private Double maxBudget;
	
	@NotNull(message = "Project Deadline for Bids is required")
	private Date deadlineForBids;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "seller_id")
	private Seller seller;

	@OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
	@javax.persistence.OrderBy("bidAmount") // to get the lowest bid in the first element of the list
	private List<Bid> bids = new ArrayList<Bid>();

	@OneToOne(mappedBy = "project")
	private WinningBid winningBid;

	public Project() {
	}

	public Project(Long projectId, Seller seller, String projectDescription, Double maxBudget,
			Date deadlineForBids) {
		this.projectId = projectId;
		this.seller = seller;
		this.projectDescription = projectDescription;
		this.maxBudget = maxBudget;
		this.deadlineForBids = deadlineForBids;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public Long getProjectId() {
		return projectId;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public Double getMaxBudget() {
		return maxBudget;
	}
	
	public Bid getLowestBid() {
		return bids != null & bids.size() > 0 ? bids.get(0) : null;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public List<Bid> getBids() {
		return bids;
	}

	public void setBids(List<Bid> bids) {
		this.bids = bids;
	}

	public void setMaxBudget(double maxBudget) {
		this.maxBudget = maxBudget;
	}
	
	public WinningBid getWinningBid() {
		return winningBid;
	}

	public void setWinningBid(WinningBid winningBid) {
		this.winningBid = winningBid;
	}
	
	public Date getDeadlineForBids() {
		return deadlineForBids;
	}

	public void setDeadlineForBids(Date deadlineForBids) {
		this.deadlineForBids = deadlineForBids;
	}

	public void setMaxBudget(Double maxBudget) {
		this.maxBudget = maxBudget;
	}

	public String toString() {
		return "projectDescription:" + projectDescription + ", maxBudget:" + maxBudget + ",deadlineForBids:"
				+ deadlineForBids + ", num of bids:" +bids.size();
	}
		
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;
        Project that = (Project) o;
        return getProjectId().equals(that.getProjectId());        		
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getProjectId(), getSeller());
    }
}
