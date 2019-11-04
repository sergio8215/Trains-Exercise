package trains.exercise.presentation;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import trains.exercise.domain.classes.Graph;
import trains.exercise.domain.classes.Town;
import trains.exercise.domain.exception.InvalidRouteException;

public class IO {
	
	/**
	 * Reads a file line and validates the information
	 * @param fileName to read
	 * @return list of town names
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException
	 * @throws InvalidRouteException
	 */
	public static String[] readGraphFile( String fileName ) throws FileNotFoundException, IllegalArgumentException, InvalidRouteException{
	    
		File file = new File(fileName); 
	    Scanner s = new Scanner(file); 
	    
    	String in = s.nextLine();
		String[] parts = in.toUpperCase().split("\\s+|,\\s*");
    
		for( String route: parts ) IO.validateData(route);
		s.close();
	    
	    return parts;
	  } 
	
		
	/**
	 * Reads graph information from the console line
	 * @param in - input from the console line
	 * @return list of town names
	 * @throws IllegalArgumentException
	 * @throws IllegalArgumentException
	 * @throws InvalidRouteException
	 */
	public static String[] readGraphManual(String in) throws IllegalArgumentException, IllegalArgumentException, InvalidRouteException{
		
    	String[] parts = in.toUpperCase().split("\\s+|,\\s*");
        for( String route: parts ) IO.validateData(route);
		return parts;
	}
	
	/**
	 * Prints a given graph with the format TownTownWeight,TownTownWeight
	 * @param graph
	 */
	public static void printGraph( Graph graph ){
		int size = graph.getGraph().size();
		StringBuilder sb = new StringBuilder(); 
		int weight = 0;
		for( String start: graph.getGraph().keySet() ) {
			for( String end : graph.getGraph().get(start).keySet() ) {
				weight = graph.getGraph().get(start).get(end);
				sb.append( start + end + String.valueOf(weight) + ", " );
			}
		}
		sb.delete(sb.length()-2, sb.length());
		System.out.println(sb.toString());
	}
	
	/**
	 * Prints the shortest route between two towns
	 * @param sp route of towns 
	 */
	public static void printShortestPath(List<Town> sp) {
		int size = sp.size();
		if ( size>1 ) {
			for( int i = 1; i <= size; i++) {
				if( i != size ) {
					System.out.print( sp.get(size-i).getName()+"-" );
				}else {
					System.out.print( sp.get(size-i).getName());
				} 
			}			
		}else {
			System.out.println("There is no possible route");
		}
	}

	/**
	 * Prints the shortest route between two towns
	 * @param sp route of towns 
	 */
	public static void printNumberDifferentRoutes(int routes) {
		
		if ( routes>0 ) {
			System.out.println(routes);			
		}else {
			System.out.println("There is no possible route");
		}
	}
	
	/**
	 * Reads and validates list of towns, each town is one letter 
	 * @param towns names
	 * @return list of towns validated
	 * @throws InvalidRouteException  invalid town name in the route given
	 * @throws IllegalArgumentException invalid character
	 */
	public static String[] readRoute(String towns) throws
		IllegalArgumentException, InvalidRouteException {
		String[] townsList = towns.toUpperCase().split("\\s+|-\\s*");
		if( townsList.length>1 ) {
			for ( String town: townsList ) validateTown(town);

		}else {
			throw new InvalidRouteException("To compute distance, the entry should be minimum 2 towns");
		}
	   return townsList;
	}
	
	/**
	 * Validates if the input is with the format TownTown 
	 * @param in - input to validate
	 * @return if the input is correct, return a list of 2 towns
	 * @throws IllegalArgumentException
	 * @throws InvalidRouteException
	 */
	public static String[] validateTwoTownRoute(String in) throws IllegalArgumentException, InvalidRouteException {
		String[] towns = in.toUpperCase().split("");
	
		if( towns.length == 2 ) {
			validateTown(towns[0]);
			validateTown(towns[1]);
			isNotLoopRoute(towns[0], towns[1]);
		}else {
			throw new InvalidRouteException("To compute distance, the entry should be 2 towns");
		}
	   return towns;
	}
	
	/**
	 * Gets the start town
	 * @param town
	 * @return
	 */
	public static String getStart(String town){
		return Character.toString(town.charAt(0));
	}
	
	/**
	 * Gets the end town
	 * @param town
	 * @return
	 */
	public static String getEnd(String town){
		return Character.toString(town.charAt(1));
	}
	
	/**
	 * Gets the weight of the edge
	 * @param weight
	 * @return
	 */
	public static int getWeigth(String weight){
		return Character.getNumericValue(weight.charAt(2));
	}
	
	/**
	 * Validates that the town format is one letter a-z or A-Z
	 * @param town
	 * @return true if the format is correct
	 * @throws InvalidRouteException
	 * @throws IllegalArgumentException
	 */
	public static boolean validateTown( String town ) throws InvalidRouteException, IllegalArgumentException{
		if (town.length() == 1&&isLetter(town.charAt(0))) return  true;
		throw new InvalidRouteException("The town name '"+town+"' is invalid");
	}
	
	/**
	 * Validates the origin town, destination town and weight are with correct format
	 * @param data
	 * @return true if the format is correct
	 * @throws InvalidRouteException
	 */
	public static boolean validateData( String data ) throws InvalidRouteException{
	    if(data.length() == 3) return 	isLetter(data.charAt(0)) &&
	    								isLetter(data.charAt(1)) &&
	    								isNumber(data.charAt(2));
		throw new InvalidRouteException("The route '"+data+"' is invalid, please use the format LetterLetterNumber, ex: AB4");
	}
	
	/**
	 * Validates the input is a letter
	 * @param s
	 * @return true if is a letter a-z A-Z
	 */
	private static boolean isLetter( char s ){
		if ( Character.isLetter(s)) return true; 
		throw new IllegalArgumentException("Name of town '"+ s +"' should be a letter");
	}
	
	/**
	 * Validates the input as a number
	 * @param i
	 * @return true if is a number
	 */
	private static boolean isNumber( char i ) {
		if ( Character.isDigit(i) ) return true; 
		throw new IllegalArgumentException("The weight of the route should be a number, now it's '"+ i +"'");
	}
	
	/**
	 * Validates that the route it is not the same start and end
	 * @param s
	 * @param d
	 * @return true if is not a loop route
	 */
	private static boolean isNotLoopRoute( String s, String d ) {
		if ( !s.equals(d) ) return true; 
		throw new IllegalArgumentException("A route from '"+s+"' to it self is not possible");
	}
}
