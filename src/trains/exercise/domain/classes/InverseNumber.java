package trains.exercise.domain.classes;
/**
 * Is the ordinal number which the city has being closed
 * The town is closed when the recursive traversal of all its
 * adjacent/successors has been finished
 * the value is the number in which the town has been open
 * @author Sergio
 *
 */
public class InverseNumber extends DFSStructure{
	/** 
	 * Constructor of the class
	 * @param town
	 * @param ndfs
	 */
	public InverseNumber(Town town, int ninv) {
		super(town, ninv);  
	}	
}
