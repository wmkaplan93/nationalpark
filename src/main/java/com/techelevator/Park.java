package com.techelevator;

import java.time.LocalDate;

public class Park {
	
	private int parkId;
	private String parkName;
	private String location;
	private LocalDate establishedDate;
	private String area;
	private int annualVisitors;
	private String parkDescription;
	
	
	//uses default constructor.
	/*********/
	public int getParkId() {
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
	
	public String getArea() {
		return area;
	}
	
	public int getAnnualVisitors() {
		return annualVisitors;
	}
	
	public String getParkDescription() {
		return parkDescription;
	}
	
	public void setParkId(int parkId) {
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
	
	public void setArea(String area) {
		this.area = area;
	}
	
	public void setAnnualVisitors(int annualVisitors) {
		this.annualVisitors = annualVisitors;
	}
	
	public void setParkDescription(String parkDescription) {
		this.parkDescription = parkDescription;
	}
	/************/
	
	
}
