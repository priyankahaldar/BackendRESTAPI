package com.intuit.cg.backendtechassessment.repository;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Optional;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.intuit.cg.backendtechassessment.model.Bid;
import com.intuit.cg.backendtechassessment.model.Buyer;
import com.intuit.cg.backendtechassessment.model.Project;
import com.intuit.cg.backendtechassessment.model.Seller;

@RunWith(SpringRunner.class)
@DataJpaTest
public class IBidRepositoryTest {

	@Autowired
	// To set up initial data in db
	private TestEntityManager entityManager;

	@Autowired
	private IBidRepository bidRepository;

	@Test
	public void save_createNewBid() {
		
		final Seller seller = createSeller();
		final Project project = createProject(seller, "Test Desc", 500.0, DateTime.now().plusDays(1).toDate());
		final Buyer buyer = createBuyer();

		final Bid bid = new Bid(project, buyer, 400.0);

		final Bid bidSaved = bidRepository.save(bid);

		assertEquals(bid.getProject(), bidSaved.getProject());
		assertEquals(bid.getBuyer(), bidSaved.getBuyer());
		assertEquals(bid.getBidAmount(), bidSaved.getBidAmount());
	}
	
	

	Project createProject(Seller seller, String projectDesc, Double maxBudget, Date deadlineForBids) {
		
		final Project project = new Project(null, seller, projectDesc, maxBudget, deadlineForBids);
		persist(project);
		
		return project;
	}

	Buyer createBuyer() {

		final Buyer buyer = new Buyer();
		buyer.setFirstName("Brad");
		buyer.setLastName("Spies");

		persist(buyer);
		return buyer;
	}

	Seller createSeller() {

		final Seller seller = new Seller(null, "Sam", "Smith");
		persist(seller);
		return seller;
	}

	void persist(Object entity) {

		entityManager.persist(entity);
		entityManager.flush();
	}
}