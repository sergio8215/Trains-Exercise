package trains.exercise;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import trains.exercise.exception.DestinationAlreadyExistsException;

public class Graph implements Cloneable{
	
	
	// Compute distance following a route
	private Map<String, Map<String, Integer> > graph;
	
	// Compute shortest distance
	private Map<String, PriorityQueue<Destination> > graphP;
	private Map<String, Integer> minimumWeight; //D
	private Map<String, Town> sucesors;
	
	private Set<String> candidates;
	private Set<String> visited;
	

	
	public Graph() {
		graph = new HashMap<String, Map<String, Integer> >();
		graphP = new HashMap<String, PriorityQueue<Destination> >();
		minimumWeight = new HashMap<String, Integer>();
		sucesors = new HashMap<String, Town>();
		candidates = new HashSet<String>();
		visited = new HashSet<String>(); 
	}
	
	public Graph(Map<String, Map<String, Integer> > graph) {
		this.graph = graph;
	}

    public Graph clone() throws
		CloneNotSupportedException { 
    	return (Graph)super.clone(); 
    }
    

	public Map<String, PriorityQueue<Destination>> getGraphP() {
		return graphP;
	}

	public Map<String, Integer> getMinimumWeight() {
		return minimumWeight;
	}

	public Map<String, Town> getSucesors() {
		return sucesors;
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
	
	/**
	 * Pre: Exists values of routes, the syntax of the routes is correct.
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
				PriorityQueue<Destination> pq= new PriorityQueue<Destination>(new DestinationComparator());
				pq.add(d);
				graphP.put(start, pq );
				minimumWeight.put(start, 0);
				candidates.add(start);
			
			}
		}
	}
}
