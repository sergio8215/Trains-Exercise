package trains.exercise.presentation;

import java.io.FileNotFoundException;
import java.util.Scanner;

import trains.exercise.domain.controller.Controller;
import trains.exercise.domain.exception.DestinationAlreadyExistsException;
import trains.exercise.domain.exception.InvalidRouteException;
import trains.exercise.domain.test.ControllerTest;

public class Menu {
	
	private static Controller c = new Controller();
	
	/**
	 * Manage the menu and options 
	 * @return option chosen
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException
	 * @throws InvalidRouteException
	 * @throws DestinationAlreadyExistsException
	 * @throws CloneNotSupportedException
	 */
	public static String start() throws
		FileNotFoundException, IllegalArgumentException, InvalidRouteException, DestinationAlreadyExistsException, CloneNotSupportedException {
		
		Scanner s = new Scanner(System.in);
		String option = s.nextLine();
		Integer op = 0;		
		
		try {
			op = Integer.valueOf(option);
			switch(op) {
				case 0:
					printMenu();
					break;
				case 1:
					System.out.println("For the input, the towns are named using one letter of the alphabet from A to Z. A route between two towns (A to B) with a\r\n" + 
					        "distance of 5 is represented as AB5.\r\n" + 
					        "\r\n" + 
					        "Example Graph: AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7\r\n");
					System.out.println("Option 2. If you want to introduce the data with an input file, you need to write the Graph in one line separating the routes with coma ',' as the example. You'll be ask for the absolute route of the file, example: C:\\user\\pepito\\input.txt");
					System.out.println("Option 3. If you want to introduce the data manually, you can write the Graph in one line separating the routes with coma ','.");
					System.out.println("Option 4. The input data should be with the format: town-town-town-town, ex: A-B-D-F");
					System.out.println("Option 5. The input data should be with the format: TownTown, ex: AB");
					System.out.println("Option 6. The input data should be with the format: TownTown, ex: AB");
					System.out.println("Option 7. Test the distance along certain path, the format of the file should be, one line the path, next line expected answer, can be more than one test. In the same file.");
					System.out.println("Option 8. Test the shortest path between two towns, the format of the file should be, one line the path, next line expected answer, can be more than one test. In the same file.");
					System.out.println("Option 9. Exit");
					break;
				case 2:
					System.out.println("Please insert the routes information: ");
					c.generateGraph(IO.readGraphManual(s.nextLine()));
					c.printGraph();
					c.setGraphLoaded(true);
					System.out.println("\nThe graph have been generated successfully");
					break;
				case 3:
					System.out.println("Please enter fullpath of the file: ");
					c.generateGraph(IO.readGraphFile(s.nextLine()));
					c.printGraph();
					c.setGraphLoaded(true);
					System.out.println("\nThe graph have been generated successfully");
					break;
				case 4:
					if(c.isGraphLoaded()) {
						System.out.println("Please introduce the route to calculate the distance: ");
						String result = c.computeDistanceAndValidate(s.nextLine());
							System.out.println(result);
						
					}else {
						System.out.println("First you need to load the graph");
					}
					break;
				case 5:
					if(c.isGraphLoaded()) {
						System.out.println("Please introduce the start town and destination town to calculate the number of routes: ");
						IO.printNumberDifferentRoutes(c.numberDifferentRoutesAndValidate(s.nextLine()));
					}else {
						System.out.println("First you need to load the graph");
					}
					break;
				case 6:
					if(c.isGraphLoaded()) {
						System.out.println("Please introduce the start town and destination town to calculate the shortest route:");
						IO.printShortestPath(c.computeShortestRouteAndValidate(s.nextLine()));
					}else {
						System.out.println("First you need to load the graph");
					}
				   	break;
				case 7:
					if(c.isGraphLoaded()) {
						System.out.println("Test the distance between two towns along a certain path. First line of the file the input, second line expected output ");
						System.out.println("Please enter fullpath of the file: ");
						ControllerTest ct = new ControllerTest();
						ct.readTestFileComputeDistance(s.nextLine(), c);
						
					}else {
						System.out.println("First you need to load the graph");
					}
					break;
				case 8:
					if(c.isGraphLoaded()) {
						System.out.println("Test the shortest path between two towns. First line of the file the input, second line expected output ");
						System.out.println("Please enter fullpath of the file: ");
						ControllerTest ct = new ControllerTest();
						ct.readTestFileNumberDifferentRoutes(s.nextLine(), c);
						
					}else {
						System.out.println("First you need to load the graph");
					}
					break;
				case 9:
					if(c.isGraphLoaded()) {
						System.out.println("Test the number of different routes between two towns. First line of the file the input, second line expected output ");
						System.out.println("Please enter fullpath of the file: ");
						ControllerTest ct = new ControllerTest();
						ct.readTestFilecomputeShortestRoute(s.nextLine(), c);
						
					}else {
						System.out.println("First you need to load the graph");
					}
					break;					
				case 10:
					// Exit
					break;
				default:
					System.err.println("That option doesn't exist, please select one option in the menu");
					break;
			}
			System.out.println("\nPress 0 to show the menu");
		}catch(NumberFormatException e){
			System.err.println("The option it's not a number, please select one option within the menu");
			printMenu();
		}
		return option;
	}
	
	/**
	 * Prints the menu options
	 */
	public static void printMenu(){
		System.out.println("\nPlease press");
		System.out.println("0. Print the menu");
		System.out.println("1. If you need help");
		System.out.println("2. Introduce input graph data manually" );
		System.out.println("3. Introduce input graph data with a file" );
		System.out.println("4. Calculate distance along route" );
		System.out.println("5. Number of different routes between two towns" );
		System.out.println("6. Shortest route between two towns" );
		System.out.println("7. Test the distance between two towns along a certain path" );
		System.out.println("8. Test number of different routes between two towns" );
		System.out.println("9. Test the shortest path between two towns" );
		System.out.println("10. Exit" );
	}
	
}
