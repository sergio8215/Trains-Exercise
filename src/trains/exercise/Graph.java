package trains.exercise;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import trains.exercise.exception.DestinationAlreadyExistsException;
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
	private Map<String, Integer> minimumWeight;
	private Map<String, Town> successor;
	
	private Set<String> candidates;
	private Set<String> visited;
	
	// Number of different routes between two towns
	private List<Boolean> visitedDFS;
	private List<Integer> ndfs;
	private List<Integer> ninv;
	private int num_dfs = 0;
	private int num_inv = 0;

	/**
	 * Empty constructor method
	 */
	public Graph() {
		
		graph = new HashMap<String, Map<String, Integer> >();
		graphP = new HashMap<String, List<Destination> >();
		minimumWeight = new HashMap<String, Integer>();
		successor = new HashMap<String, Town>();
		candidates = new HashSet<String>();
		visited = new HashSet<String>(); 
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
				
				// If the destination doesn't exists yet, we add it
				if( !graph.get(start).containsKey(end) ) {
					graph.get(start).put( end, weight );
					Destination d = new Destination(new Town(end),weight);
					graphP.get(start).add(d);
					
				}else {
					throw new DestinationAlreadyExistsException( "The route '" + IO.getStart(route) + IO.getEnd(route) + "' already exists");
				
				}
			}else {					
				HashMap<String, Integer> destination = new HashMap<String, Integer>();
				destination.put(end, weight);
				graph.put( start, destination);
				Destination d = new Destination(new Town(end),weight);
				List<Destination> destinations = new ArrayList<Destination>();
				destinations.add(d);
				graphP.put(start, destinations );
				minimumWeight.put(start, 0);
				candidates.add(start);
			
			}
		}
	}
	
	/* Getters */
	public Map<String, List<Destination>> getGraphP() {
		return graphP;
	}

	public Map<String, Integer> getMinimumWeight() {
		return minimumWeight;
	}

	public Map<String, Town> getSuccessor() {
		return successor;
	}

	public Set<String> getCandidates() {
		return candidates;
	}

	public Set<String> getVisited() {
		return visited;
	}

	public Map<String, Map<String, Integer> > getGraph(){
		return graph;
	}

	public List<Boolean> getVisitedDFS() {
		return visitedDFS;
	}

	public List<Integer> getNdfs() {
		return ndfs;
	}

	public List<Integer> getNinv() {
		return ninv;
	}

	public int getNum_dfs() {
		return num_dfs;
	}

	public int getNum_inv() {
		return num_inv;
	}	
}
