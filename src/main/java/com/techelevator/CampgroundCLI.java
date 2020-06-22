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
	JDBCReservationDAO jdbcReservationDao;
	JDBCSiteDAO jdbcSiteDao;
	String userInput;
	List<Campground>allCampgrounds = new ArrayList<Campground>();
	List<Park> allParks = new ArrayList<Park>();
	//List<Reservation> mostReservations = new ArrayList<Reservation>();
	List<Site> mostReservations = new ArrayList<Site>();
	
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
		jdbcReservationDao = new JDBCReservationDAO(dataSource);
		jdbcSiteDao = new JDBCSiteDAO(dataSource);
		
		
		startMenu();
	}
	
	

	public void run() {
		
		

	}
	
	public void startMenu() {
		
		allParks = jdbcParkDao.getAllParks();
		
		
		System.out.println("Welcome to the National Park camping reservation application!");
		System.out.println("To get started please type (S), or (Q) to exit.");
		userInput = userIn.nextLine();
		System.out.println();
		
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
		campgroundsMenu();
	}
	
	public void campgroundsMenu() {
		
		int parkInput = 0;
		while(!userIn.hasNextInt()) {
			if(userIn.next().toUpperCase().equals("Q")) {
				System.exit(1);
			}
			System.out.println("Please select valid park!");
			userIn.next();
		}
		parkInput = userIn.nextInt() - 1;
		
		System.out.println();
		
		System.out.println("Park Information Screen");
		System.out.println(allParks.get(parkInput).getParkName());
		System.out.println(allParks.get(parkInput).getLocation());
		System.out.println(allParks.get(parkInput).getEstablishedDate());
		System.out.println(allParks.get(parkInput).getArea());
		System.out.println(allParks.get(parkInput).getAnnualVisitors());
		System.out.println();
		System.out.println(allParks.get(parkInput).getParkDescription());
		System.out.println();
		System.out.println("Select a command");
		System.out.println("1) View Camgrounds");
		System.out.println("2) Search Reservation");
		System.out.println("3) Return to previous screen");
		userInput = userIn.nextLine();
		userInput = userIn.nextLine();
		//userInput = userIn.nextLine();
		//System.out.println("I'm HERE");
		
		
		if(userInput.equals("1")) {
			campgroundMenu(parkInput);
		}
		else if(userInput.equals("2")) {
			
			
		}
		else if(userInput.equals("3")) {
			startMenu();
		}
	}
	
	public void campgroundMenu(int parkInput) {
		
		allCampgrounds = jdbcCampgroundDao.getAllCampgrounds(allParks.get(parkInput).getParkId());
		int optNum = 1;
		
		//System.out.format("%32s%10d%16s", string1, int1, string2);
		System.out.format("%12s%16s%16s%14s", "Name", "Open Month", "Close Month", "Daily Fee" + "\n");
		for(Campground cGround : allCampgrounds) {
			System.out.println("#" + optNum +") " + cGround.getCampgroundName() + "      " + cGround.getOpenMonth() + "      " +
							 cGround.getCloseMonth() + "     $" + cGround.getDailyFee());
			//System.out.print("#" + optNum + ") ");
			//System.out.format("%16s%16d%16d%16d%n", cGround.getCampgroundName() + cGround.getOpenMonth() +
			//		 cGround.getCloseMonth() + cGround.getDailyFee());
			optNum++;
		}
		System.out.println();
		System.out.println("Select a command");
		System.out.println("1) Search for Available Reservation");
		System.out.println("2) Return to Previous Screen");
		
		userInput = userIn.nextLine();
		while(!userIn.hasNextInt()) {
			System.out.println("Please select a valid command");
			userIn.next();
		}
		if(userInput.equals("1")) {
			//Ask for desired campground
			System.out.println("Which campground would you like a reservation at? ");
			long userCamp = Long.parseLong(userIn.nextLine());
			//Ask for starting month
			System.out.println("What months are you looking at?");
			System.out.println("Starting Month (01, 02, etc): ");
			int userStart = Integer.parseInt(userIn.nextLine());
			//Ask for ending month
			System.out.println("Ending Month (01, 02, etc): ");
			int userEnd = Integer.parseInt(userIn.nextLine());
			//Returns list via mostPopularSites
			mostReservations = jdbcSiteDao.mostPopularSites(userCamp, userStart, userEnd);
			for(Site site : mostReservations) {
				site.toString();
			}
			
		}
		else if(userInput.equals("2")) {
			campgroundsMenu();
		}
	}
}
