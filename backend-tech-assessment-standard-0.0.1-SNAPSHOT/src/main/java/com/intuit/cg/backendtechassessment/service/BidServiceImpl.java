package com.intuit.cg.backendtechassessment.service;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intuit.cg.backendtechassessment.exception.InvalidInputException;
import com.intuit.cg.backendtechassessment.model.Bid;
import com.intuit.cg.backendtechassessment.model.Buyer;
import com.intuit.cg.backendtechassessment.model.Project;
import com.intuit.cg.backendtechassessment.repository.IBidRepository;

/**
 ** Service Layer for Bid related operations like creating a bid,
 * validating bid attributes before saving, fetching one or more bids,
 * fetching projects that are close for bidding and assigning a winning bid to a project
 */
@Service
public class BidServiceImpl implements IBidService {

	final Logger LOG = LoggerFactory.getLogger(BidServiceImpl.class);
	
	@Autowired
	IBidRepository bidRepository;
	
	@Autowired
	IProjectService projectService;
	
	@Autowired
	IBuyerService buyerService;
	
	void validateBidAttributes(Bid bid) throws InvalidInputException {
		
		final Project project = projectService.validateProject(bid.getProject());		
		final Buyer buyer = buyerService.validateBuyer(bid.getBuyer());
		
		bid.setProject(project);
		bid.setBuyer(buyer);
		
		validateBidAmount(bid);	
		
		bid.setCreationDate(DateTime.now().toDate());
	}
	
	//check bid amount against Project Max budget
	boolean validateBidAmount(Bid bid) throws InvalidInputException{

		if(bid.getBidAmount() > bid.getProject().getMaxBudget()){
			throw new InvalidInputException("Bid should be less than Maximum Project Budget"); 
		}
		return false;		
	}
	
	@Override
	public Bid save(Bid bid) throws Exception {			
		
		LOG.debug("Bid Inside service :"+bid);	
		validateBidAttributes(bid);		
		return bidRepository.save(bid);
	}
		
	public List<Bid> findAll(){
		
		final Iterable<Bid> bidIterator = bidRepository.findAll();
		
		final List<Bid> bidList = new ArrayList<Bid>();
		bidIterator.forEach(bid -> bidList.add(bid));
		
		return bidList;	
	}
}
