package com.techelevator;

import java.time.LocalDate;

public class Reservation {
	
	//takes in information for a reservation
	//and pushes it to the reservation table.
	private long reservationId;
	private long siteId;
	private String customerName;
	private LocalDate fromDate;
	private LocalDate toDate;
	private LocalDate createDate;
	private long resCount;
	
	/************/
	public long getResCount() {
		return resCount;
	}

	public void setResCount(long resCount) {
		this.resCount = resCount;
	}

	public long getReservationId() {
		return reservationId;
	}
	
	public void setReservationId(long reservationId) {
		this.reservationId = reservationId;
	}
	
	public long getSiteId() {
		return siteId;
	}
	
	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public LocalDate getFromDate() {
		return fromDate;
	}
	
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	
	public LocalDate getToDate() {
		return toDate;
	}
	
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	
	public LocalDate getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}
	/************/
	
	@Override
	public String toString() {
		return "Your reservation ID number is: " + reservationId;
	}
	
	

}
