package com.techelevator;

public class Campground {
	
	private int campgroundId;
	private int parkId;
	private String campgroundName;
	private int openMonth;
	private int closeMonth;
	private int dailyFee;
	
	/********/
	public int getCampgroundId() {
		return campgroundId;
	}
	
	public void setCampgroundId(int campgroundId) {
		this.campgroundId = campgroundId;
	}
	
	public int getParkId() {
		return parkId;
	}
	
	public void setParkId(int parkId) {
		this.parkId = parkId;
	}
	
	public String getCampgroundName() {
		return campgroundName;
	}
	
	public void setCampgroundName(String campgroundName) {
		this.campgroundName = campgroundName;
	}
	
	public int getOpenMonth() {
		return openMonth;
	}
	
	public void setOpenMonth(int openMonth) {
		this.openMonth = openMonth;
	}
	
	public int getCloseMonth() {
		return closeMonth;
	}
	
	public void setCloseMonth(int closeMonth) {
		this.closeMonth = closeMonth;
	}
	
	public int getDailyFee() {
		return dailyFee;
	}
	
	public void setDailyFee(int dailyFee) {
		this.dailyFee = dailyFee;
	}
	/*********/
	
	

}
