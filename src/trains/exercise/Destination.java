package trains.exercise;

public class Destination implements Comparable{
	private Town town;
	private int weight;
	
	/**
	 * @param name
	 * @param weight
	 */
	public Destination(Town town, int weight) {
		this.town = town;
		this.weight = weight;
	}
	
	public Town getTown() {
		return town;
	}

	public int getWeight() {
		return weight;
	}
	
	@Override
	public int compareTo(Object o) {
		  if(this.weight == ((FoodItems)o).valueOfFood)
		        return 0;
		    else if (this.x <((FoodItems)o).valueOfFood)
		        return 1;
		    else 
		        return -1;
	}
}
