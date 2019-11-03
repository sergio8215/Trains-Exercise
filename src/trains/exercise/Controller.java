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
	
	public List<Town> computeShortestRoute(String in) throws
		IllegalArgumentException, DestinationAlreadyExistsException, InvalidRouteException, CloneNotSupportedException  {
		
		String[] towns = IO.validateShortestRoute(in);
		Town start = new Town(towns[0]);
		Town end =  new Town(towns[1]);
		Graph g = graph.clone();
		
		// Initialize all distances to infinity
		g.getMinimumWeight().entrySet().forEach(entry-> {
			entry.setValue(INFINITY);
		});
	
		Destination dest = new Destination(start, 0);
		g.getGraphP().get(start.getName()).add(dest);
		g.getMinimumWeight().put(start.getName(), 0);
		Town v = start;
		int w = 0;
		
		while( g.getCandidates().size() != 0 ) {
			
			Destination u = g.getGraphP().get(v.getName()).poll();
			if( u == null ) {
				u = g.getGraphP().get(g.getCandidates().iterator().next()).poll();
			} 
			v = u.getTown();
			g.getCandidates().remove(u.getTown().getName());
			//TODO: Copia?
			PriorityQueue<Destination> neighbor = new PriorityQueue<Destination>(g.getGraphP().get(u.getTown().getName())) ;
			int size = neighbor.size();
			
			for( int i=0; i < size; i++) {
				
				Destination n = neighbor.poll();
				
				if( g.getMinimumWeight().get(n.getTown().getName()) == INFINITY ){
					
					w = u.getWeight() + n.getWeight();
					
					g.getMinimumWeight().put(n.getTown().getName(), w);
					g.getSucesors().put(n.getTown().getName(), u.getTown());
				
				}else {
					
					w = g.getMinimumWeight().get(u.getTown().getName()) + n.getWeight();
					
					if( w < g.getMinimumWeight().get(n.getTown().getName())) {
						g.getMinimumWeight().put(n.getTown().getName(), w);
						g.getSucesors().put(n.getTown().getName(), u.getTown());
					}
				}
			}
			
			g.getVisited().add(u.getTown().getName());
		}
		
		return shortestRoute(start, end);
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
