package com.intuit.cg.backendtechassessment.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.cg.backendtechassessment.controller.requestmappings.RequestMappings;
import com.intuit.cg.backendtechassessment.dto.BidDTO;
import com.intuit.cg.backendtechassessment.dto.ErrorDTO;
import com.intuit.cg.backendtechassessment.exception.InvalidInputException;
import com.intuit.cg.backendtechassessment.model.Bid;
import com.intuit.cg.backendtechassessment.service.IBidService;
import com.intuit.cg.backendtechassessment.util.EntityToDTOConverterUtil;

@RestController
@RequestMapping(RequestMappings.BIDS)
public class BidController {

	final Logger LOG = LoggerFactory.getLogger(BidController.class);

	@Autowired
	private IBidService bidService;

	@Autowired
	private EntityToDTOConverterUtil dtoConverterUtil;

	@PostMapping
	public ResponseEntity<Object> addBid(@Valid @RequestBody Bid bid) throws Exception {

		Bid bidSaved = null;
		try {
			bidSaved = bidService.save(bid);
			return new ResponseEntity<>(dtoConverterUtil.convertToBidDTO(bidSaved), HttpStatus.CREATED);
		}
		catch (final InvalidInputException iie) {
			LOG.error("Validation Exception:", iie);			
			return new ResponseEntity<>( new ErrorDTO(iie.getMessage()), HttpStatus.BAD_REQUEST);
		}
		catch (final Exception e) {
			LOG.error("Exception occured:", e);			
			return new ResponseEntity<>( new ErrorDTO("Error ocurred processing the request"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	

	@GetMapping
	public ResponseEntity<List<BidDTO>> getAllbids() {

		final List<Bid> bidList = bidService.findAll();
		if (bidList != null) {
			return new ResponseEntity<>(
					bidList.stream().map(bid -> dtoConverterUtil.convertToBidDTO(bid)).collect(Collectors.toList()),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
