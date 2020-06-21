package com.techelevator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCReservationDAO implements ReservationDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public Reservation makeReservation(String reservationName, LocalDate startDate, LocalDate endDate, long siteId) {
		
		Reservation newRes = new Reservation();
		LocalDate currentDate = LocalDate.now();
		
		newRes.setCustomerName(reservationName);
		newRes.setFromDate(startDate);
		newRes.setToDate(endDate);
		
		String sqlCommand = "INSERT INTO reservation(site_id, name, from_date, to_date, create_date) "+
							"VALUES (" + siteId + ", " + startDate + ", " + endDate + ", " + currentDate + ");";
		
		jdbcTemplate.update(sqlCommand);
		
		String sqlGetThisReservation = "SELECT reservation_id, site_id, name, from_date, to_date, create_date " +
										"FROM reservation " +
										"WHERE name = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetThisReservation, reservationName);
		
		newRes.setCreateDate(results.getDate("create_date").toLocalDate());
		newRes.setReservationId(results.getLong("reservation_id"));
		
		return newRes;
	}
	@Override
	public List<Reservation> mostPopularSites(long campground_id) {
		
		List<Reservation> mostReservations = new ArrayList<Reservation>();
		
		String sqlList = "WITH    reservation AS (SELECT count(*) AS rescount, r.site_id " + 
				"        FROM reservation r " + 
				"        GROUP BY r.site_id " + 
				"        ORDER BY rescount DESC) " + 
				"SELECT r.site_id, r.rescount r.name, r.from_date, r.to_date, r.create_date, r.reservation_id " + 
				"FROM site s " + 
				"LEFT JOIN reservation r ON s.site_id = r.site_id " + 
				"WHERE r.rescount < s.max_occupancy " + 
				"AND s.campground_id = ?"
				+ " LIMIT 5";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlList, campground_id);
		
		while(results.next()) {
			Reservation testRes = new Reservation();
			testRes.setSiteId(results.getLong("r.site_id"));
			testRes.setCustomerName(results.getString("r.name"));
			testRes.setFromDate(results.getDate("r.from_date").toLocalDate());
			testRes.setToDate(results.getDate("r.to_date").toLocalDate());
			testRes.setCreateDate(results.getDate("r.create_date").toLocalDate());
			testRes.setReservationId(results.getLong("r.reservation_id"));
			testRes.setResCount(results.getLong("r.rescount"));
			mostReservations.add(testRes);
		}
		return mostReservations;
	}
	
	/*
	 * For each reservation in the lists
	 * If resCount < max_occupancy
	 * put into a new list of available sites.
	 * 
	 */

}
