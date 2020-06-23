package com.techelevator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCSiteDAO implements SiteDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCSiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	//Returns sites from camp ground that are active during the given time frame
	//if none are available, return to menu
	//Returns top 5 sorted by popularity.
	@Override
	public List<Site> getAllSites(String location, LocalDate startDate, LocalDate endDate, long campgroundId) {
		
		List<Site> allSites = new ArrayList<Site>();
		
		String sqlSiteList = "SELECT site_id, campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities "
								  +"FROM site "
								  +"WHERE campgroundId = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSiteList, campgroundId);
		
		while(results.next()) {
			Site newSite = new Site();
			newSite.setSiteId(results.getLong("site_id"));
			newSite.setCampgroundId(results.getLong("campground_id"));
			newSite.setSiteNumber(results.getLong("site_number"));
			newSite.setMaxOccupancy(results.getLong("max_occupancy"));
			newSite.setAccessible(results.getBoolean("accessible"));
			newSite.setMaxRvLength(results.getLong("max_rv_length"));
			newSite.setUtilities(results.getBoolean("utilities"));
			allSites.add(newSite);
		}
		return allSites;
	}
	
	public List<Site> mostPopularSites(long campground_id, int fromDate, int toDate) {
		
		List<Site> mostReservations = new ArrayList<Site>();
		
		String sqlList = "WITH    reservation AS ( "
				+ "		  SELECT count(*) AS rescount, r.site_id "
				+ "       FROM reservation r " 
				+ "       GROUP BY r.site_id " 
				+ "       ) " 
				+ "SELECT s.site_number, COALESCE(r.rescount, 0) AS sitepopularity, s.site_id " 
				+ "FROM site s " 
				+ "LEFT JOIN reservation r ON s.site_id = r.site_id "
				+ "JOIN campground c ON s.campground_id = c.campground_id " 
				+ "WHERE s.campground_id = ? "
				+ "AND c.open_from_mm::integer <= ? "
				+ "AND c.open_to_mm::integer >= ? "
				+ "ORDER BY sitepopularity DESC "
				+ "LIMIT 5";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlList, campground_id, fromDate, toDate);

		
		while(results.next()) {
			Site newSite = new Site();
			newSite.setSiteId(results.getLong("site_id"));
//			newSite.setCampgroundId(results.getLong("campground_id"));
			newSite.setSiteNumber(results.getLong("site_number"));
//			newSite.setMaxOccupancy(results.getLong("max_occupancy"));
//			newSite.setAccessible(results.getBoolean("accessible"));
//			newSite.setMaxRvLength(results.getLong("max_rv_length"));
//			newSite.setUtilities(results.getBoolean("utilities"));
			mostReservations.add(newSite);
		}
		return mostReservations;
	}
		
//
//	public void createSite(Site theSite) {
//		String sqlInsertSite = "INSERT INTO site(site_id"
//		
//	}
	
}
