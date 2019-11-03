package trains.exercise;

import java.io.FileNotFoundException;
import java.util.List;
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
	
	public List<String> computeShortestRoute(String in) throws IllegalArgumentException, DestinationAlreadyExistsException, InvalidRouteException  {
		
		String[] towns = IO.validateShortestRoute(in);
		Town start = new Town(towns[0]);
		Town end =  new Town(towns[1]);
		
		// Initialize all distances to infinity
		graph.getMinimumWeight().entrySet().forEach(entry-> {
			entry.setValue(INFINITY);
		});
	
		Destination dest = new Destination(start, 0);
		graph.getGraphP().get(start.getName()).add(dest);
		graph.getMinimumWeight().put(start.getName(), 0);
		Town v = start;
		
		while( graph.getCandidates().size() != 0 ) {
			
			Destination u = graph.getGraphP().get(v.getName()).poll();
			graph.getCandidates().remove(u.getTown().getName());
			
			for( int i=0; i< graph.getGraphP().get(u.getTown().getName()).size(); i++) {
				
				int w = u.getWeight();
				
				if( graph.getMinimumWeight().get(u.getTown().getName()) == INFINITY ){
					graph.getMinimumWeight().put(u.getTown().getName(), w);
					graph.getSucesors().put(u.getTown().getName(), v);
				
				}else {
					w += graph.getMinimumWeight().get(u.getTown().getName());
					
					if( w < graph.getMinimumWeight().get(u.getTown().getName())) {
						graph.getMinimumWeight().put(u.getTown().getName(), w);
						graph.getSucesors().put(u.getTown().getName(), v);
					}
				}
			}
			
			graph.getVisited().add(u.getTown().getName());
		}
		
		System.out.println("R= " + graph.getSucesors().get(start.getName()).toString());
		return null;
	}
	
	public void generateGraph(String[] in) throws IllegalArgumentException, DestinationAlreadyExistsException {
		graph.generateGraph(in);
	}
	
	public void printGraph() {
		IO.printGraph(graph);
	}
	
}
