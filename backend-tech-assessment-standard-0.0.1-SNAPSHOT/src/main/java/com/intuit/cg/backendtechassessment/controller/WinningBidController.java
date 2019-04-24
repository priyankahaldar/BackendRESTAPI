package com.intuit.cg.backendtechassessment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.cg.backendtechassessment.dto.BidDTO;
import com.intuit.cg.backendtechassessment.model.WinningBid;
import com.intuit.cg.backendtechassessment.service.IProjectService;
import com.intuit.cg.backendtechassessment.util.EntityToDTOConverterUtil;

@RestController
public class WinningBidController {

	@Autowired
	IProjectService projectService;	
	
	@Autowired
	private EntityToDTOConverterUtil dtoConverterUtil;

	@GetMapping("/winningBid/{projectId}")
	public ResponseEntity<BidDTO> findProjectWinningBid(@PathVariable Long projectId){		
		
		final WinningBid saved = projectService.findProjectWinningBid(projectId);
		
		if(saved!=null){
			return new ResponseEntity<>(dtoConverterUtil.convertToBidDTO(saved.getBid()), HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
	}
	
}
