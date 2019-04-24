package com.intuit.cg.backendtechassessment.util;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.intuit.cg.backendtechassessment.dto.BidDTO;
import com.intuit.cg.backendtechassessment.dto.BuyerDTO;
import com.intuit.cg.backendtechassessment.dto.ProjectDTO;
import com.intuit.cg.backendtechassessment.dto.SellerDTO;
import com.intuit.cg.backendtechassessment.model.Bid;
import com.intuit.cg.backendtechassessment.model.Buyer;
import com.intuit.cg.backendtechassessment.model.Project;
import com.intuit.cg.backendtechassessment.model.Seller;

@Component
public class EntityToDTOConverterUtil {
	
	private ModelMapper modelMapper;
	
	public EntityToDTOConverterUtil(){}
	
	@Autowired
	public EntityToDTOConverterUtil(ModelMapper modelMapper){
		this.modelMapper = modelMapper;
	}
	
	public ProjectDTO convertToProjectDTO(Project project) {

		ProjectDTO projectDTO = null;

		if (project != null) {
			projectDTO = modelMapper.map(project, ProjectDTO.class);
			projectDTO.setLowestBid(convertToBidDTO(project.getLowestBid()));
		}
		return projectDTO;
	}

	public BidDTO convertToBidDTO(Bid bid) {

		BidDTO bidDTO = null;

		if (bid != null) {
			bidDTO = modelMapper.map(bid, BidDTO.class);
			bidDTO.setBuyer(convertToBuyerDTO(bid.getBuyer()));			
			
			if (bid.getWinningBid() != null) {
				bidDTO.setWinner(true);				
			}
		}
		return bidDTO;
	}

	public SellerDTO convertToSellerDTO(Seller seller) {

		SellerDTO sellerDTO = null;

		if (seller != null) {
			sellerDTO = modelMapper.map(seller, SellerDTO.class);

			final List<Project> projects = seller.getProjects();
			if (projects != null) {
				sellerDTO.setProjects(
						projects.stream().map(project -> convertToProjectDTO(project)).collect(Collectors.toList()));
			}
		}
		return sellerDTO;
	}

	public BuyerDTO convertToBuyerDTO(Buyer buyer) {

		BuyerDTO buyerDTO = null;

		if (buyer != null) {
			buyerDTO = modelMapper.map(buyer, BuyerDTO.class);
		}
		return buyerDTO;
	}
}
