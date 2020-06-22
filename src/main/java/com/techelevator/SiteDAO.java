package com.techelevator;

import java.time.LocalDate;
import java.util.List;

public interface SiteDAO {
	
	//we just need a single method
	//Gets active campsites, 
	//returns top 5
	//returns to menu if none.
	public List<Site> mostPopularSites(long campground_id, int fromDate, int toDate);
	
	
	public List <Site> getAllSites (String location, LocalDate startDate, LocalDate endDate, long campgroundId);
	
	

}
