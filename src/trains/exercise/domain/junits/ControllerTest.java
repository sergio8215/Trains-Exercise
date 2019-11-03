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
	
	public boolean computeDistanceAndValidateTest( String in, String out, Controller c ) {
		boolean result = false;
		try {
			result = String.valueOf(c.computeDistanceAndValidate(in)).equals(out);
		} catch (IllegalArgumentException | DestinationAlreadyExistsException | InvalidRouteException e) {}
		return result;
	}
	
	public void readTestFileComputeDistance( String fileName, Controller c ) throws FileNotFoundException, IllegalArgumentException, InvalidRouteException{
	    
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
	
	public boolean computeShortestRouteAndValidateTest( String in, String out, Controller c ) {
		boolean result = false;
		try {
			
			result = printShortestPathTest(c.computeShortestRouteAndValidate(in)).equals(out);
		} catch (IllegalArgumentException | DestinationAlreadyExistsException | InvalidRouteException
				| CloneNotSupportedException e) {}
		return result;
	}
	
	
	public void readTestFilecomputeShortestRoute( String fileName, Controller c ) throws FileNotFoundException, IllegalArgumentException, InvalidRouteException{
	    
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
	

	public String printShortestPathTest(List<Town> sp) {
		int size = sp.size();
		StringBuilder sb = new StringBuilder();
		if ( size>1 ) {
			for( int i = 1; i <= size; i++) {
				if( i != size ) {
					sb.append(sp.get(size-i).getName()+"-" );
				}else {
					sb.append(( sp.get(size-i).getName()));
				} 
			}			
		}else {
			sb.append("There is no possible route");
		}
		return sb.toString();
	}
	
}
