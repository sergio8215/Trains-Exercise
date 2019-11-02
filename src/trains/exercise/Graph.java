package trains.exercise;
import java.util.HashMap;
import java.util.Map;

import trains.exercise.exception.DestinationAlreadyExistsException;

public class Graph {
	Map<Character, Map<Character, Integer> > graph;
	
	public Graph() {
		graph = new HashMap<Character, Map<Character, Integer> >();
	}
	
	public Graph(Map<Character, Map<Character, Integer> > graph) {
		this.graph = graph;
	}
	
	/**
	 * Pre: Exists values of routes, the syntax of the routes is correct.
	 * @param routes
	 * @throws DestinationAlreadyExistsException
	 * @throws IllegalArgumentException
	 */
	public void generateGraph( String[] routes ) throws DestinationAlreadyExistsException,IllegalArgumentException{

		for(String route: routes) {
			
			Character start = IO.getStart(route);
			Character end = IO.getEnd(route);
			Integer weight = IO.getWeigth(route);

			// If the start city already exists, we add the new destination
			if( graph.containsKey(start) ){
				
				// If the destination doesn't exists yet, we add it
				if( !graph.get(start).containsKey(end) ) {
					graph.get(start).put( end, weight );
			
				}else {
					throw new DestinationAlreadyExistsException( "The route '" + IO.getStart(route) + IO.getEnd(route) + "' already exists");
				
				}
			}else {					
				HashMap<Character, Integer> destination = new HashMap<Character, Integer>();
				destination.put(end, weight);
				graph.put( start, destination);
			
			}
		}
	}
	
	public Map<Character, Map<Character, Integer> > getGraph(){
		return graph;
	}
}
