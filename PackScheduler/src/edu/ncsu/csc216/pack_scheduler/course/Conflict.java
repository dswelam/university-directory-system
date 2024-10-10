package edu.ncsu.csc216.pack_scheduler.course;

/**
 * This interface defines a method for checking conflicts between activities.
 * Implementing classes should provide a way to determine whether the given
 * activity conflicts with another activity, and throw a @ConflictException 
 * to indicate the conflict.
 * 
 * @author Dania Swelam
 */
public interface Conflict {

	/**
	 * Checks for conflicts between the current activity and the
	 * provided conflicting activity.
	 * 
	 * @param possibleConflictingActivity the activity to check for conflicts with
	 * @throws ConflictException if a conflict is detected between the current
	 *                           activity and the provided activity
	 */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;

}
