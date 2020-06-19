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
	public Map<Long, Long> mostPopularSites() {
		
		Map<Long, Long> popularReservations = new HashMap<Long, Long>();
		
		String sqlCommand = "SELECT count(*) AS resCount, site_id FROM reservation "
				+ "GROUP BY site_id "
				+ "ORDER BY count DESC";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlCommand);
		
		while(results.next()) {
			long thisKey = results.getLong("rescount");
			long thisValue = results.getLong("site_id");
			popularReservations.put(thisKey, thisValue);
		}
		
		return popularReservations;
	}
	
	/*
	 * For each reservation in the lists
	 * If resCount < max_occupancy
	 * put into a new list of available sites.
	 * 
	 */

}
