package trains.exercise.domain.classes;

	/**
	 * Candidate weight gives the full path weight 
	 * @author Sergio
	 *
	 */
public class Candidate extends Route{
	/**
	 * @param name
	 * @param weight
	 */
	public Candidate(Town town, int weight) {
		super(town, weight);
	}
}
