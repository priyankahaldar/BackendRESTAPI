package com.intuit.cg.backendtechassessment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intuit.cg.backendtechassessment.exception.InvalidInputException;
import com.intuit.cg.backendtechassessment.model.Buyer;
import com.intuit.cg.backendtechassessment.repository.IBuyerRepository;

/**
 ** Service Layer for Buyer related operations like saving a buyer, 
 * fetching one or list of buyers
 */
@Service
public class BuyerServiceImpl implements IBuyerService {

	@Autowired
	IBuyerRepository buyerRepository;

	@Override
	public List<Buyer> findAll() {
		return (List<Buyer>) buyerRepository.findAll();
	}

	public Buyer validateBuyer(final Buyer buyer) throws InvalidInputException {

		if (buyer == null || (buyer != null && buyer.getBuyerId() == null)) {
			throw new InvalidInputException("Invalid Buyer Input");
		}

		final Buyer buyerRetrieved = findById(buyer.getBuyerId());

		if (buyerRetrieved == null) {
			throw new InvalidInputException("Buyer not found");
		}
		return buyerRetrieved;
	}

	@Override
	public Buyer findById(final Long id) {

		Buyer returnObj = null;
		final Optional<Buyer> buyer = buyerRepository.findById(id);

		if (buyer.isPresent()) {
			returnObj = buyer.get();
		}
		return returnObj;
	}

	public Buyer saveOrUpdate(final Buyer buyer) {
		return buyerRepository.save(buyer);
	}
}
