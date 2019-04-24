package com.intuit.cg.backendtechassessment.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Entity class for Buyers who will place bids on Projects 
 * For now considering only a few attributes like id, first name, last name
 **/
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="buyerId")
public class Buyer implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long buyerId;
	
	@NotBlank(message = "Buyer First Name is required")
	private String firstName;
	
	@NotBlank(message = "Buyer Last Name is required")
	private String lastName;
	
	@OneToMany(mappedBy="buyer")
	private List<Bid> bids;
	
	public Buyer(){}
	
	public Buyer(String firstName, String lastName){
		this.firstName = firstName;
		this.lastName = lastName;
		bids = new ArrayList<Bid>();
	}
	
	public Long getBuyerId() {
		return buyerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public List<Bid> getBids() {
		return bids;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setBids(List<Bid> bids) {
		this.bids = bids;
	}
	
}
