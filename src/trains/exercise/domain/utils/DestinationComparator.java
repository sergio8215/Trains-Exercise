package trains.exercise.domain.utils;

import java.util.Comparator;

import trains.exercise.domain.classes.Destination;

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
