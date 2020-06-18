package com.techelevator;

import java.time.LocalDate;

public class Park {
	
	private long parkId;
	private String parkName;
	private String location;
	private LocalDate establishedDate;
	private long area;
	private long annualVisitors;
	private String parkDescription;
	
	
	//uses default constructor.
	/*********/
	public long getParkId() {
		return parkId;
	}
	
	public String getParkName() {
		return parkName;
	}
	
	public String getLocation() {
		return location;
	}
	
	public LocalDate getEstablishedDate() {
		return establishedDate;
	}
	
	public long getArea() {
		return area;
	}
	
	public long getAnnualVisitors() {
		return annualVisitors;
	}
	
	public String getParkDescription() {
		return parkDescription;
	}
	
	public void setParkId(long parkId) {
		this.parkId = parkId;
	}
	
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public void setEstablishedDate(LocalDate establishedDate) {
		this.establishedDate = establishedDate;
	}
	
	public void setArea(long area) {
		this.area = area;
	}
	
	public void setAnnualVisitors(long annualVisitors) {
		this.annualVisitors = annualVisitors;
	}
	
	public void setParkDescription(String parkDescription) {
		this.parkDescription = parkDescription;
	}
	/************/
	
	
}
