/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;

/**
 * The Faculty class represents an individual faculty record.
 * 
 * @author Dania Swelam
 */
public class Faculty extends User {

	/** Field for minimum number of courses a faculty member can have */
	private static final int MIN_COURSES = 1;
	/** Field for maximum number of courses a faculty member can have */
	private static final int MAX_COURSES = 3;
	/** Field for max courses */
	private int maxCourses;
	/** Faculty schedule */
	private FacultySchedule schedule;

	/**
	 * Constructor for constructing a Faculty object consisting of first name, last
	 * name, faculty ID, email, hashed password, and maximum number of courses.
	 * 
	 * @param firstName  the first name of the faculty member
	 * @param lastName   the last name of the faculty member
	 * @param id         the faculty's Unity ID
	 * @param email      the email address of the faculty member
	 * @param password   the hashed password of the faculty member
	 * @param maxCourses the maximum number of courses the faculty can teach per
	 *                   semester
	 */
	public Faculty(String firstName, String lastName, String id, String email, String password, int maxCourses) {
		super(firstName, lastName, id, email, password);
		setMaxCourses(maxCourses);
		this.schedule = new FacultySchedule(id);
	}

	/**
	 * Retrieves the maximum number of courses the faculty can have per semester.
	 * 
	 * @return the maximum number of courses the faculty can have per semester.
	 */
	public int getMaxCourses() {
		return maxCourses;
	}

	/**
	 * Sets the maximum number of courses the faculty member can enroll in.
	 * 
	 * @param maxCourses the maximum number of courses to set
	 * @throws IllegalArgumentException if the maximum courses are less than the
	 *                                  minimum allowed courses or exceed the
	 *                                  maximum allowed courses
	 */
	public void setMaxCourses(int maxCourses) {
		if (maxCourses < MIN_COURSES || maxCourses > MAX_COURSES) {
			throw new IllegalArgumentException("Invalid max courses");
		}
		this.maxCourses = maxCourses;
	}

	/**
	 * Returns the faculty's schedule.
	 * 
	 * @return the faculty's schedule
	 */
	public FacultySchedule getSchedule() {
		return schedule;
	}

	/**
	 * Checks if the faculty is overloaded.
	 * 
	 * @return true if the faculty is overloaded, false otherwise
	 */
	public boolean isOverloaded() {
		return schedule.getNumScheduledCourses() > maxCourses;
	}

	/**
	 * Returns a string representation of the Faculty object.
	 * 
	 * @return A string representation of the Faculty object containing the first
	 *         name, last name, id, email, password, and maximum courses.
	 */
	@Override
	public String toString() {
		return getFirstName() + "," + getLastName() + "," + getId() + "," + getEmail() + "," + getPassword() + ","
				+ getMaxCourses();
	}

	/**
	 * Generates a hash code for the Faculty object.
	 * 
	 * @return the hash code
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCourses;
		return result;
	}

	/**
	 * Checks if two Faculty objects are equal.
	 * 
	 * @param obj the object to compare to
	 * @return true if the objects are equal, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Faculty other = (Faculty) obj;
		return maxCourses == other.maxCourses;
	}

}