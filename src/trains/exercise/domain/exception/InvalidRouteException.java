package trains.exercise.domain.exception;

public class InvalidRouteException extends Exception{
	
	private static final long serialVersionUID = -8576969300126424977L;

	/**
	 * Error given when the route is invalid
	 * @param message
	 */
	public InvalidRouteException(String message){
		super(message);
	}
}
