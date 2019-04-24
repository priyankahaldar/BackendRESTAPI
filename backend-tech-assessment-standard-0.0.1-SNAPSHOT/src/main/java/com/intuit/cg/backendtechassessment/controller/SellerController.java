package com.intuit.cg.backendtechassessment.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.cg.backendtechassessment.controller.requestmappings.RequestMappings;
import com.intuit.cg.backendtechassessment.dto.SellerDTO;
import com.intuit.cg.backendtechassessment.model.Seller;
import com.intuit.cg.backendtechassessment.service.ISellerService;
import com.intuit.cg.backendtechassessment.util.EntityToDTOConverterUtil;


@RestController
@RequestMapping(RequestMappings.SELLERS)
public class SellerController {

	final Logger LOG = LoggerFactory.getLogger(SellerController.class);
	
	@Autowired
	ISellerService sellerService;

	@Autowired
	private EntityToDTOConverterUtil dtoConverterUtil;

	@GetMapping
	public ResponseEntity<List<SellerDTO>> getAllSellers() {

		final List<Seller> sellerList = sellerService.findAll();

		if (sellerList != null) {
			return new ResponseEntity<>(sellerList.stream().map(seller -> dtoConverterUtil.convertToSellerDTO(seller))
					.collect(Collectors.toList()), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/{id}")
	public ResponseEntity<SellerDTO> getSellerById(@PathVariable Long id) {

		final Seller seller = sellerService.findById(id);
		if (seller != null) {
			return new ResponseEntity<>(dtoConverterUtil.convertToSellerDTO(seller), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping
	public ResponseEntity<SellerDTO> addSeller(@Valid @RequestBody Seller seller) {

		final Seller sellerSaved = sellerService.saveOrUpdate(seller);
		if (sellerSaved != null) {
			
			final HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
			  
			return new ResponseEntity<>(dtoConverterUtil.convertToSellerDTO(sellerSaved), headers, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
	}
}
