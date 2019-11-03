package trains.exercise;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;

import trains.exercise.exception.DestinationAlreadyExistsException;
import trains.exercise.exception.InvalidRouteException;

public class Controller {
	
	private final int INFINITY = 999999;
	
	private Graph graph;

	/**
	 * @param graph
	 */
	public Controller() {
		this.graph = new Graph();
	}
	
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
	 * @throws IllegalArgumentException wrong city name character
	 * @throws DestinationAlreadyExistsException Town already exist
	 * @throws InvalidRouteException Invalid route to compute
	 */
	public int computeDistance(String in) throws IllegalArgumentException, DestinationAlreadyExistsException, InvalidRouteException  {
		int distance = 0;
		String[] townsList = IO.readRoute(in);
		
		for( int i = 0; i < townsList.length-1; i++ ) {
			if( graph.getGraph().containsKey(townsList[i].charAt(0)) && 
				graph.getGraph().get(townsList[i].charAt(0)).containsKey(townsList[i+1].charAt(0))) {
				distance += graph.getGraph().get(townsList[i].charAt(0)).get(townsList[i+1].charAt(0));
				
			}else {
				distance = -1;
				break;
			}
		}
		return distance;
		
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
	
		if( graph.getCandidates().isEmpty() ){
			graph.getCandidates().addAll(graph.getVisited());
			graph.getVisited().clear();
			graph.getSucesors().clear();
		}
		
		graph.getMinimumWeight().put(start.getName(), 0);
		Town v = start;
		int w = 0;
		
		while( graph.getCandidates().size() != 0 ) {
			
			Destination cand = getMinimumCand();
			v = cand.getTown();
			graph.getCandidates().remove(cand.getTown().getName());
			
			int size = graph.getGraphP().get(cand.getTown().getName()).size();
			
			for( int i=0; i < size; i++) {
				// checking all neighbor weights
				Destination neighbor = graph.getGraphP().get(cand.getTown().getName()).get(i);
				
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
			
			graph.getVisited().add(cand.getTown().getName());
		}
		
		return shortestRoute(start, end);
	}
	
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
	
	private boolean isInfinity( Destination d ) {
		return graph.getMinimumWeight().get(d.getTown().getName()) == INFINITY;
	}
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
	
	public void generateGraph(String[] in) throws IllegalArgumentException, DestinationAlreadyExistsException {
		graph.generateGraph(in);
	}
	
	public void printGraph() {
		IO.printGraph(graph);
	}
	
}
