import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class Graph {
	private Town town;
	private Route route;
	
	public Graph() {}
	
	public Graph(Town town, Route route) {
		this.town = town;
		this.route = route;
	}
	
	public void generateGraph( List<Route> routes ){
        // key = name, value - websites , but the key 'linode' is duplicated!?
		
		Map<String, Route> graph = routes.stream().collect(
                Collectors.toMap(Route::getStart, Functions ));

        System.out.println("Result 1 : " + result1);
	}
}
