package trains.exercise.domain.classes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import trains.exercise.domain.exception.DestinationAlreadyExistsException;
import trains.exercise.presentation.IO;
/**
 * Represents a directed graph, with nodes as towns and edges with weight
 * @author Sergio
 *
 */
public class Graph{
	
	// Compute distance following a route
	private Map<String, Map<String, Integer> > graph;
	
	// Compute shortest distance
	private Map<String, List<Destination> > graphP;
	
	/**
	 * Empty constructor method
	 */
	public Graph() {
		graph = new HashMap<String, Map<String, Integer> >();
		graphP = new HashMap<String, List<Destination> >();
	}
	

	/**
	 * Generates a graph from the user routes entry.
	 * @param routes
	 * @throws DestinationAlreadyExistsException
	 * @throws IllegalArgumentException
	 */
	public void generateGraph( String[] routes ) throws
		DestinationAlreadyExistsException,IllegalArgumentException{
		
		for(String route: routes) {
			
			String start = IO.getStart(route);
			String end = IO.getEnd(route);
			Integer weight = IO.getWeigth(route);

			// If the start city already exists, we add the new destination
			if( graph.containsKey(start) ){
				
				// If the destination doesn't exist, we add it
				if( !graph.get(start).containsKey(end) ) {
					graph.get(start).put( end, weight );
					Destination d = new Destination(new Town(end),weight);
					graphP.get(start).add(d);
					
				}else {
					throw new DestinationAlreadyExistsException(
							"The route '" + IO.getStart(route) + IO.getEnd(route) + "' already exists");
				
				}
			}else {					
				Town s = new Town(start);
				Town e = new Town(end);
				initializeDestinationsGraph(s,e,weight);
				initializeDestinationsGraphP(s,e,weight);
			}
		}
	}
	
	/**
	 * Initialize graph with list of destinations for a given town.
	 * @param start town
	 * @param end town
	 * @param weight of the route
	 */
	private void initializeDestinationsGraphP(Town start, Town end, int weight){
		Destination d = new Destination(end,weight);
		List<Destination> destinations = new ArrayList<Destination>();
		destinations.add(d);
		graphP.put(start.getName(), destinations );
	}
	
	/**
	 * Initialize list of destination for a given town tree way.
	 * @param start town
	 * @param end town
	 * @param weight of the route
	 */
	private void initializeDestinationsGraph(Town start, Town end, int weight){
		HashMap<String, Integer> destination = new HashMap<String, Integer>();
		destination.put(end.getName(), weight);
		graph.put( start.getName(), destination);
	}
	
	/* Getters */
	public Map<String, List<Destination>> getGraphP() {
		return graphP;
	}

	public Map<String, Map<String, Integer> > getGraph(){
		return graph;
	}
}
