package com.techelevator;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCCampgroundDAO implements CampgroundDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Campground> getAllCampgrounds(long parkId) {
		
		List<Campground>allCampgrounds = new ArrayList<Campground>();
		
		String sqlCampgroundList = "SELECT campground_id, park_id, name, open_from_mm, open_to_mm, daily_fee::numeric::float8 "
								 + "FROM campground "
								  +"WHERE park_id = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlCampgroundList, parkId);
		
		while(results.next()) {
			Campground newCampground = new Campground();
			newCampground.setCampgroundId(results.getLong("campground_id"));
			newCampground.setParkId(results.getLong("park_id"));
			newCampground.setCampgroundName(results.getString("name"));
			newCampground.setOpenMonth(results.getString("open_from_mm"));
			newCampground.setCloseMonth(results.getString("open_to_mm"));
			newCampground.setDailyFee(results.getFloat("daily_fee"));
			allCampgrounds.add(newCampground);
		}
		return allCampgrounds;
	}
	

}
