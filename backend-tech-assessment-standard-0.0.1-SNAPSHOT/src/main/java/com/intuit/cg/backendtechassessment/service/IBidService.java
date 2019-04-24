package com.intuit.cg.backendtechassessment.service;

import java.util.List;

import com.intuit.cg.backendtechassessment.model.Bid;

public interface IBidService{

	public Bid save(Bid bid) throws Exception;

	public List<Bid> findAll();
}
