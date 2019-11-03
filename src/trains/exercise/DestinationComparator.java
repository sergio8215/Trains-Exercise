package trains.exercise;

import java.util.Comparator;

public class DestinationComparator implements Comparator<Destination>{
	
	/**
	 * Compare two destination object for the ProrityQueue, where the peak it's the minimum value
	 */
	public int compare(Destination d1, Destination d2) {
		if( d1.getWeight() > d2.getWeight() ) {
			return 1;
		}else if ( d1.getWeight() < d2.getWeight() ) {
			return -1;
		}
		return 0;
	}
}
