package trains.exercise.domain.classes;

	/**
	 * Candidate weight gives the full path weight 
	 * @author Sergio
	 *
	 */
public class Candidate {
	private Town town;
	private int weight;
	
	/**
	 * @param name
	 * @param weight
	 */
	public Candidate(Town town, int weight) {
		this.town = town;
		this.weight = weight;
	}
	
	public Town getTown() {
		return town;
	}

	public int getWeight() {
		return weight;
	}
}
