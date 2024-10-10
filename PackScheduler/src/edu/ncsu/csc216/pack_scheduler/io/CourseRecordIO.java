package edu.ncsu.csc216.pack_scheduler.io;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Reads Course records from text files and writes a set of Course records to a
 * file.
 * 
 * @author Sarah Heckman
 * @author Dania Swelam
 */
public class CourseRecordIO {

	/**
	 * Reads course records from a file and generates a list of valid Courses. Any
	 * invalid Courses are ignored. If the file to read cannot be found or the
	 * permissions are incorrect a FileNotFoundException is thrown.
	 * 
	 * @param fileName the file to read Course records from
	 * @return a list of valid Courses
	 * @throws FileNotFoundException if the file cannot be found or read
	 */
	public static SortedList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName)); // Create a file scanner to read the file
		SortedList<Course> courses = new SortedList<Course>(); // Create an empty array of Course objects
		while (fileReader.hasNextLine()) { // While we have more lines in the file
			try { // Attempt to do the following
					// Read the line, process it in readCourse, and get the object
					// If trying to construct a Course in readCourse() results in an exception, flow
					// of control will transfer to the catch block, below
				Course course = readCourse(fileReader.nextLine());

				// Create a flag to see if the newly created Course is a duplicate of something
				// already in the list
				boolean duplicate = false;
				// Look at all the courses in our list
				for (int i = 0; i < courses.size(); i++) {
					// Get the course at index i
					Course current = courses.get(i);
					// Check if the name and section are the same
					if (course.getName().equals(current.getName())
							&& course.getSection().equals(current.getSection())) {
						// It's a duplicate!
						duplicate = true;
						break; // We can break out of the loop, no need to continue searching
					}
				}
				// If the course is NOT a duplicate
				if (!duplicate) {
					courses.add(course); // Add to the SortedList!
				} // Otherwise ignore it
			} catch (IllegalArgumentException e) {
				continue;
			}
		}
		// Close the Scanner b/c we're responsible with our file handles
		fileReader.close();
		// Return the SortedList with all the courses we read!
		return courses;
	}

	/**
	 * Helper method to read in each line from the input file and create a Course
	 * object.
	 * 
	 * @param line the line from the input file
	 * @return a Course object with data extracted from the line
	 */
	private static Course readCourse(String line) {
		Scanner readLine = new Scanner(line);
		readLine.useDelimiter(",");

		try {
			String name = readLine.next();
			String title = readLine.next();
			String section = readLine.next();
			int credits = readInt(readLine, "Invalid credits format.");
			String instructorId = readLine.hasNext() ? readLine.next() : null; // Allow null
			int enrollmentCap = readInt(readLine, "Invalid enrollment capacity format.");
			String meetingDays = readLine.next();

			Course course;
			if ("A".equals(meetingDays)) {
				if (meetingDays.length() > 1 || readLine.hasNext()) {
					throw new IllegalArgumentException("Invalid meeting days format.");
				}
				course = new Course(name, title, section, credits, null, enrollmentCap, meetingDays);
			} else {
				int startTime = readInt(readLine, "Invalid start time format.");
				int endTime = readInt(readLine, "Invalid end time format.");
				if (readLine.hasNext()) {
					throw new IllegalArgumentException("Extra data after end time.");
				}
				course = new Course(name, title, section, credits, null, enrollmentCap, meetingDays, startTime,
						endTime);
			}

			// Check for a Faculty with the given instructorId
			if (instructorId != null) {
				Faculty faculty = RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(instructorId);
				if (faculty != null) {
					faculty.getSchedule().addCourseToSchedule(course); // This will set the instructorId
				}
			}

			return course;
		} catch (NoSuchElementException e) {
			throw new IllegalArgumentException("Incomplete course data.", e);
		} finally {
			readLine.close();
		}
	}

	/**
	 * Helper method to read an integer from the Scanner with error checking.
	 * 
	 * @param scanner      the Scanner to read from
	 * @param errorMessage the error message to use if reading the integer fails
	 * @return the next integer in the scanner
	 */
	private static int readInt(Scanner scanner, String errorMessage) {
		if (!scanner.hasNextInt()) {
			throw new IllegalArgumentException(errorMessage);
		}
		return scanner.nextInt();
	}

	/**
	 * Writes the given list of Courses to a .txt file.
	 * 
	 * @param fileName the file to write the schedule of Courses to
	 * @param courses  the list of courses to write
	 * @throws IOException if the file cannot be written to
	 */
	public static void writeCourseRecords(String fileName, SortedList<Course> courses) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));

		for (int i = 0; i < courses.size(); i++) {
			fileWriter.println(courses.get(i).toString());
		}

		fileWriter.close();
	}

}
