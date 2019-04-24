package com.intuit.cg.backendtechassessment.service;

import java.util.List;

import com.intuit.cg.backendtechassessment.exception.InvalidInputException;
import com.intuit.cg.backendtechassessment.model.Buyer;

public interface IBuyerService {

	public List<Buyer> findAll();

	public Buyer findById(Long id);

	public Buyer saveOrUpdate(Buyer buyer);

	public Buyer validateBuyer(Buyer buyer) throws InvalidInputException;
}
