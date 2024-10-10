package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * Represents an exception that is thrown when a transition is invalid during
 * State Pattern. A transition is invalid between states if there is whitespace
 * present or a letter/digit is present in the incorrect transition.
 * 
 * Extends the Exception class.
 * 
 * @author Dania Swelam
 */
public class InvalidTransitionException extends Exception {

	/** ID used for serialization. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new InvalidTransitionException with the specified detail
	 * message.
	 * 
	 * @param message the detail message (which is saved for later retrieval by the
	 *                Throwable.getMessage() method)
	 */
	public InvalidTransitionException(String message) {
		super(message);
	}

	/**
	 * Constructs a new InvalidTransitionException with a default detail message
	 * indicating a an invalid transition.
	 */
	public InvalidTransitionException() {
		this("Invalid FSM Transition.");
	}

}
