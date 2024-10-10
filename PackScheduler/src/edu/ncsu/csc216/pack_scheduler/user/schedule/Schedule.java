package edu.ncsu.csc216.pack_scheduler.user.schedule;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.util.ArrayList;
import edu.ncsu.csc216.pack_scheduler.course.ConflictException;

/**
 * The Schedule class constructs a schedule of courses for a student.
 * It manages the list of courses, the title of the schedule, and the total number of credits.
 * 
 * @author Dania Swelam
 */
public class Schedule {

    /** The list of courses available for scheduling */
    private ArrayList<Course> schedule;
    /** The title of the user's schedule */
    private String title;
    /** The total number of credits in the schedule */
    private int totalCredits;

    /**
     * Default constructor constructs a new Schedule with a default title and an empty schedule.
     */
    public Schedule() {
        this.title = "My Schedule";
        this.schedule = new ArrayList<>();
        this.totalCredits = 0;
    }

    /**
     * Returns the title of the schedule.
     * 
     * @return the schedule title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the schedule title to the provided value.
     * 
     * @param scheduleTitle the new schedule title
     * @throws IllegalArgumentException if the provided title is null or empty
     */
    public void setTitle(String scheduleTitle) {
        if (scheduleTitle == null || scheduleTitle.isEmpty()) {
            throw new IllegalArgumentException("Invalid title.");
        }
        title = scheduleTitle;
    }

    /**
     * Adds a course to the schedule.
     * 
     * @param course the course to be added
     * @return true if the course was added, false otherwise
     * @throws IllegalArgumentException if the user is already enrolled in the course,
     *                                  if there is a conflict, or if adding the course
     *                                  would exceed the student's max credits
     */
    public boolean addCourseToSchedule(Course course) {
        if (course == null) {
            throw new NullPointerException("Course cannot be null.");
        }

        for (Course courses : schedule) {
            // Check for duplicates
            if (courses.equals(course) || courses.isDuplicate(course)) {
                throw new IllegalArgumentException("You are already enrolled in " + course.getName());
            }

            // Check for conflicts
            try {
                courses.checkConflict(course);
            } catch (ConflictException e) {
                throw new IllegalArgumentException("The course cannot be added due to a conflict.");
            }
        }

        schedule.add(course);
        totalCredits += course.getCredits();
        return true;
    }

    /**
     * Removes a course from the schedule based on its name and section.
     * 
     * @param course the course to be removed
     * @return true if the course was removed, false otherwise
     */
    public boolean removeCourseFromSchedule(Course course) {
        if (schedule.remove(course)) {
            totalCredits -= course.getCredits();
            return true;
        }
        return false;
    }

    /**
     * Resets the schedule to an empty state with the default title and resets total credits.
     */
    public void resetSchedule() {
        this.title = "My Schedule";
        this.schedule = new ArrayList<>();
        this.totalCredits = 0;
    }

    /**
     * Returns the scheduled courses as a 2D array of course information.
     * 
     * @return a 2D array of course information
     */
    public String[][] getScheduledCourses() {
        String[][] scheduledCourses = new String[schedule.size()][5];
        for (int i = 0; i < schedule.size(); i++) {
            scheduledCourses[i] = schedule.get(i).getShortDisplayArray();
        }
        return scheduledCourses;
    }

    /**
     * Returns the total number of credits in the schedule.
     * 
     * @return the total number of credits
     */
    public int getScheduleCredits() {
        return totalCredits;
    }

    /**
     * Determines if the course can be added to the schedule.
     * 
     * @param course the course to be added
     * @return true if the course can be added, false otherwise
     */
    public boolean canAdd(Course course) {
        if (course == null) {
            return false;
        }
        for (int i = 0; i < schedule.size(); i++) {
            Course existingCourse = schedule.get(i);
            if (existingCourse.getName().equals(course.getName())) {
                return false; // Course already in schedule
            }
            try {
                existingCourse.checkConflict(course);
            } catch (ConflictException e) {
                return false; // Course conflicts with existing course
            }
        }
        return true;
    }
    
}
