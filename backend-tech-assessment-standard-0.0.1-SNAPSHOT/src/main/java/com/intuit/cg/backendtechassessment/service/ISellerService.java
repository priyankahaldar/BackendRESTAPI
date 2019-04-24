package com.intuit.cg.backendtechassessment.service;

import java.util.List;

import com.intuit.cg.backendtechassessment.model.Seller;

public interface ISellerService {

	public List<Seller> findAll();

	public Seller findById(Long id);
	
	public Seller saveOrUpdate(Seller seller);

}
