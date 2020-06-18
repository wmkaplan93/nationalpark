package com.techelevator;

import java.util.List;

public interface ParkDAO {
	
	//get all parks method
	//Sorted alphabetically by name
	//returns all info
	//Returns a list of park objects
	public List<Park> getAllParks();

}
