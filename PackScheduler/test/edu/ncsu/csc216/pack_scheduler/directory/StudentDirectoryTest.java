package edu.ncsu.csc216.pack_scheduler.directory;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Tests StudentDirectory.
 * 
 * @author Sarah Heckman
 */
public class StudentDirectoryTest {

    /** Valid student records */
    private final String validTestFile = "test-files/student_records.txt";
    /** Invalid student records */
    private final String invalidTestFile = "non-existent_file.txt";
    /** Test first name */
    private static final String FIRST_NAME = "Zahir";
    /** Test last name */
    private static final String LAST_NAME = "King";
    /** Test id */
    private static final String ID = "zking";
    /** Test email */
    private static final String EMAIL = "orci.Donec@ametmassaQuisque.com";
    /** Test password */
    private static final String PASSWORD = "pw";
    /** Test max credits */
    private static final int MAX_CREDITS = 15;

    /**
     * Resets student_records.txt for use in other tests.
     * 
     * @throws Exception if something fails during setup.
     */
    @BeforeEach
    public void setUp() throws Exception {
        // Reset student_records.txt so that it's fine for other needed tests
        Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_full_student_records.txt");
        Path destinationPath = FileSystems.getDefault().getPath("test-files", "student_records.txt");
        try {
            Files.deleteIfExists(destinationPath);
            Files.copy(sourcePath, destinationPath);
        } catch (IOException e) {
            fail("Unable to reset files");
        }
    }

    /**
     * Tests StudentDirectory().
     */
    @Test
    public void testStudentDirectory() {
        // Test that the StudentDirectory is initialized to an empty list
        StudentDirectory sd = new StudentDirectory();
        assertFalse(sd.removeStudent("sesmith5"));
        assertEquals(0, sd.getStudentDirectory().length);
    }

    /**
     * Tests StudentDirectory.newStudentDirectory().
     */
    @Test
    public void testNewStudentDirectory() {
        // Test that if there are students in the directory, they
        // are removed after calling newStudentDirectory().
        StudentDirectory sd = new StudentDirectory();

        sd.loadStudentsFromFile(validTestFile);
        assertEquals(10, sd.getStudentDirectory().length);

        sd.newStudentDirectory();
        assertEquals(0, sd.getStudentDirectory().length);
    }

    /**
     * Tests StudentDirectory.loadStudentsFromFile().
     */
    @Test
    public void testLoadStudentsFromFile() {
        StudentDirectory sd = new StudentDirectory();
        
        // Test valid file
        sd.loadStudentsFromFile(validTestFile);
        assertEquals(10, sd.getStudentDirectory().length);
    }

    /**
     * Tests StudentDirectory.loadStudentsFromFile() with an invalid file.
     */
    @Test
    public void testLoadStudentsFromFileInvalid() {
        StudentDirectory sd = new StudentDirectory();

        // Test invalid file
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> sd.loadStudentsFromFile(invalidTestFile));
        assertEquals("Unable to read file " + invalidTestFile, e1.getMessage());
    }

    /**
     * Tests StudentDirectory.addStudent().
     */
    @Test
    public void testAddStudentValid() {
        StudentDirectory sd = new StudentDirectory();

        // Test valid Student
        assertTrue(sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS));
        String[][] studentDirectory = sd.getStudentDirectory();
        assertEquals(1, studentDirectory.length);
        assertEquals(FIRST_NAME, studentDirectory[0][0]);
        assertEquals(LAST_NAME, studentDirectory[0][1]);
        assertEquals(ID, studentDirectory[0][2]);
    }

    /**
     * Tests StudentDirectory.addStudent() with invalid entries
     * 
     * @param password invalid password
     */
    @ParameterizedTest
    @ValueSource(strings = { "", " ", " " })
    public void testAddStudentInvalidEmptyPasswordsNotNull(String password) {
        StudentDirectory sd = new StudentDirectory();

        assertThrows(IllegalArgumentException.class, () -> sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, password, MAX_CREDITS), "Invalid password");
    }

    /**
     * Tests StudentDirectory.addStudent() with non-empty, non-matching passwords
     * 
     * @param nonMatchingPassword invalid passowrd
     */
    @ParameterizedTest
    @ValueSource(strings = { "glub", "glob" })
    public void testAddStudentInvalidPasswordsNotMatching(String nonMatchingPassword) {
        StudentDirectory sd = new StudentDirectory();

        assertThrows(IllegalArgumentException.class, () -> sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, nonMatchingPassword, PASSWORD, MAX_CREDITS), "Passwords do not match");
        assertThrows(IllegalArgumentException.class, () -> sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, nonMatchingPassword, MAX_CREDITS), "Passwords do not match");
    }

    /**
     * Tests StudentDirectory.addStudent() with null values
     */
    @Test
    public void testAddStudentInvalidPasswordsNull() {
        StudentDirectory sd = new StudentDirectory();

        assertThrows(IllegalArgumentException.class, () -> sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, null, null, MAX_CREDITS), "Invalid password");
        assertThrows(IllegalArgumentException.class, () -> sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, null, PASSWORD, MAX_CREDITS), "Invalid password");
        assertThrows(IllegalArgumentException.class, () -> sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, null, MAX_CREDITS), "Invalid password");
        assertThrows(IllegalArgumentException.class, () -> sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, "", null, MAX_CREDITS), "Invalid password");
        assertThrows(IllegalArgumentException.class, () -> sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, null, "", MAX_CREDITS), "Invalid password");
    }

    /**
     * Tests StudentDirectory.addStudent() with duplicate student
     */
    @Test
    public void testAddDuplicateStudent() {
        StudentDirectory sd = new StudentDirectory();

        assertTrue(sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS));
        assertFalse(sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS));
        assertTrue(sd.addStudent("Johnny", "Cash", "jcash", "jcash@folsom.com", "ringoffire", "ringoffire", MAX_CREDITS));
    }

    /**
     * Tests StudentDirectory.addStudent() with invalid maxCredits
     * 
     * @param invalidCredits invalid credits
     */
    @ParameterizedTest
    @ValueSource(ints = { 1, 19 })
    public void testAddStudentInvalidMaxCredits(int invalidCredits) {
        StudentDirectory sd = new StudentDirectory();

        assertTrue(sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, invalidCredits));
        String[][] dir = sd.getStudentDirectory();
        assertEquals(1, dir.length);
    }

    /**
     * Tests StudentDirectory.removeStudent().
     */
    @Test
    public void testRemoveStudent() {
        StudentDirectory sd = new StudentDirectory();

        // Add students and remove
        sd.loadStudentsFromFile(validTestFile);
        assertEquals(10, sd.getStudentDirectory().length);
        assertTrue(sd.removeStudent("efrost"));
        String[][] studentDirectory = sd.getStudentDirectory();
        assertEquals(9, studentDirectory.length);
        assertEquals("Zahir", studentDirectory[5][0]);
        assertEquals("King", studentDirectory[5][1]);
        assertEquals("zking", studentDirectory[5][2]);
    }

    /**
     * Tests StudentDirectory.saveStudentDirectory().
     */
    @Test
    public void testSaveStudentDirectory() {
        StudentDirectory sd = new StudentDirectory();

        // Add a student
        sd.addStudent("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "pw", "pw", 15);
        assertEquals(1, sd.getStudentDirectory().length);
        sd.saveStudentDirectory("test-files/actual_student_records.txt");
        checkFiles("test-files/expected_student_records.txt", "test-files/actual_student_records.txt");
    }

    /**
     * Tests StudentDirectory.saveStudentDirectory() with invalid file path.
     */
    @Test
    public void testSaveStudentDirectoryInvalid() {
        StudentDirectory sd = new StudentDirectory();

        sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS);

        Exception e1 = assertThrows(IllegalArgumentException.class, () -> sd.saveStudentDirectory(""));
        assertEquals("Unable to write to file ", e1.getMessage());
    }

    /**
     * Tests StudentDirectory.getStudentById().
     */
    @Test
    public void testGetStudentById() {
        StudentDirectory sd = new StudentDirectory();

        // Add a student
        sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS);
        Student student = sd.getStudentById(ID);
        assertNotNull(student);
        assertEquals(FIRST_NAME, student.getFirstName());
        assertEquals(LAST_NAME, student.getLastName());
        assertEquals(ID, student.getId());

        // Test for non-existent student
        assertNull(sd.getStudentById("null"));
    }

    /**
     * Helper method to compare two files for the same contents
     * 
     * @param expFile expected output
     * @param actFile actual output
     */
    private void checkFiles(String expFile, String actFile) {
        try {
            Scanner expScanner = new Scanner(new FileInputStream(expFile));
            Scanner actScanner = new Scanner(new FileInputStream(actFile));

            while (expScanner.hasNextLine()) {
                assertEquals(expScanner.nextLine(), actScanner.nextLine());
            }

            expScanner.close();
            actScanner.close();
        } catch (IOException e) {
            fail("Error reading files.");
        }
    }
}
