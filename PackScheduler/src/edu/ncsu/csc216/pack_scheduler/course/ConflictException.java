/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Represents an exception that is thrown when a conflict is encountered within a schedule.
 * A conflict occurs when two activities cannot occur at the same time in a schedule.
 * Extends the Exception class.
 * 
 * @author Dania Swelam
 */
public class ConflictException extends Exception {

	/** ID used for serialization. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new ConflictException with the specified detail message.
	 * 
	 * @param message the detail message (which is saved for later retrieval by the Throwable.getMessage() method)
	 */
	public ConflictException(String message) {
        super(message);
    } 

	/**
     * Constructs a new ConflictException with a default detail message indicating a schedule conflict.
     */
    public ConflictException() {
        this("Schedule conflict.");
    }

}
