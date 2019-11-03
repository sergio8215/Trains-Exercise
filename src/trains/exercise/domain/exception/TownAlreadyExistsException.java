package trains.exercise.domain.exception;

public class TownAlreadyExistsException extends Exception {
	
	private static final long serialVersionUID = 2627559558311750765L;

	/**
	 * Error given when the town already exists 
	 * @param message
	 */
	public TownAlreadyExistsException(String message){
		super(message);
	}
}
