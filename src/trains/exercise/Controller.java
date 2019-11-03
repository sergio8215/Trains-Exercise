package trains.exercise;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import trains.exercise.exception.DestinationAlreadyExistsException;
import trains.exercise.exception.InvalidRouteException;

public class Controller {
	
	private final int INFINITY = 999999;
	
	private Graph graph;

	/**
	 * Empty constructor
	 * @param graph
	 */
	public Controller() {
		this.graph = new Graph();
	}
	
	/**
	 * Function that capture errors and call the menu
	 */
	public void start() {
		
		Menu.printMenu();
		String option = "0";
		
		while( !option.equals("7") ) {
			try {
				option = Menu.start();
				
			} catch (FileNotFoundException e) {
				System.err.println( "Error reading the data: File not found");
				Menu.printMenu();
			} catch( IllegalArgumentException e ) {
				System.err.println( "Error reading the data: " + e.getMessage());
				Menu.printMenu();				
			} catch (DestinationAlreadyExistsException e) {
				System.err.println( "Error reading the data: " + e.getMessage());
				Menu.printMenu();
			} catch (InvalidRouteException e) {
				System.err.println( "Error reading the data: " + e.getMessage());
				Menu.printMenu();
			} catch (CloneNotSupportedException e) {
				System.err.println( "Error computing shortest path ");
				Menu.printMenu();
			}
		}
	}
	
	/**
	 * Compute the distance between to towns along a certain route.
	 * @param in Route to compute distance
	 * @return Calculated distance or NO SUCH ROUTE if the route doesn't exist
	 * @throws IllegalArgumentException wrong city name
	 * @throws DestinationAlreadyExistsException Town already exist
	 * @throws InvalidRouteException Invalid route to compute
	 */
	public int computeDistance(String in) throws
			IllegalArgumentException, DestinationAlreadyExistsException, InvalidRouteException  {
		int distance = 0;
		String[] townsList = IO.readRoute(in);
		
		for( int i = 0; i < townsList.length-1; i++ ) {
			if( containsTown(townsList[i]) && 
				containsDestinationTown(townsList[i], townsList[i+1])) {
				distance += graph.getGraph().get(townsList[i]).get(townsList[i+1]);
				
			}else {
				distance = -1;
				break;
			}
		}
		return distance;
	}
	
	private int computeDistanceChild(  ) {
		return 0;
	}
	
	/**
	 * Check if town exists in the graph
	 * @param town
	 * @return
	 */
	private boolean containsTown(String town){
		return graph.getGraph().
				containsKey(town);
	}
	
	/**
	 * Gets town routes from the graph
	 * @param town
	 * @return
	 */
	private Map<String,Integer> getTown(String town) {
		return graph.getGraph().
				get(town);
	}
	
	/**
	 * Check if a destination town exists in the graph
	 * @param town
	 * @return
	 */
	private boolean containsDestinationTown(String townStart, String townEnd){
		return getTown(townStart).containsKey(townEnd);
	}
	
	/**
	 * Compute the shortest route between two towns
	 * @param in
	 * @return Shortest route between two towns
	 * @throws IllegalArgumentException
	 * @throws DestinationAlreadyExistsException
	 * @throws InvalidRouteException
	 * @throws CloneNotSupportedException
	 */
	public List<Town> computeShortestRoute(String in) throws
		IllegalArgumentException, DestinationAlreadyExistsException, InvalidRouteException, CloneNotSupportedException  {
		
		String[] towns = IO.validateShortestRoute(in);
		Town start = new Town(towns[0]);
		Town end =  new Town(towns[1]);
		
		// Initialize all distances to infinity
		graph.getMinimumWeight().entrySet().forEach(entry-> {
			entry.setValue(INFINITY);
		});
		// If the algorithm was executed before, we initialize the structures
		if( graph.getCandidates().isEmpty() ) initializeStructuresDijkstra();
		
		// Start point of the algorithm
		graph.getMinimumWeight().put(start.getName(), 0);
		
		// For each candidate we find the shortest path
		while( graph.getCandidates().size() != 0 ) {
			
			Destination cand = getMinimumCand();
			// we remove the candidate from the unvisited list
			graph.getCandidates().remove(cand.getTown().getName());
			
			int size = graph.getGraphP().get(cand.getTown().getName()).size();
			
			for( int i=0; i < size; i++) {
				// checking all neighbor weights
				Destination neighbor = graph.getGraphP().get(cand.getTown().getName()).get(i);
				updateMinimumWeight(neighbor, cand);
			}
			// We add the candidate to the visited list
			graph.getVisited().add(cand.getTown().getName());
		}
		
		return shortestRoute(start, end);
	}
	
	/**
	 * Updates the minimum weight table with the new weight computation
	 * @param neighbor
	 * @param cand
	 */
	private void updateMinimumWeight(Destination neighbor, Destination cand) {
		int w = 0;
		
		if( isInfinity(neighbor) ){
			
			w = cand.getWeight() + neighbor.getWeight();
			
			graph.getMinimumWeight().put(neighbor.getTown().getName(), w);
			graph.getSucesors().put(neighbor.getTown().getName(), cand.getTown());
		
		}else {
			
			w = graph.getMinimumWeight().get(cand.getTown().getName()) + neighbor.getWeight();
			
			if( w < graph.getMinimumWeight().get(neighbor.getTown().getName())) {
				graph.getMinimumWeight().put(neighbor.getTown().getName(), w);
				graph.getSucesors().put(neighbor.getTown().getName(), cand.getTown());
			}
		}
	}
	
	/**
	 * After applying the algorithm one time, we have to reset this structures to the initial values
	 */
	private void initializeStructuresDijkstra() {
		graph.getCandidates().addAll(graph.getVisited());
		graph.getVisited().clear();
		graph.getSucesors().clear();
	}
	
	/**
	 * Gets the candidate with minimum weight
	 * @return candidate
	 */
	private Destination getMinimumCand() {
		int minimum = INFINITY+1;
		int weight = INFINITY;
		String result = "";
		
		for( String cand: graph.getCandidates() ) {
			
			weight = graph.getMinimumWeight().get(cand);
			if ( weight < minimum ) {
				result = cand;
				minimum = weight;
			}
		}
		return new Destination(new Town(result), minimum);
	}
	
	/**
	 * Check if a destination weight is infinity
	 * @param d
	 * @return true if is infinity
	 */
	private boolean isInfinity( Destination d ) {
		return graph.getMinimumWeight().get(d.getTown().getName()) == INFINITY;
	}
	
	/**
	 * Creates the route from one town to other, with the successor list
	 * @param start point
	 * @param end point
	 * @return Ordered list with the route
	 */
	private List<Town> shortestRoute(Town start, Town end){
		List<Town> result = new ArrayList<Town>();
		Town sucesor = graph.getSucesors().get(end.getName());
		result.add(end);
		
		if(sucesor != null) {			
			result.add(sucesor);
			while( !(sucesor.getName()).equals(start.getName()) ) {
				
				sucesor = graph.getSucesors().get(sucesor.getName());
				result.add(sucesor);
			}
		}
		return result;
	}
	
	/**
	 * Call necessary method to generate a graph
	 * @param in list of towns
	 * @throws IllegalArgumentException
	 * @throws DestinationAlreadyExistsException
	 */
	public void generateGraph(String[] in) throws
		IllegalArgumentException, DestinationAlreadyExistsException {
		graph.generateGraph(in);
	}
	
	/**
	 * Print a graph
	 */
	public void printGraph() {
		IO.printGraph(graph);
	}
	
}
