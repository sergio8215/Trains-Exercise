package trains.exercise.domain.classes;
/**
 * Is the ordinal number which the city has being open
 * When a town is visited for the first time, this town is open
 * The value is the number in which the town has been open
 * @author Sergio
 *
 */
public class DFSNumber extends DFSStructure{
	
	/** 
	 * Constructor of the class
	 * @param town
	 * @param ndfs
	 */
	public DFSNumber(Town town, int ndfs) {
		super(town, ndfs);
	}
}
