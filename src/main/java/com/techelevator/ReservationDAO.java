package com.techelevator;

import java.time.LocalDate;

public interface ReservationDAO {
	
	//Takes in a location, name, start date, end date
	//returns a Reservation ID
	public Reservation makeReservation(String reservationName, LocalDate startDate, LocalDate endDate, long siteId);
	

}
