package edu.ncsu.csc216.pack_scheduler.catalog;

import java.io.FileNotFoundException;
import java.io.IOException;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.io.CourseRecordIO;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Class to house a catalog of courses.
 * 
 * @author Dania Swelam
 */
public class CourseCatalog {

	/** SortedList of courses */
	private SortedList<Course> catalog;

	/**
	 * Constructs a new CourseCatalog and initializes the catalog.
	 */
	public CourseCatalog() {
		newCourseCatalog();
	}

	/**
	 * Initializes the catalog with an empty list of courses.
	 */
	public void newCourseCatalog() {
		catalog = new SortedList<Course>();
	}

	/**
	 * Loads courses from a file and adds them to the catalog.
	 * 
	 * @param fileName the name of the file to load courses from
	 * @throws IllegalArgumentException if the file cannot be read
	 */
	public void loadCoursesFromFile(String fileName) {
		
		try {
            catalog = CourseRecordIO.readCourseRecords(fileName);         
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Cannot find file.", e);
        }
    }

	/**
	 * Adds a course to the catalog. Returns true if the course is added
	 * successfully, false if the course already exists in the catalog.
	 * 
	 * @param name         the name of the course
	 * @param title        the title of the course
	 * @param section      the section of the course
	 * @param credits      the number of credits for the course
	 * @param instructorId the ID of the instructor for the course
	 * @param enrollmentCap enrollment capacity
	 * @param meetingDays  the meeting days for the course
	 * @param startTime    the start time of the course
	 * @param endTime      the end time of the course
	 * @return true if the course is added successfully, false if the course already
	 *         exists in the catalog
	 * @throws IllegalArgumentException if the catalog is not initialized or if a
	 *                                  duplicate course is added
	 */
	public boolean addCourseToCatalog(String name, String title, String section, int credits, String instructorId, int enrollmentCap,
			String meetingDays, int startTime, int endTime) {
		if (catalog == null) {
			throw new IllegalArgumentException("Invalid Course catalog.");
		}

		for (int i = 0; i < catalog.size(); i++) {
			Course course = catalog.get(i);
			if (course.getName().equals(name) && course.getSection().equals(section)) {
				return false;
			}
		}

		Course newCourse = new Course(name, title, section, credits, instructorId, enrollmentCap, meetingDays, startTime, endTime);
		catalog.add(newCourse);
		return true;
	}

	/**
	 * Removes a course from the catalog.
	 * 
	 * @param name    the name of the course
	 * @param section the section of the course
	 * @return true if the course is successfully removed, false if the course is
	 *         not found in the catalog
	 */
	public boolean removeCourseFromCatalog(String name, String section) {
		for (int i = 0; i < catalog.size(); i++) {
			Course course = catalog.get(i);
			if (course.getName().equals(name) && course.getSection().equals(section)) {
				catalog.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * Retrieves a course from the catalog based on its name and section.
	 * 
	 * @param name    the name of the course
	 * @param section the section of the course
	 * @return the course if found, or null if the course is not found in the
	 *         catalog
	 */
	public Course getCourseFromCatalog(String name, String section) {
		for (int i = 0; i < catalog.size(); i++) {
			Course course = catalog.get(i);
			if (course.getName().equals(name) && course.getSection().equals(section)) {
				return course;
			}
		}
		return null;
	}

	/**
	 * Retrieves the catalog of courses as a two-dimensional array. Each row in the
	 * array represents a course, and each column represents a course attribute.
	 * 
	 * @return the catalog of courses as a two-dimensional array
	 */
	public String[][] getCourseCatalog() {
		String[][] catalogArray = new String[catalog.size()][5];
		for (int i = 0; i < catalog.size(); i++) {
			Course c = catalog.get(i);
			catalogArray[i] = c.getShortDisplayArray();
		}
		return catalogArray;
	}

	/**
	 * Saves the catalog of courses to a file.
	 * 
	 * @param fileName the name of the file to save the catalog to
	 * @throws IllegalArgumentException if the catalog cannot be written to the file
	 */
	public void saveCourseCatalog(String fileName) {
		try {
			CourseRecordIO.writeCourseRecords(fileName, catalog);
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to write to file " + fileName);
		}
	}
}
