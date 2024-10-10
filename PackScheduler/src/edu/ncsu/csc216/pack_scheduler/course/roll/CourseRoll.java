package edu.ncsu.csc216.pack_scheduler.course.roll;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList;
import edu.ncsu.csc216.pack_scheduler.util.LinkedQueue;

/**
 * CourseRoll manages the enrollment of students in a course.
 * 
 * @author Dania Swelam
 */
public class CourseRoll {

	/** The waitlist capacity of a course */
	private static final int WAITLIST_SIZE = 10;
	/** Minimum enrollment for a course */
	public static final int MIN_ENROLLMENT = 10;
	/** Maximum enrollment for a course */
	public static final int MAX_ENROLLMENT = 250;
	/** The list of students enrolled in the course */
	private LinkedAbstractList<Student> roll;
	/** The list of students in a course waitlist */
	private LinkedQueue<Student> waitlist;
	/** The enrollment capacity of the course */
	private int enrollmentCap;
	/** A course for enrollment */
	private Course course;

	/**
	 * Constructs a CourseRoll with a given course and enrollment capacity.
	 * 
	 * @param course        the course associated with the CourseRoll
	 * @param enrollmentCap the enrollment capacity of the course
	 */
	public CourseRoll(Course course, int enrollmentCap) {

		roll = new LinkedAbstractList<>(enrollmentCap);
		waitlist = new LinkedQueue<>(WAITLIST_SIZE);
		setEnrollmentCap(enrollmentCap);
		setCourse(course);
	}

	/**
	 * Private method to set the course associated with the CourseRoll.
	 * 
	 * @param course the course to associate with this CourseRoll
	 * @throws IllegalArgumentException if the course is null or if the
	 *                                  enrollmentCap is invalid
	 */
	private void setCourse(Course course) {
		if (course == null) {
			throw new IllegalArgumentException();
		}
		this.course = course;
	}

	/**
	 * Gets the number of open seats in the course.
	 * 
	 * @return the number of open seats
	 */
	public int getOpenSeats() {
		return enrollmentCap - roll.size();
	}

	/**
	 * Gets the enrollment capacity of the course.
	 * 
	 * @return the enrollment capacity
	 */
	public int getEnrollmentCap() {
		return enrollmentCap;
	}

	/**
	 * Sets the enrollment capacity of the course.
	 * 
	 * @param enrollmentCap the new enrollment capacity
	 * @throws IllegalArgumentException if the enrollmentCap is less than
	 *                                  MIN_ENROLLMENT or greater than
	 *                                  MAX_ENROLLMENT
	 */
	public void setEnrollmentCap(int enrollmentCap) {
		 if (enrollmentCap < MIN_ENROLLMENT || enrollmentCap > MAX_ENROLLMENT) {
	            throw new IllegalArgumentException();
	        }
	        if (enrollmentCap < roll.size()) {
	            throw new IllegalArgumentException();
	        }

	        this.enrollmentCap = enrollmentCap;
	        if (roll != null) {
	            roll.setCapacity(enrollmentCap);
	        }
	}
	
	/**
	 * Enrolls a student in the course.
	 * 
	 * @param s the student to enroll
	 * @throws IllegalArgumentException if the student cannot be enrolled or added
	 *                                  to the waitlist
	 */
	public void enroll(Student s) {
		if (s == null) {
			throw new IllegalArgumentException("Course cannot be added to schedule.");
		}
		
		if (roll.contains(s)) {
			throw new IllegalArgumentException("The course cannot be added");
		}
		
		if (roll.size() < enrollmentCap) {
			roll.add(roll.size(), s);
		} else {
			if (waitlist.size() >= WAITLIST_SIZE) {
				throw new IllegalArgumentException("The course cannot be added");
			}
			waitlist.enqueue(s);
		}
	}

	/**
    /**
     * Drops a student from the course.
     * 
     * @param s the student to drop
     * @throws IllegalArgumentException if the student is not enrolled in the course or waitlist
     */
    public void drop(Student s) {
        if (s == null) {
            throw new IllegalArgumentException("Student cannot be null.");
        }
        try {
            if (roll.contains(s)) {
                roll.remove(roll.indexOf(s));
                if (!waitlist.isEmpty()) {
                    Student nextStudent = waitlist.dequeue();
                    roll.add(roll.size(), nextStudent);
                    nextStudent.getSchedule().addCourseToSchedule(course); 
                }
            } else if (waitlist.contains(s)) {
                waitlist.remove(s);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

	/**
	 * Checks if a student can enroll in the course or be added to the waitlist.
	 * 
	 * @param s the student to check
	 * @return true if the student can be added to the course or waitlist
	 */
    public boolean canEnroll(Student s) {
        if (s == null) {
            return false;
        }
        if (roll.contains(s) || waitlist.contains(s)) {
            return false;
        }
        return roll.size() < enrollmentCap || waitlist.size() < WAITLIST_SIZE;
    }

	/**
	 * Gets the number of students on the waitlist.
	 * 
	 * @return the number of students on the waitlist
	 */
	public int getNumberOnWaitlist() {
		return waitlist.size();
	}

}
