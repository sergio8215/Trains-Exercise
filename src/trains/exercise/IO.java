package trains.exercise;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import trains.exercise.exception.InvalidRouteException;

public class IO {
	
	
	public static String[] readGraphFile( String fileName ) throws FileNotFoundException, IllegalArgumentException, InvalidRouteException{
	    
		// TODO: pass the path to the file as a parameter
		File file = new File("C:\\Users\\Sergio\\eclipse-workspace\\Trains Exercise\\files\\input.txt"); 
	    Scanner s = new Scanner(file); 
	    
    	String in = s.nextLine();
		String[] parts = in.toUpperCase().split("\\s+|,\\s*");
    
		for( String route: parts ) IO.validateData(route);
		s.close();
	    
	    return parts;
	  } 
		
	
	public static String[] readGraphManual(String in) throws IllegalArgumentException, IllegalArgumentException, InvalidRouteException{
		
    	String[] parts = in.toUpperCase().split("\\s+|,\\s*");
        for( String route: parts ) IO.validateData(route);
		
		return parts;
	}
	
	public static void printGraph( Graph graph ){
		
		int size = graph.getGraph().size();
		int i = 1;
		
		for( Character start: graph.getGraph().keySet() ) {
			for( Character end : graph.getGraph().get(start).keySet() ) {
				int weight = graph.getGraph().get(start).get(end) ;
				String print = String.valueOf(start) + String.valueOf(end) + String.valueOf(weight);
				System.out.print( print );
				if (i < size) System.out.print( ", " );
			}
			i++;
		}
	}
	
	/**
	 * Reads and validates list of towns, each town is one letter 
	 * @param towns names
	 * @return list of towns validated
	 * @throws InvalidRouteException  invalid town name in the route given
	 * @throws IllegalArgumentException invalid character
	 */
	public static String[] readRoute(String towns) throws IllegalArgumentException, InvalidRouteException {
		String[] townsList = towns.toUpperCase().split("\\s+|-\\s*");
		if( townsList.length>1 ) {
			for ( String town: townsList ) validateTown(town);

		}else {
			throw new InvalidRouteException("To compute distance, the entry should be minimum 2 towns");
		}
	   return townsList;
	}
	
	public static Character getStart(String town){
		return town.charAt(0);
	}
	public static Character getEnd(String town){
		return town.charAt(1);
	}
	public static int getWeigth(String weight){
		return Character.getNumericValue(weight.charAt(2));
	}
	
	public static boolean validateTown( String town ) throws InvalidRouteException, IllegalArgumentException{
		if (town.length() == 1&&isLetter(town.charAt(0))) return  true;
		throw new InvalidRouteException("The town name '"+town+"' is invalid, please use the format LetterLetter, ex: AB");
	}
	
	public static boolean validateData( String data ) throws InvalidRouteException{
	    if(data.length() == 3) return isLetter(data.charAt(0)) && isLetter(data.charAt(1)) && isNumber(data.charAt(2));
		throw new InvalidRouteException("The route '"+data+"' is invalid, please use the format LetterLetterNumber, ex: AB4");
	}
	
	private static boolean isLetter( char s ){
		if ( Character.isLetter(s)) return true; 
		throw new IllegalArgumentException("Name of town '"+ s +"' should be a letter");
	}
	
	private static boolean isNumber( char i ) {
		if ( Character.isDigit(i) ) return true; 
		throw new IllegalArgumentException("The weight of the route should be a number, now it's '"+ i +"'");
	}
}
