package com.techelevator;

public class Site {
	
	private long siteId;
	private long campgroundId;
	private long siteNumber;
	private long maxOccupancy;
	private boolean accessible;
	private long maxRvLength;
	private boolean utilities;
	
	/***********/
	public long getSiteId() {
		return siteId;
	}
	
	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}
	
	public long getCampgroundId() {
		return campgroundId;
	}
	
	public void setCampgroundId(long campgroundId) {
		this.campgroundId = campgroundId;
	}
	
	public long getSiteNumber() {
		return siteNumber;
	}
	
	public void setSiteNumber(long siteNumber) {
		this.siteNumber = siteNumber;
	}
	
	public long getMaxOccupancy() {
		return maxOccupancy;
	}
	
	public void setMaxOccupancy(long maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}
	
	public boolean isAccessible() {
		return accessible;
	}
	
	public void setAccessible(boolean accessible) {
		this.accessible = accessible;
	}
	
	public long getMaxRvLength() {
		return maxRvLength;
	}
	
	public void setMaxRvLength(long maxRvLength) {
		this.maxRvLength = maxRvLength;
	}
	
	public boolean isUtilities() {
		return utilities;
	}
	
	public void setUtilities(boolean utilities) {
		this.utilities = utilities;
	}
	/***************/

}
