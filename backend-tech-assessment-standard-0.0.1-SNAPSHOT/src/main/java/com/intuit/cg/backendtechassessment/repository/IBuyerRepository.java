package com.intuit.cg.backendtechassessment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.intuit.cg.backendtechassessment.model.Buyer;
 
@Repository
public interface IBuyerRepository extends CrudRepository<Buyer, Long>{	
}
