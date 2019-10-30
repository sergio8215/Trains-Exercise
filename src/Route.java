
public class Route {
	
private char start;
private char ends;
private int weight;
	
	/**
	 * Constructor of the class
	 * @param weight
	 * @param start
	 * @param ends
	 */
	public Route(char start, char ends, int weight) {
		this.start = start;
		this.ends = ends;
		this.weight = weight;
	}
	
	public Route( String data ) {
	    char start = data.charAt(0);
	    char ends  = data.charAt(1);
	    int weight = Integer.valueOf( data.charAt(2) );
		
		this.start = start;
		this.ends = ends;
		this.weight = weight;
	}
	
	public char getStart() {
		return start;
	}
	public char getEnds() {
		return ends;
	}
	public int getWeight() {
		return weight;
	}
}
