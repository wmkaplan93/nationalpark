package com.techelevator;

import java.math.BigDecimal;

public class Campground {
	
	private long campgroundId;
	private long parkId;
	private String campgroundName;
	private String openMonth;
	private String closeMonth;
	private float dailyFee;
	
	/********/
	public long getCampgroundId() {
		return campgroundId;
	}
	
	public void setCampgroundId(long campgroundId) {
		this.campgroundId = campgroundId;
	}
	
	public long getParkId() {
		return parkId;
	}
	
	public void setParkId(long parkId) {
		this.parkId = parkId;
	}
	
	public String getCampgroundName() {
		return campgroundName;
	}
	
	public void setCampgroundName(String campgroundName) {
		this.campgroundName = campgroundName;
	}
	
	public String getOpenMonth() {
		return openMonth;
	}
	
	public void setOpenMonth(String openMonth) {
		this.openMonth = openMonth;
	}
	
	public String getCloseMonth() {
		return closeMonth;
	}
	
	public void setCloseMonth(String closeMonth) {
		this.closeMonth = closeMonth;
	}
	
	public float getDailyFee() {
		return dailyFee;
	}
	
	public void setDailyFee(float dailyFee) {
		this.dailyFee = dailyFee;
	}
	/*********/
	
	

}
