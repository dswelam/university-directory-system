package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * The Student class represents an individual student record in the
 * PackScheduler System consisting of a student's first name, last name, student
 * ID, email, hashed password, and maximum number of credits.
 * It also includes a schedule of courses for the student.
 * 
 * @author Dania Swelam
 */
public class Student extends User implements Comparable<Student> {

    /** Student's maximum amount of credits */
    private int maxCredits;
    /** The maximum number of credits a student can have */
    public static final int MAX_CREDITS = 18;
    /** The minimum number of credits a student can have */
    public static final int MIN_CREDITS = 3;
    /** The schedule of the student */
    private Schedule schedule;

    /**
     * Constructor for constructing a Student object consisting of first name, last
     * name, student ID, email, hashed password, and maximum number of credits.
     * 
     * @param firstName  the first name of the student
     * @param lastName   the last name of the student
     * @param id         the student's ID
     * @param email      the email address of the student
     * @param password   the hashed password of the student
     * @param maxCredits the maximum number of credits the student can enroll in
     */
    public Student(String firstName, String lastName, String id, String email, String password, int maxCredits) {
        super(firstName, lastName, id, email, password);
        setMaxCredits(maxCredits);
        this.schedule = new Schedule();
    }

    /**
     * Parameterized constructor consisting of first name, last name, student ID,
     * email, hashed password, and maximum number of credits.
     * 
     * @param firstName the first name of the student
     * @param lastName  the last name of the student
     * @param id        the student's ID
     * @param email     the email address of the student
     * @param password  the password of the student
     */
    public Student(String firstName, String lastName, String id, String email, String password) {
        this(firstName, lastName, id, email, password, MAX_CREDITS);
    }

    /**
     * Retrieves the maximum number of credits the student can enroll in.
     * 
     * @return the maximum number of credits the student can enroll in
     */
    public int getMaxCredits() {
        return maxCredits;
    }

    /**
     * Sets the maximum number of credits the student can enroll in.
     * 
     * @param maxCredits the maximum number of credits to set
     * @throws IllegalArgumentException if the maximum credits are less than the
     *                                  minimum allowed credits or exceed the
     *                                  maximum allowed credits
     */
    public void setMaxCredits(int maxCredits) {
        if (maxCredits < MIN_CREDITS || maxCredits > MAX_CREDITS) {
            throw new IllegalArgumentException("Invalid max credits");
        }
        this.maxCredits = maxCredits;
    }

    /**
     * Returns a string representation of the Student object.
     * 
     * @return a string representation of the Student object containing the first
     *         name, last name, id, email, password, and maximum credits
     */
    @Override
    public String toString() {
        return getFirstName() + "," + getLastName() + "," + getId() + "," + getEmail() + "," + getPassword() + "," + maxCredits;
    }

    /**
     * Compares this object with the specified object for order. Returns a negative
     * integer, zero, or a positive integer as this object is less than, equal to,
     * or greater than the specified object.
     * 
     * @param s the student to compare to
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object
     */
    @Override
    public int compareTo(Student s) {
        if (s == null) {
            throw new NullPointerException();
        }

        int lastNameComparison = this.getLastName().compareTo(s.getLastName());
        if (lastNameComparison != 0) {
            return lastNameComparison;
        }

        int firstNameComparison = this.getFirstName().compareTo(s.getFirstName());
        if (firstNameComparison != 0) {
            return firstNameComparison;
        }

        return this.getId().compareTo(s.getId());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + maxCredits;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Student other = (Student) obj;
        return maxCredits == other.maxCredits;
    }
    
    /**
     * Retrieves the schedule of the student.
     * 
     * @return the schedule of the student
     */
    public Schedule getSchedule() {
        return schedule;
    }

    /**
     * Determines if the course can be added to the student's schedule.
     * 
     * @param course the course to be added
     * @return true if the course can be added, false otherwise
     */
    public boolean canAdd(Course course) {
        return schedule.canAdd(course) 
            && schedule.getScheduleCredits() + course.getCredits() <= maxCredits;
    }
    
}
