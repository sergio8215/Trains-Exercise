package trains.exercise.domain.junits;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import trains.exercise.domain.classes.Town;
import trains.exercise.domain.controller.Controller;
import trains.exercise.domain.exception.DestinationAlreadyExistsException;
import trains.exercise.domain.exception.InvalidRouteException;

public class ControllerTest {
	
	/**
	 * Test the computation of distance between two towns
	 * @param in
	 * @param out
	 * @param c
	 * @return true when the the output is as expected
	 */
	public boolean computeDistanceAndValidateTest( String in, String out, Controller c ) {
		boolean result = false;
		try {
			result = (c.computeDistanceAndValidate(in)).equals(out);
		} catch (IllegalArgumentException | DestinationAlreadyExistsException | InvalidRouteException e) {}
		return result;
	}
	
	/**
	 * Reads the input file and execute the distance between two town test
	 * @param fileName
	 * @param c
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException
	 * @throws InvalidRouteException
	 */
	public void readTestFileComputeDistance( String fileName, Controller c ) throws
		FileNotFoundException, IllegalArgumentException, InvalidRouteException{
	    
		File file = new File(fileName); 
	    Scanner s = new Scanner(file);
	    String in = "";
	    String out= "";
	    while(s.hasNext()) {
	    	in = s.nextLine();
		    out = s.nextLine();
		    System.out.println("Result of the test: "+computeDistanceAndValidateTest(in, out, c));
	    }
	    s.close();
	  } 
	
	/**
	 * Test computation of shortest route
	 * @param in
	 * @param out
	 * @param c
	 * @return true when the the output is as expected
	 */
	public boolean computeShortestRouteAndValidateTest( String in, String out, Controller c ) {
		boolean result = false;
		try {
			result = toStringShortestPathTest(c.computeShortestRouteAndValidate(in)).equals(out);
		} catch (IllegalArgumentException | DestinationAlreadyExistsException | InvalidRouteException
				| CloneNotSupportedException e) {}
		return result;
	}
	
	/**
	 * Reads the input file and execute the test
	 * @param fileName
	 * @param c
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException
	 * @throws InvalidRouteException
	 */
	public void readTestFilecomputeShortestRoute( String fileName, Controller c ) throws
		FileNotFoundException, IllegalArgumentException, InvalidRouteException{
	    
		File file = new File(fileName); 
	    Scanner s = new Scanner(file);
	    String in = "";
	    String out= "";
	    while(s.hasNext()) {
	    	in = s.nextLine();
		    out = s.nextLine();
		    System.out.println("Result of the test: "+computeShortestRouteAndValidateTest(in, out, c));
	    }
	    s.close();
	  } 
	
	/**
	 * Transform the output to string
	 * @param towns
	 * @return the output as a string
	 */
	public String toStringShortestPathTest(List<Town> towns) {
		int size = towns.size();
		StringBuilder sb = new StringBuilder();
		if ( size>1 ) {
			for( int i = 1; i <= size; i++) {
				if( i != size ) {
					sb.append(towns.get(size-i).getName()+"-" );
				}else {
					sb.append(( towns.get(size-i).getName()));
				} 
			}			
		}else {
			sb.append("There is no possible route");
		}
		return sb.toString();
	}
}
