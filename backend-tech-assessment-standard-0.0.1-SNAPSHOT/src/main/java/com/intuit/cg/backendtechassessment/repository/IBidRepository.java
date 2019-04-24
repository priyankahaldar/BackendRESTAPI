package com.intuit.cg.backendtechassessment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.intuit.cg.backendtechassessment.model.Bid;

@Repository
public interface IBidRepository extends CrudRepository<Bid, Long>{
	
}
