package com.techelevator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class DAOIntegrationTest {

	/* Using this particular implementation of DataSource so that
	 * every database interaction is part of the same database
	 * session and hence the same database transaction */
	private static SingleConnectionDataSource dataSource;
	private JDBCParkDAO daoPark;
	private JDBCCampgroundDAO daoCampground;
	private JDBCReservationDAO daoReservation;
	private JDBCSiteDAO daoSite;

	/* Before any tests are run, this method initializes the datasource for testing. */
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		/* The following line disables autocommit for connections
		 * returned by this DataSource. This allows us to rollback
		 * any changes after each test */
		dataSource.setAutoCommit(false);
	}

	/* After all tests have finished running, this method will close the DataSource */
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	/* After each test, we rollback any changes that were made to the database so that
	 * everything is clean for the next test */
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	/* This method provides access to the DataSource for subclasses so that
	 * they can instantiate a DAO for testing */
	protected DataSource getDataSource() {
		return dataSource;
	}
	
	@Before
	public void setup() {
		
		daoPark = new JDBCParkDAO(dataSource);
		daoCampground = new JDBCCampgroundDAO(dataSource);
		daoReservation = new JDBCReservationDAO(dataSource);
		daoSite = new JDBCSiteDAO(dataSource);
	}
	
	@Test
	public void returns_all_parks() {
		
		List<Park> results = daoPark.getAllParks();
		
		Park thePark = getPark("Test Park");
		daoPark.createPark(thePark);

		List<Park> compareResults = daoPark.getAllParks();

		assertNotNull(results);
		assertEquals((results.size() + 1), compareResults.size());
	}
	
	@Test
	public void returns_all_campgrounds() {
		
		List<Campground> results = daoCampground.getAllCampgrounds(1L);
		
		Campground theCampground = getCampground("Test Campground");
		daoCampground.createCampground(theCampground);

		List<Campground> compareResults = daoCampground.getAllCampgrounds(1L);

		assertNotNull(results);
		assertEquals((results.size() + 1), compareResults.size());
	}
	
	//assert reservations are equal
	@Test
	public void new_reservation_made_correctly() {
		
		LocalDate startingDate = LocalDate.of(2020, 7, 7);
		LocalDate endingDate = LocalDate.of(2020, 8, 8);
		LocalDate createdDate = LocalDate.now();
		long testRes = daoReservation.getNextReservationId();
		
		Reservation theReservation = getReservation(testRes, 622L, "Steve Miami", startingDate, endingDate, createdDate);
		daoReservation.createReservation(theReservation);

		Reservation actualReservation = daoReservation.makeReservation("Steve Miami", startingDate, endingDate, 622L);
		
		assertNotNull(actualReservation);
		assertReservationsAreEqual(theReservation, actualReservation);
		
	}
	
	@Test
	public void returns_all_sites() {
		
		LocalDate startDate = LocalDate.of(2020, 1, 1);
		LocalDate endDate = LocalDate.of(2020, 2, 2);
		
		List<Site> results = daoSite.getAllSites("Steve", startDate, endDate, 1L);
		
		Site theSite = getSite(1L);
		daoSite.createSite(theSite);

		List<Site> compareResults = daoSite.getAllSites("Steve", startDate, endDate, 1L);

		assertNotNull(results);
		assertEquals((results.size() + 1), compareResults.size());
	}
	
	
	
	
	//HELPER METHODS***
	
	private void assertReservationsAreEqual(Reservation expected, Reservation actual) {
		
		assertEquals((expected.getReservationId() +1), actual.getReservationId());
		assertEquals(expected.getCustomerName(), actual.getCustomerName());
		assertEquals(expected.getFromDate(), actual.getFromDate());
		assertEquals(expected.getToDate(), actual.getToDate());
		//assertEquals(expected.getSiteId(), actual.getSiteId());
		//assertEquals(expected.getCreateDate(), actual.getCreateDate());
		assertEquals(expected.getResCount(), actual.getResCount());
	}
	
	private Park getPark(String name) {
		Park thisPark = new Park();
		thisPark.setParkName(name);
		return thisPark;
	}
	
	private Site getSite(long siteId) {
		Site thisSite = new Site();
		thisSite.setSiteId(siteId);
		return thisSite;
	}
	
	private Campground getCampground(String campgroundName) {
		Campground thisCampground = new Campground();
		thisCampground.setCampgroundName(campgroundName);
		return thisCampground;
	}
	
	private Reservation getReservation(Long reservation_id, Long site_id, String custName, LocalDate from_date, LocalDate to_date, LocalDate create_date) {
		Reservation thisReservation = new Reservation();
		thisReservation.setCustomerName(custName);
		thisReservation.setReservationId(reservation_id);
		thisReservation.setSiteId(site_id);
		thisReservation.setFromDate(from_date);
		thisReservation.setToDate(to_date);
		thisReservation.setCreateDate(create_date);
		return thisReservation;
	}
	
	//So what do we actually need to test?
	/*
	 * Make sure lists are correct length (Campground, sites, parks, reservation)
	 * One for resrvation to check the new reservation? Is that assert equals?
	 * 
	 */
	//TODO:
	//create a test park, test campground, and test site. Use them to run tests.
	//This will take a while, don't let the stress get to you. Slow and steady.
	//Calm, cool, collected, and In Control. You can do this.
	
}
