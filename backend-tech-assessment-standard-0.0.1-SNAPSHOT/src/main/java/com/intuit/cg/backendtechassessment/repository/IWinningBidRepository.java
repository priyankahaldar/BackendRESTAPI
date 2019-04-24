package com.intuit.cg.backendtechassessment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.intuit.cg.backendtechassessment.model.WinningBid;

@Repository
public interface IWinningBidRepository extends CrudRepository<WinningBid, Long>{	
	
}