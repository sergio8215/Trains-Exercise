package trains.exercise.exception;

public class TownAlreadyExistsException extends Exception {
	
	private static final long serialVersionUID = 2627559558311750765L;

	public TownAlreadyExistsException(String message){
		super(message);
	}
}
