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

		
	@Override
	public List<Site> getTop5SiteOptions(String location, LocalDate startDate, LocalDate endDate, long campgroundId) {
		
		List<Site> topSites = new ArrayList<Site>();
		
		String sqlCampgroundList = "SELECT site_id, campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities "
								 + "FROM site "
								  +"WHERE campgroundId = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlCampgroundList, campgroundId);
		
		while(results.next()) {
			Site newSite = new Site();
			newSite.setParkId(results.getLong("park_id"));
			newSite.setCampgroundId(results.getLong("campround_id"));
			newSite.setCampgroundName(results.getString("name"));
			newSite.setOpenMonth(results.getString("open_from_mm"));
			newSite.setCloseMonth(results.getString("open_to_mm"));
			newSite.setDailyFee(results.getLong("daily_fee"));
			topSites.add(newSite);
		}
		return topSites;
	}

}
