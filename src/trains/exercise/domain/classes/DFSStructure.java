package trains.exercise.domain.classes;

public abstract class DFSStructure {
	private Town town;
	private int value;
	
	/** 
	 * Constructor of the class
	 * @param town
	 * @param Value
	 */
	public DFSStructure(Town town, int value) {
		this.town = town;
		this.value = value;  
	}

	public Town getTown() {
		return town;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
