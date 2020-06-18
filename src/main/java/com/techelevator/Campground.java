package com.techelevator;

public class Campground {
	
	private long campgroundId;
	private long parkId;
	private String campgroundName;
	private String openMonth;
	private String closeMonth;
	private long dailyFee;
	
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
	
	public long getDailyFee() {
		return dailyFee;
	}
	
	public void setDailyFee(long dailyFee) {
		this.dailyFee = dailyFee;
	}
	/*********/
	
	

}
