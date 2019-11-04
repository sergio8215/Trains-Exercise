package trains.exercise.domain.classes;


public class Visited {
	private Town town;
	private boolean visited;
	
	/**
	 * Constructor of the class
	 * @param town
	 * @param visited
	 */
	public Visited(Town town, boolean visited) {
		this.town = town;
		this.visited = visited;
	}

	public Town getTown() {
		return town;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
}
