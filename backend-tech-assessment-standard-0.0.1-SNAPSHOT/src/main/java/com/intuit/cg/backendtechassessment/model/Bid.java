package com.intuit.cg.backendtechassessment.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Entity class for Bids that will be placed by Buyers on a project 
 * */
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="bidId")
public class Bid implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long bidId;
	@NotNull
	private Double bidAmount;
	private Date creationDate;
	
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;
	
	@ManyToOne
	@JoinColumn(name = "buyer_id")
	private Buyer buyer;
		
	@OneToOne(mappedBy="bid", fetch = FetchType.LAZY)	
	private WinningBid winningBid;
	
	public Bid(){}
	
	public Bid(Project project, Buyer buyer, Double bidAmount){
		
		this.bidAmount = bidAmount;
		this.project = project;	
		this.buyer = buyer;
	}
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public Long getBidId() {
		return bidId;
	}

	public Double getBidAmount() {
		return bidAmount;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setBidId(Long bidId) {
		this.bidId = bidId;
	}

	public void setBidAmount(Double bidAmount) {
		this.bidAmount = bidAmount;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}	

	public WinningBid getWinningBid() {
		return winningBid;
	}

	public void setWinningBid(WinningBid winningBid) {
		this.winningBid = winningBid;
	}

	public String toString() {
		return "Project Id:" + (project!=null ? project.getProjectId(): null) 
				+ ", bidAmount:" + bidAmount + ",buyerId:" + (buyer!=null?buyer.getBuyerId():null);
	}
}
