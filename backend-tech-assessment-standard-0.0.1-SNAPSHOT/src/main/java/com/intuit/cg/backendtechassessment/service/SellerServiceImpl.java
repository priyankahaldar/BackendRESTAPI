package com.intuit.cg.backendtechassessment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intuit.cg.backendtechassessment.model.Seller;
import com.intuit.cg.backendtechassessment.repository.ISellerRepository;
/**
 ** Service Layer for seller related operations like saving a seller, fetching a seller, list of sellers *
 */
@Service
public class SellerServiceImpl implements ISellerService {

	@Autowired
	ISellerRepository sellerRepository;
	
	@Override
	public List<Seller> findAll() {		
		return (List<Seller>) sellerRepository.findAll();
	}

	@Override
	public Seller findById(Long id) {	
		
		Seller returnObj=null;
		final Optional<Seller> seller = sellerRepository.findById(id);
		
		if(seller.isPresent()){
			returnObj = seller.get();
		}
		return returnObj;
	}

	@Override
	public Seller saveOrUpdate(Seller seller) {		
		return sellerRepository.save(seller);
	}
}
