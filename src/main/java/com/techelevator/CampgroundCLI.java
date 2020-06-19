package com.techelevator;

import java.util.List;
import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class CampgroundCLI {
	
	Scanner userIn = new Scanner(System.in);
	

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
		
		JDBCParkDAO jdbcParkDao = new JDBCParkDAO(dataSource);
		
		
		System.out.println("Welcome to the National Park camping reservation application!");
		System.out.print("To get started please type (S), or (Q) to exit.");
		String userInput = userIn.nextLine();
		
		if(userInput.toUpperCase().equals("S")) {
			
			List<Park>allParks = jdbcParkDao.getAllParks();
			
			System.out.println("Select a park for further details:");
			int optNum = 1;
			for(Park park : allParks) {
				System.out.println(optNum +") " + park.getParkName());
			}
			System.out.println("Q) Quit");
			
		}
		else if(userInput.toUpperCase().contentEquals("Q")) {
			System.exit(1);
		}
	}

	public void run() {
		
		

	}
}
