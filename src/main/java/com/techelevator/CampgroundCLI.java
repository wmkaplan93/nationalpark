package com.techelevator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class CampgroundCLI {
	
	Scanner userIn = new Scanner(System.in);
	JDBCParkDAO jdbcParkDao;
	JDBCCampgroundDAO jdbcCampgroundDao;
	
	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
	}

	public CampgroundCLI(DataSource datasource) {
		// create your DAOs here
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		
		jdbcParkDao = new JDBCParkDAO(dataSource);
		jdbcCampgroundDao = new JDBCCampgroundDAO(dataSource);
		
		startMenu();
		
	}

	public void run() {
		
		

	}
	
	public void startMenu() {
		
		List<Park>allParks = jdbcParkDao.getAllParks();
		List<Campground>allCampgrounds = new ArrayList<Campground>();
		
		System.out.println("Welcome to the National Park camping reservation application!");
		System.out.println("To get started please type (S), or (Q) to exit.");
		String userInput = userIn.nextLine();
		
		if(userInput.toUpperCase().equals("S")) {
			
			
			
			System.out.println("Select a park for further details:");
			int optNum = 1;
			for(Park park : allParks) {
				System.out.println(optNum +") " + park.getParkName());
				optNum++;
			}
			System.out.println("Q) Quit");
			
		}
		else if(userInput.toUpperCase().contentEquals("Q")) {
			System.exit(1);
		}
		else { 
			System.out.println("Please enter a valid input.");
			startMenu();
		}
		userInput = userIn.nextLine();
		
		
		if(userInput.equals("1")) {
			jdbcCampgroundDao.getAllCampgrounds(allParks.get(1).getParkId());
			int optNum = 1;
			for(Campground cGround : allCampgrounds) {
				System.out.println(optNum +") " + cGround.getCampgroundName());
				optNum++;
				System.out.println("We got to this point");
			}
		}
	}
}
