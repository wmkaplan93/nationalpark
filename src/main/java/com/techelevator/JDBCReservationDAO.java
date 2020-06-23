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
	public Reservation makeReservation(String reservationName, LocalDate startDate, LocalDate endDate, Site site) {
		
		Reservation newRes = new Reservation();
		LocalDate currentDate = LocalDate.now();
		
		newRes.setCustomerName(reservationName);
		newRes.setFromDate(startDate);
		newRes.setToDate(endDate);
		newRes.setSiteId(site.getSiteId());
		newRes.setReservationId(getNextReservationId());
		
		long siteNum = site.getSiteId();
		
		long thisId = newRes.getReservationId();
		
		String sqlCommand = "INSERT INTO reservation(reservation_id, site_id, name, from_date, to_date, create_date) "+
							"VALUES (?, ?, CONCAT(?, 'Family Reservation'), ?, ?, ?)";
		
		jdbcTemplate.update(sqlCommand, thisId, siteNum, reservationName, startDate, endDate, currentDate);
		
//		String sqlGetThisReservation = "SELECT reservation_id, site_id, name, from_date, to_date, create_date " +
//										"FROM reservation " +
//										"WHERE name = ?";
//		
//		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetThisReservation, reservationName);
//		
//		newRes.setCreateDate(results.getDate("create_date").toLocalDate());
//		newRes.setReservationId(getNextReservationId());
		System.out.println("Thank you for the reservation.");
		newRes.toString();
		return newRes;
	}
	
	public void createReservation(Reservation theReservation) {
		String sqlInsertReservation = "INSERT INTO reservation(reservation_id, site_id, name, from_date, to_date, create_date) "
				+ "VALUES(?, ?, CONCAT(?, Family Reservation), ?, ?, ?)";
		jdbcTemplate.update(sqlInsertReservation, getNextReservationId(), theReservation.getSiteId(), theReservation.getCustomerName(), theReservation.getFromDate(), theReservation.getToDate(), theReservation.getCreateDate());
		
	}
	
	public long getNextReservationId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('reservation_reservation_id_seq')");
		if (nextIdResult.next()) {
			return nextIdResult.getLong(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id for the new reservation.");
		}
	}
	
	/*
	 * For each reservation in the lists
	 * If resCount < max_occupancy
	 * put into a new list of available sites.
	 * 
	 */

}
