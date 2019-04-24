package com.intuit.cg.backendtechassessment.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.cg.backendtechassessment.controller.requestmappings.RequestMappings;
import com.intuit.cg.backendtechassessment.dto.BuyerDTO;
import com.intuit.cg.backendtechassessment.model.Buyer;
import com.intuit.cg.backendtechassessment.service.IBuyerService;
import com.intuit.cg.backendtechassessment.util.EntityToDTOConverterUtil;

@RestController
@RequestMapping(RequestMappings.BUYERS)
public class BuyerController {

	@Autowired
	IBuyerService buyerService;
	
	@Autowired
	private EntityToDTOConverterUtil dtoConverterUtil;
	
	@GetMapping	
	public ResponseEntity<List<BuyerDTO>> findAll(){
		
		final List<Buyer> buyerList = buyerService.findAll();
		if(buyerList!=null){
			return new ResponseEntity<>(buyerList.stream().map(buyer -> dtoConverterUtil.convertToBuyerDTO(buyer))
					.collect(Collectors.toList()), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}	
		
	@GetMapping("/{id}")	
	public ResponseEntity<BuyerDTO> findById(@PathVariable Long id){		
			
		final Buyer buyer = buyerService.findById(id);	
		if(buyer!=null){
			return new ResponseEntity<>(dtoConverterUtil.convertToBuyerDTO(buyer), HttpStatus.OK);
		}
		return new ResponseEntity<>(dtoConverterUtil.convertToBuyerDTO(buyer), HttpStatus.NOT_FOUND);
	}
	
	@PostMapping
	public ResponseEntity<BuyerDTO> add(@Valid @RequestBody Buyer buyer){
						
		final Buyer buyerSaved = buyerService.saveOrUpdate(buyer);	
		if(buyerSaved!=null){
			return new ResponseEntity<>(dtoConverterUtil.convertToBuyerDTO(buyerSaved), HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
