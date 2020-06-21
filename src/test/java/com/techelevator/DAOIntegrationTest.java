package com.techelevator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public abstract class DAOIntegrationTest {

	/* Using this particular implementation of DataSource so that
	 * every database interaction is part of the same database
	 * session and hence the same database transaction */
	private static SingleConnectionDataSource dataSource;
	private JDBCParkDAO daoPark;

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
	
	
	//HELPER METHODS***
	
	private void assertReservationsAreEqual(Reservation expected, Reservation actual) {
		
		assertEquals(expected.getReservationId(), actual.getReservationId());
		assertEquals(expected.getCustomerName(), actual.getCustomerName());
		assertEquals(expected.getFromDate(), actual.getFromDate());
		assertEquals(expected.getToDate(), actual.getToDate());
		assertEquals(expected.getSiteId(), actual.getSiteId());
		assertEquals(expected.getCreateDate(), actual.getCreateDate());
		assertEquals(expected.getResCount(), actual.getResCount());
	}
	
	private Park getPark(String name) {
		Park thisPark = new Park();
		thisPark.setParkName(name);
		return thisPark;
	}
	
	//So what do we actually need to test?
	/*
	 * Make sure lists are correct length (Campground, sites, parks, reservation)
	 * One for resrvation to check the new reservation? Is that assert equals?
	 * 
	 */
	
}
