package com.techelevator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
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
	private static final long TEST_SITE = -5L;
	private static final long TEST_PARK = -5L;
	private static final long TEST_CAMPGROUND = -5L;

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
		
		String sqlInsertPark = "INSERT INTO park (park_id, name, location, establish_date, area, visitors, description) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		JdbcTemplate jdbcTemplatePark = new JdbcTemplate(dataSource);
		LocalDate parkDate = LocalDate.of(2001, 01, 01);
		jdbcTemplatePark.update(sqlInsertPark, TEST_PARK, "a", "c", parkDate, 10, 10, "b");
		
		String sqlInsertCampground = "INSERT INTO campground (campground_id,park_id, name, open_from_mm, open_to_mm, daily_fee) "
				+ "VALUES (?, ?, ?, ?, ?, ?::numeric::money)";
		JdbcTemplate jdbcTemplateCampground = new JdbcTemplate(dataSource);
		jdbcTemplateCampground.update(sqlInsertCampground, TEST_CAMPGROUND, TEST_PARK, "a", "05", "07", 10);
		
		String sqlInsertSite = "INSERT INTO site (site_id, campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		JdbcTemplate jdbcTemplateSite = new JdbcTemplate(dataSource);
		jdbcTemplateSite.update(sqlInsertSite, TEST_SITE, TEST_CAMPGROUND, -5, 10, true, 10, true);
	}
	
	@Test
	public void returns_all_parks() {
		
		List<Park> results = daoPark.getAllParks();
		
		Park thePark = getPark("Test");
		daoPark.createPark(thePark);

		List<Park> compareResults = daoPark.getAllParks();

		assertNotNull(results);
		assertEquals((results.size() + 1), compareResults.size());
	}
	
	@Test
	public void returns_all_campgrounds() {
		
		List<Campground> results = daoCampground.getAllCampgrounds(TEST_PARK);
		
		Campground theCampground = getCampground("Test Campground");
		daoCampground.createCampground(theCampground);

		List<Campground> compareResults = daoCampground.getAllCampgrounds(TEST_PARK);

		assertNotNull(results);
		assertEquals((results.size() + 1), compareResults.size());
	}
	
	@Test
	public void returns_all_sites() {
		
		LocalDate startDate = LocalDate.of(2020, 1, 1);
		LocalDate endDate = LocalDate.of(2020, 2, 2);
		
		List<Site> results = daoSite.getAllSites("Steve", startDate, endDate, 1L);
		
		Site testSite = getSite(TEST_SITE);
		daoSite.createSite(testSite);

		List<Site> compareResults = daoSite.getAllSites("Steve", startDate, endDate, 1L);

		assertNotNull(results);
		assertEquals((results.size() + 1), compareResults.size());
	}
	
	//assert reservations are equal
	@Test
	public void new_reservation_made_correctly() {
		
		LocalDate startingDate = LocalDate.of(2020, 7, 7);
		LocalDate endingDate = LocalDate.of(2020, 8, 8);
		LocalDate createdDate = LocalDate.now();

		long testResId = daoReservation.getNextReservationId();
		
		Reservation theReservation = getReservation(testResId);
		daoReservation.createReservation(theReservation);
		
		Site testSite = getSite(TEST_SITE);
		daoSite.createSite(testSite);

		Reservation actualReservation = daoReservation.makeReservation("Steve Miami", startingDate, endingDate, testSite);
		
		assertNotNull(actualReservation);
		assertReservationsAreEqual(theReservation, actualReservation);
		
	}
	
	//HELPER METHODS***
	
	private void assertReservationsAreEqual(Reservation expected, Reservation actual) {
		
		assertEquals((expected.getReservationId() + 2), actual.getReservationId());
		assertEquals(expected.getCustomerName(), actual.getCustomerName());
		assertEquals(expected.getFromDate(), actual.getFromDate());
		assertEquals(expected.getToDate(), actual.getToDate());
		assertEquals(expected.getResCount(), actual.getResCount());
	}
	
	private Park getPark(String name) {
		LocalDate thisDate = LocalDate.of(2001, 01, 01);
		
		Park thisPark = new Park();
		thisPark.setParkId(-10L);
		thisPark.setParkName(name);
		thisPark.setLocation("a");
		thisPark.setEstablishedDate(thisDate);
		thisPark.setAnnualVisitors(10);
		thisPark.setArea(10);
		thisPark.setParkDescription("words");
		return thisPark;
	}
	
	private Campground getCampground(String campgroundName) {
		Campground thisCampground = new Campground();
		thisCampground.setCampgroundId(-10L);
		thisCampground.setParkId(TEST_PARK);
		thisCampground.setCampgroundName(campgroundName);
		thisCampground.setOpenMonth("05");
		thisCampground.setCloseMonth("07");
		thisCampground.setDailyFee(10.00f);
		return thisCampground;
	}
	
	private Site getSite(long siteNumber) {
		Site thisSite = new Site();
		thisSite.setSiteId(-10L);
		thisSite.setCampgroundId(1l);
		thisSite.setSiteNumber(siteNumber);
		thisSite.setMaxOccupancy(1);
		thisSite.setAccessible(true);
		thisSite.setMaxRvLength(1);
		thisSite.setUtilities(true);
		return thisSite;
	}
	
	private Reservation getReservation(Long reservation_id) {
		LocalDate startingDate = LocalDate.of(2020, 7, 7);
		LocalDate endingDate = LocalDate.of(2020, 8, 8);
		LocalDate createdDate = LocalDate.now();
		
		Reservation thisReservation = new Reservation();
		thisReservation.setCustomerName("Steve Miami");
		thisReservation.setReservationId(reservation_id);
		thisReservation.setSiteId(TEST_SITE);
		thisReservation.setFromDate(startingDate);
		thisReservation.setToDate(endingDate);
		thisReservation.setCreateDate(createdDate);
		return thisReservation;
	}
	
}
