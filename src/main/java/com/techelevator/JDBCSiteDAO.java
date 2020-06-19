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

	//Returns sites from campground that are active during the given time frame
	//if none are available, return to menu
	//Returns top 5 sorted by popularity.
	@Override
	public List<Site> getAllSites(String location, LocalDate startDate, LocalDate endDate, long campgroundId) {
		
		List<Site> allSites = new ArrayList<Site>();
		
		String sqlSiteList = "SELECT site_id, campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities "
								  +"FROM site "
								  +"WHERE campgroundId = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSiteList, campgroundId);
		
		//if list.length == 0 "No reservations available, please try another time frame or location." 
		//Returns to reservation menu or to main menu
		
		
		
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

	
	//
	
	@Override
	public List<Site> getTop5SiteOptions(String location, LocalDate startDate, LocalDate endDate, long campgroundId) {
		return null;
	}
	
}
