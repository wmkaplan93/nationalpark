package com.techelevator;

import java.time.LocalDate;
import java.util.List;

public interface ReservationDAO {
	
	//Takes in a location, name, start date, end date
	//returns a Reservation ID
	public Reservation makeReservation(String reservationName, LocalDate startDate, LocalDate endDate, long siteId);

	public List<Reservation> mostPopularSites(long campground_id, String fromDate, String toDate);
	
	//public List <Reservation> mostPopularSites();
}
