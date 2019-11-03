package trains.exercise.domain.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import trains.exercise.domain.classes.Destination;
import trains.exercise.domain.classes.Graph;
import trains.exercise.domain.classes.Town;
import trains.exercise.domain.exception.DestinationAlreadyExistsException;
import trains.exercise.domain.exception.InvalidRouteException;
import trains.exercise.presentation.IO;
import trains.exercise.presentation.Menu;

public class Controller {
	
	private final int INFINITY = 9999999;
	
	private Graph graph;
	private boolean wasDijkstraExecuted;
	private boolean graphLoaded;
	private Town	startDijkstra; 

	/**
	 * Empty constructor
	 * @param graph
	 */
	public Controller() {
		this.graph = new Graph();
		wasDijkstraExecuted = false;
		graphLoaded = false;
		startDijkstra = null;
	}
	
	/**
	 * Function that capture errors and calls the menu
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
	 * Validates the input and compute the distance between to towns along a certain route 
	 * @param in Route to compute distance
	 * @return Calculated distance or NO SUCH ROUTE if the route doesn't exist
	 * @throws IllegalArgumentException wrong city name
	 * @throws DestinationAlreadyExistsException Town already exist
	 * @throws InvalidRouteException Invalid route to compute
	 */
	public int computeDistanceAndValidate(String in) throws
			IllegalArgumentException, DestinationAlreadyExistsException, InvalidRouteException  {
		String[] townsList = IO.readRoute(in);
		return computeDistance(townsList);
	}
	
	/**
	 * Compute the distance between to towns along a certain route.
	 * @param townsList
	 * @return distance between a certain route
	 * @throws IllegalArgumentException
	 * @throws DestinationAlreadyExistsException
	 * @throws InvalidRouteException
	 */
	private int computeDistance(String[] townsList) throws
	IllegalArgumentException, DestinationAlreadyExistsException, InvalidRouteException  {
		
		int distance = 0;
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
	 * Validates the input, and verify if the algorithm needs to be run
	 * @param in
	 * @return A route with minimum distance between two towns
	 * @throws IllegalArgumentException
	 * @throws DestinationAlreadyExistsException
	 * @throws InvalidRouteException
	 * @throws CloneNotSupportedException
	 */
	public List<Town> computeShortestRouteAndValidate(String in) throws
		IllegalArgumentException, DestinationAlreadyExistsException, InvalidRouteException, CloneNotSupportedException  {
		
		String[] towns = IO.validateShortestRoute(in);
		Town start = new Town(towns[0]);
		Town end =  new Town(towns[1]);
		
		// If the algorithm was not executed, or the start town is different than the last execution
		if( !wasDijkstraExecuted ||
			(wasDijkstraExecuted && !isSameStartCity(start)) ) {			
			
			initializeStructuresDijkstra();
			computeShortestRoute(start);
			wasDijkstraExecuted = true;
			startDijkstra = start;
		}
		return shortestRoute(start, end);
	}
	
	/**
	 * Compute a route with minimum distance between two towns
	 * @param start
	 * @throws IllegalArgumentException
	 * @throws DestinationAlreadyExistsException
	 * @throws InvalidRouteException
	 * @throws CloneNotSupportedException
	 */
	public void computeShortestRoute(Town start) throws
		IllegalArgumentException, DestinationAlreadyExistsException, InvalidRouteException, CloneNotSupportedException  {
		
		// Start point of the algorithm
		graph.getMinimumWeight().put(start.getName(), 0);
		
		// For each candidate we find the shortest path to the start point
		while( graph.getCandidates().size() != 0 ) {
			
			Destination cand = getMinimumCand();
			// we remove the candidate from the unvisited list
			graph.getCandidates().remove(cand.getTown().getName());
			
			int size = graph.getGraphP().get(cand.getTown().getName()).size();
			
			for( int i=0; i < size; i++) {
				// checking all candidate neighbors weights
				Destination neighbor = graph.getGraphP().get(cand.getTown().getName()).get(i);
				updateMinimumWeight(neighbor, cand);
			}
			// We add the candidate to the visited list
			graph.getVisited().add(cand.getTown().getName());
		}
	}
	
	
	/**
	 * Updates the minimum weight table with the new weight computation
	 * @param neighbor
	 * @param cand
	 */
	private void updateMinimumWeight(Destination neighbor, Destination cand) {
		int w = 0;
		
		if( isInfinitySavedWeight(neighbor) ){
			w = cand.getWeight() + neighbor.getWeight();
			graph.getMinimumWeight().put(neighbor.getTown().getName(), w);
			// Add the town to the predecessors list
			graph.getSuccessor().put(neighbor.getTown().getName(), cand.getTown());
		
		}else {
			w = graph.getMinimumWeight().get(cand.getTown().getName()) + neighbor.getWeight();
			
			if( w < graph.getMinimumWeight().get(neighbor.getTown().getName())) {
				graph.getMinimumWeight().put(neighbor.getTown().getName(), w);
				// Add the town to the predecessors list
				graph.getSuccessor().put(neighbor.getTown().getName(), cand.getTown());
			}
		}
	}
	
	private boolean isSameStartCity(Town start) {
		return startDijkstra.getName().equals(start.getName());
	}
	
	/**
	 * After applying the algorithm one time, we have to reset this structures to the initial values
	 */
	private void initializeStructuresDijkstra() {
		graph.getCandidates().addAll(graph.getGraphP().keySet());
		graph.getVisited().clear();
		graph.getSuccessor().clear();
		// Initialize all distances to infinity
		for( String k: graph.getCandidates() ) {
			graph.getMinimumWeight().put(k, INFINITY);
		}
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
	private boolean isInfinitySavedWeight( Destination d ) {
		return graph.getMinimumWeight().get(d.getTown().getName()) == INFINITY;
	}
	
	/**
	 * Creates the route from one town to other, with the predecessors list
	 * @param start point
	 * @param end point
	 * @return Ordered list with the route
	 */
	private List<Town> shortestRoute(Town start, Town end){
		List<Town> result = new ArrayList<Town>();
		Town sucesor = graph.getSuccessor().get(end.getName());
		result.add(end);
		
		if(sucesor != null) {			
			result.add(sucesor);
			while( !(sucesor.getName()).equals(start.getName()) ) {
				
				sucesor = graph.getSuccessor().get(sucesor.getName());
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

	/* Getters and Setters */
	
	public boolean wasDijkstraExecuted() {
		return wasDijkstraExecuted;
	}

	public void setWasDijkstraExecuted(boolean wasDijkstraExecuted) {
		this.wasDijkstraExecuted = wasDijkstraExecuted;
	}

	public boolean isGraphLoaded() {
		return graphLoaded;
	}

	public void setGraphLoaded(boolean graphLoaded) {
		this.graphLoaded = graphLoaded;
	}

	public Town getStartDijkstra() {
		return startDijkstra;
	}

	public void setStartDijkstra(Town startDijkstra) {
		this.startDijkstra = startDijkstra;
	}
}
