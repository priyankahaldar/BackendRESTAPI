package com.intuit.cg.backendtechassessment.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
/**
 Entity class for Winning Bids for a Project
 **/
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "winningBidId")
public class WinningBid {

	@Id	
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long winningBidId;
	private Date approvalDate;
	
	@OneToOne
	@JoinColumn(name="bid_id")
	private Bid bid;
	
	public WinningBid(){}
	
	public WinningBid(Project project, Bid bid, Date approvalDate){
		this.project = project;	
		this.bid = bid;
		this.approvalDate = approvalDate;
	}
	
	@OneToOne
	@JoinColumn(name="project_id")
	private Project project;

	public Long getWinningBidId() {
		return winningBidId;
	}

	public void setWinningBidId(Long winningBidId) {
		this.winningBidId = winningBidId;
	}

	public Bid getBid() {
		return bid;
	}

	public void setBid(Bid bid) {
		this.bid = bid;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}
	
	public String toString() {
		return "Winning Proj Id:" + project + ", bid Id:" + bid.getBidId() + ",approvalDate:" + approvalDate;
	}
}
