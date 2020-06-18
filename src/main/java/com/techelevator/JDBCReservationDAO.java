package com.techelevator;

import java.time.LocalDate;

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

}
