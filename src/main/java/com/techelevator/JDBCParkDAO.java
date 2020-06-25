package com.techelevator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.Park;

public class JDBCParkDAO implements ParkDAO{
	
	private JdbcTemplate jdbcTemplate;

	public JDBCParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Park> getAllParks() {
		
		List<Park>allParks = new ArrayList<Park>();
		
		String sqlParkList = "SELECT park_id, name, location, establish_date, area, visitors, description FROM park";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlParkList);
		
		while(results.next()) {
			Park newPark = new Park();
			newPark.setParkId(results.getLong("park_id"));
			newPark.setParkName(results.getString("name"));
			newPark.setLocation(results.getString("location"));
			newPark.setEstablishedDate(results.getDate("establish_date").toLocalDate());
			newPark.setArea(results.getLong("area"));
			newPark.setAnnualVisitors(results.getLong("visitors"));
			newPark.setParkDescription(results.getString("description"));
			allParks.add(newPark);
		}
		return allParks;
	}

	public void createPark(Park park) {
		String sqlRowSetPark = "INSERT INTO park (park_id, name, location, establish_date, area, visitors, description) "
				+ "VALUES (" + park.getParkId() + ", \'" + park.getParkName() + "\', \'" + park.getLocation() + "\', \'" + park.getEstablishedDate() + "\', " + park.getArea() + ", " + park.getAnnualVisitors() + ", \'" + park.getParkDescription() + "\')";
		jdbcTemplate.update(sqlRowSetPark);
	}
	
	

}
