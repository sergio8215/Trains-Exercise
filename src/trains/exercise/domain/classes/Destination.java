package trains.exercise.domain.classes;

	/**
	 * Candidate weight gives the track weight
	 * @author Sergio
	 *
	 */
public class Destination extends Route{
	
	/**
	 * @param name
	 * @param weight
	 */
	public Destination(Town town, int weight) {
		super(town, weight);
	}
		
}
