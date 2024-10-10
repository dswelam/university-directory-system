package edu.ncsu.csc216.pack_scheduler.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Properties;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Manages the registration of users, including the course catalog, student
 * directory, and user login. Handles the operations related to course enrollment
 * and schedule management. Implements the singleton design pattern.
 * 
 * @author Dania Swelam
 */
public class RegistrationManager {

    /** Hashing algorithm used for password encryption */
    private static final String HASH_ALGORITHM = "SHA-256";
    /** Properties file containing registrar details */
    private static final String PROP_FILE = "registrar.properties";
    /** Singleton instance of RegistrationManager */
    private static RegistrationManager instance;
    /** The registrar user */
    private Registrar registrar;
    /** The current logged in user */
    private User currentUser;
    /** The course catalog */
    private CourseCatalog courseCatalog;
    /** The student directory */
    private StudentDirectory studentDirectory;
    /** The faculty directory */
    private FacultyDirectory facultyDirectory;

    /**
     * Private constructor to create a new RegistrationManager. Initializes the
     * registrar, course catalog, student directory, and faculty directory.
     */
    private RegistrationManager() {
        currentUser = null;
        createRegistrar();
        courseCatalog = new CourseCatalog();
        studentDirectory = new StudentDirectory();
        facultyDirectory = new FacultyDirectory();
        studentDirectory.loadStudentsFromFile("test-files/student_records.txt");
        facultyDirectory.loadFacultyFromFile("test-files/faculty_records.txt");
        courseCatalog.loadCoursesFromFile("test-files/course_records.txt");
    }

    /**
     * Returns the singleton instance of the RegistrationManager.
     * 
     * @return instance the singleton instance
     */
    public static RegistrationManager getInstance() {
        if (instance == null) {
            instance = new RegistrationManager();
        }
        return instance;
    }

    /**
     * Creates the registrar user by reading properties from the properties file.
     * 
     * @throws IllegalArgumentException if the registrar properties file cannot be read or 
     *                                  if the password property is missing
     */
    private void createRegistrar() {
        Properties prop = new Properties();

        try (InputStream input = new FileInputStream(PROP_FILE)) {
            prop.load(input);

            String password = prop.getProperty("pw");
            if (password == null) {
                throw new IllegalArgumentException("Password property is missing in registrar.properties file.");
            }

            String hashPW = hashPW(password);

            registrar = new Registrar(prop.getProperty("first"), prop.getProperty("last"), prop.getProperty("id"),
                    prop.getProperty("email"), hashPW);
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot create registrar.", e);
        }
    }

    /**
     * Hashes the given password using the specified hashing algorithm.
     * 
     * @param pw the password to hash
     * @return the hashed password
     * @throws IllegalArgumentException if the hashing algorithm is not found
     */
    private String hashPW(String pw) {
        try {
            MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
            digest1.update(pw.getBytes());
            return Base64.getEncoder().encodeToString(digest1.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("Cannot hash password", e);
        }
    }

    /**
     * Returns the course catalog.
     * 
     * @return the course catalog
     */
    public CourseCatalog getCourseCatalog() {
        return courseCatalog;
    }

    /**
     * Returns the student directory.
     * 
     * @return the student directory
     */
    public StudentDirectory getStudentDirectory() {
        return studentDirectory;
    }

    /**
     * Returns the faculty directory.
     * 
     * @return the faculty directory
     */
    public FacultyDirectory getFacultyDirectory() {
        return facultyDirectory;
    }

    /**
     * Logs in a user with the given id and password.
     * 
     * @param id       the user id
     * @param password the user password
     * @return true if login is successful, false otherwise
     * @throws IllegalArgumentException if the id or password is null or if the id
     *                                  does not match any user
     */
    public boolean login(String id, String password) {

        // Check if there is already a user logged in
        if (currentUser != null) {
            return false; // Only one user can be logged in at a time
        }

        String localHashPW = hashPW(password);

        // Check if the ID matches the registrar's ID
        if (id.equals(registrar.getId())) {
            if (localHashPW.equals(registrar.getPassword())) {
                currentUser = registrar;
                return true;
            } else {
                return false;
            }
        }

        // Check if the ID matches a student in the directory
        Student s = studentDirectory.getStudentById(id);
        if (s != null) {
            if (localHashPW.equals(s.getPassword())) {
                currentUser = s;
                return true;
            } else {
                return false;
            }
        }

        // Check if the ID matches a faculty in the directory
        Faculty f = facultyDirectory.getFacultyById(id);
        if (f != null) {
            if (localHashPW.equals(f.getPassword())) {
                currentUser = f;
                return true;
            } else {
                return false;
            }
        }

        // If the ID doesn't match any user, throw an IllegalArgumentException
        throw new IllegalArgumentException("User doesn't exist.");
    }

    /**
     * Logs out the current user.
     */
    public void logout() {
        currentUser = null;
    }

    /**
     * Returns the current logged in user.
     * 
     * @return the current logged in user, or null if no user is logged in
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Clears the data in the course catalog, student directory, and faculty directory.
     */
    public void clearData() {
        courseCatalog.newCourseCatalog();
        studentDirectory.newStudentDirectory();
        facultyDirectory.newFacultyDirectory();
    }

    /**
     * Represents the registrar user, extending the User class.
     */
    private static class Registrar extends User {
        /**
         * Creates a registrar user.
         * 
         * @param firstName the first name of the registrar
         * @param lastName  the last name of the registrar
         * @param id        the id of the registrar
         * @param email     the email of the registrar
         * @param password  the hashed password of the registrar
         */
        public Registrar(String firstName, String lastName, String id, String email, String password) {
            super(firstName, lastName, id, email, password);
        }
    }

    /**
     * Returns true if the logged in student can enroll in the given course.
     * 
     * @param c the Course to enroll in
     * @return true if the student is enrolled in the course, false otherwise
     * @throws IllegalArgumentException if the current user is not a student
     */
    public boolean enrollStudentInCourse(Course c) {
        if (!(currentUser instanceof Student)) {
            throw new IllegalArgumentException("Illegal Action");
        }
        try {
            Student s = (Student) currentUser;
            Schedule schedule = s.getSchedule();
            CourseRoll roll = c.getCourseRoll();

            if (s.canAdd(c) && roll.canEnroll(s)) {
                schedule.addCourseToSchedule(c);
                roll.enroll(s);
                return true;
            }

        } catch (IllegalArgumentException e) {
            return false;
        }
        return false;
    }

    /**
     * Returns true if the logged in student can drop the given course.
     * 
     * @param c the Course to drop
     * @return true if the student is dropped from the course, false otherwise
     * @throws IllegalArgumentException if the current user is not a student
     */
    public boolean dropStudentFromCourse(Course c) {
        if (!(currentUser instanceof Student)) {
            throw new IllegalArgumentException("Illegal Action");
        }
        try {
            Student s = (Student) currentUser;
            c.getCourseRoll().drop(s);
            return s.getSchedule().removeCourseFromSchedule(c);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Resets the logged in student's schedule by dropping them from every course
     * and then resetting the schedule.
     * 
     * @throws IllegalArgumentException if the current user is not a student
     */
    public void resetSchedule() {
        if (!(currentUser instanceof Student)) {
            throw new IllegalArgumentException("Illegal Action");
        }
        try {
            Student s = (Student) currentUser;
            Schedule schedule = s.getSchedule();
            String[][] scheduleArray = schedule.getScheduledCourses();
            for (int i = 0; i < scheduleArray.length; i++) {
                Course c = courseCatalog.getCourseFromCatalog(scheduleArray[i][0], scheduleArray[i][1]);
                c.getCourseRoll().drop(s);
            }
            schedule.resetSchedule();
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }
    
    /**
     * Adds a course to a faculty's schedule if the current user is the registrar.
     * 
     * @param course  the course to add
     * @param faculty the faculty to add the course to
     * @return true if the course is successfully added, false otherwise
     * @throws IllegalArgumentException if the current user is not the registrar or if unable to add course to faculty's schedule
     */
    public boolean addFacultyToCourse(Course course, Faculty faculty) {
        if (currentUser != null && currentUser.equals(registrar)) {
            return faculty.getSchedule().addCourseToSchedule(course);
        } else {
            throw new IllegalArgumentException("Illegal Action");
        }
    }

    /**
     * Removes a course from a faculty's schedule if the current user is the registrar.
     * 
     * @param course  the course to remove
     * @param faculty the faculty to remove the course from
     * @return true if the course is successfully removed, false otherwise
     * @throws IllegalArgumentException if the current user is not the registrar or if unable to remove course from faculty's schedule
     */
    public boolean removeFacultyFromCourse(Course course, Faculty faculty) {
        if (currentUser != null && currentUser.equals(registrar)) {
            return faculty.getSchedule().removeCourseFromSchedule(course);
        } else {
            throw new IllegalArgumentException("Illegal Action");
        }
    }

    /**
     * Resets the schedule for a faculty member if the current user is the registrar.
     * 
     * @param faculty the faculty to reset the schedule for
     * @throws IllegalArgumentException if the current user is not the registrar or if unable to reset faculty's schedule
     */
    public void resetFacultySchedule(Faculty faculty) {
        if (currentUser != null && currentUser.equals(registrar)) {
            faculty.getSchedule().resetSchedule();
        } else {
            throw new IllegalArgumentException("Illegal Action");
        }
    }
    
}
