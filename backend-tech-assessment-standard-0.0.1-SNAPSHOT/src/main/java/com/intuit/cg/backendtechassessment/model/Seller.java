package com.intuit.cg.backendtechassessment.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
/**
 * Entity class for Seller
 **/
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "sellerId")
public class Seller implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long sellerId;
	
	@NotBlank(message = "Seller First Name is required")
	private String firstName;
	
	@NotBlank(message = "Seller Last Name is required")
	private String lastName;
	
	@OneToMany(mappedBy = "seller", fetch= FetchType.LAZY)
	private List<Project> projects = new ArrayList<Project>();

	public Seller(){}
	
	public Seller(Long sellerId, String firstName, String lastName){
		this.sellerId = sellerId;
		this.firstName = firstName;
		this.lastName = lastName;	
	}
	
	public Long getSellerId() {
		return sellerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public List<Project> getProjects() {
		return projects;
	}
		
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public String toString() {
		return "Seller Id:" + sellerId + ", firstName:" + firstName + ",lastName:" + lastName;
	}
	
}
