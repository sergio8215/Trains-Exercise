import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IO {
	
	public static List<Route> readGraph(){
		
		Scanner s = new Scanner(System.in);
		System.out.println("Welcome to the local commuter railroad services information");
		System.out.println("Please insert the Towns and routes information: ");
		List<Route> routes = new ArrayList<Route>();
		Route route = null;
		
		// TODO: Verificaciones de finalizar, y de que los datos introducidos sean correctos.
		int size = s.nextInt();
		for (int i = 0; i<size ; i++) {
			try {
				route = validateData(s.next());
				routes.add(route);
				
			}catch( IllegalArgumentException e ) {
				System.err.println( "Error reading the data: " + e.getMessage());
			}
		}
		s.close();
		return routes;

	}
	
	private static Route validateData( String data ) {
	    
		Town start = null;
	    Town ends  = null;
	    int weight = 0;

	    if( isLetter(data.charAt(0)) ) start = new Town( String.valueOf( data.charAt(0) ));
	    if( isLetter(data.charAt(1)) ) ends	 = new Town( String.valueOf( data.charAt(1) ));
	    if( isNumber(data.charAt(2)) ) weight= Integer.valueOf( data.charAt(2) );
	    return new Route(start, ends, weight);
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
