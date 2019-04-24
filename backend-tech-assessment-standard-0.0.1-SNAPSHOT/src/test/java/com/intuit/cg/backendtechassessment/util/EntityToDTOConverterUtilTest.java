package com.intuit.cg.backendtechassessment.util;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import com.intuit.cg.backendtechassessment.dto.SellerDTO;
import com.intuit.cg.backendtechassessment.model.Seller;


public class EntityToDTOConverterUtilTest {	
	
	private EntityToDTOConverterUtil dtoConverterUtil;
	
	@Before
	public void setUp(){		
		dtoConverterUtil = new EntityToDTOConverterUtil(new ModelMapper());
	}
	
    @Test
    public void convertToSellerDTO() {
        
    	final Seller seller = new Seller(100L, "Sam", "Smith");
    	
    	final SellerDTO sellerDTOExpected = new SellerDTO();  
    	sellerDTOExpected.setSellerId(100L);
    	sellerDTOExpected.setFirstName("Sam");
    	sellerDTOExpected.setLastName("Smith");
      
        final SellerDTO dtoActual = dtoConverterUtil.convertToSellerDTO(seller);
    	
        assertEquals(sellerDTOExpected.getSellerId(), dtoActual.getSellerId());
        assertEquals(sellerDTOExpected.getFirstName(), dtoActual.getFirstName());
        assertEquals(sellerDTOExpected.getLastName(), dtoActual.getLastName());
    }
    
}
