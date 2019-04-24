package com.intuit.cg.backendtechassessment.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.intuit.cg.backendtechassessment.model.Project;

@Repository
public interface IProjectRepository extends CrudRepository<Project, Long>{	
	
	@Query("SELECT p FROM Project p LEFT JOIN p.winningBid wb "
			+ " WHERE wb.winningBidId IS NULL AND p.deadlineForBids < :currentDate")	
    public List<Project> fetchProjectsByDeadLine(@Param("currentDate") Date currentDate);

}
