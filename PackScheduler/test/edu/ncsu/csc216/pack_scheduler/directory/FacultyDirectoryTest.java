package edu.ncsu.csc216.pack_scheduler.directory;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests FacultyDirectory.
 * 
 * @author Dania Swelam
 */
public class FacultyDirectoryTest {

    /** Valid faculty records */
    private final String validTestFile = "test-files/faculty_records.txt";
    /** Expected faculty records */
    private final String expectedFullFile = "test-files/expected_full_faculty_records.txt";

    /** FacultyDirectory instance to be tested */
    private FacultyDirectory fd;

    /**
     * Sets up the FacultyDirectory for testing.
     * 
     * @throws Exception if something fails during setup.
     */
    @Before
    public void setUp() throws Exception {
        fd = new FacultyDirectory();
    }

    /**
     * Tests addFaculty().
     */
    @Test
    public void testAddFaculty() {
        fd.newFacultyDirectory();
        assertTrue(fd.addFaculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", "pw", "pw", 2));
        assertFalse(fd.addFaculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", "pw", "pw", 2));
    }

    /**
     * Tests removeFaculty().
     */
    @Test
    public void testRemoveFaculty() {
        fd.newFacultyDirectory();
        assertTrue(fd.addFaculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", "pw", "pw", 2));
        assertTrue(fd.removeFaculty("awitt"));
        assertFalse(fd.removeFaculty("awitt"));
    }

    /**
     * Tests loadFacultyFromFile().
     */
    @Test
    public void testLoadFacultyFromFile() {
        fd.newFacultyDirectory();
        fd.loadFacultyFromFile(validTestFile);
        String[][] facultyDirectory = fd.getFacultyDirectory();
        assertEquals(8, facultyDirectory.length);

        assertEquals("Ashely", facultyDirectory[0][0]);
        assertEquals("Witt", facultyDirectory[0][1]);
        assertEquals("awitt", facultyDirectory[0][2]);

        assertEquals("Fiona", facultyDirectory[1][0]);
        assertEquals("Meadows", facultyDirectory[1][1]);
        assertEquals("fmeadow", facultyDirectory[1][2]);

        assertEquals("Brent", facultyDirectory[2][0]);
        assertEquals("Brewer", facultyDirectory[2][1]);
        assertEquals("bbrewer", facultyDirectory[2][2]);

        assertEquals("Halla", facultyDirectory[3][0]);
        assertEquals("Aguirre", facultyDirectory[3][1]);
        assertEquals("haguirr", facultyDirectory[3][2]);

        assertEquals("Kevyn", facultyDirectory[4][0]);
        assertEquals("Patel", facultyDirectory[4][1]);
        assertEquals("kpatel", facultyDirectory[4][2]);

        assertEquals("Elton", facultyDirectory[5][0]);
        assertEquals("Briggs", facultyDirectory[5][1]);
        assertEquals("ebriggs", facultyDirectory[5][2]);

        assertEquals("Norman", facultyDirectory[6][0]);
        assertEquals("Brady", facultyDirectory[6][1]);
        assertEquals("nbrady", facultyDirectory[6][2]);

        assertEquals("Lacey", facultyDirectory[7][0]);
        assertEquals("Walls", facultyDirectory[7][1]);
        assertEquals("lwalls", facultyDirectory[7][2]);
    }

    /**
     * Tests saveFacultyDirectory().
     */
    @Test
    public void testSaveFacultyDirectory() {
        FacultyDirectory faculty = new FacultyDirectory();
        
        faculty.addFaculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", "pw", "pw", 2);
		faculty.addFaculty("Fiona", "Meadows", "fmeadow", "pharetra.sed@et.org", "pw", "pw", 3);
		faculty.addFaculty("Brent", "Brewer", "bbrewer", "sem.semper@orcisem.co.uk", "pw", "pw", 1);
        faculty.loadFacultyFromFile(validTestFile);
        faculty.saveFacultyDirectory("test-files/actual_full_faculty_records.txt");
        assertEquals(8, faculty.getFacultyDirectory().length);

        checkFiles(expectedFullFile, "test-files/actual_full_faculty_records.txt");       
    }

    /**
     * Helper method to compare two files for the same contents.
     * 
     * @param expFile  expected output file
     * @param actFile  actual output file
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

    /**
     * Tests getFacultyDirectory().
     */
    @Test
    public void testGetFacultyDirectory() {
        fd.newFacultyDirectory();
        fd.loadFacultyFromFile(validTestFile);
        String[][] facultyDirectory = fd.getFacultyDirectory();
        assertEquals(8, facultyDirectory.length);

        assertEquals("Ashely", facultyDirectory[0][0]);
        assertEquals("Witt", facultyDirectory[0][1]);
        assertEquals("awitt", facultyDirectory[0][2]);

        assertEquals("Fiona", facultyDirectory[1][0]);
        assertEquals("Meadows", facultyDirectory[1][1]);
        assertEquals("fmeadow", facultyDirectory[1][2]);

        assertEquals("Brent", facultyDirectory[2][0]);
        assertEquals("Brewer", facultyDirectory[2][1]);
        assertEquals("bbrewer", facultyDirectory[2][2]);

        assertEquals("Halla", facultyDirectory[3][0]);
        assertEquals("Aguirre", facultyDirectory[3][1]);
        assertEquals("haguirr", facultyDirectory[3][2]);

        assertEquals("Kevyn", facultyDirectory[4][0]);
        assertEquals("Patel", facultyDirectory[4][1]);
        assertEquals("kpatel", facultyDirectory[4][2]);

        assertEquals("Elton", facultyDirectory[5][0]);
        assertEquals("Briggs", facultyDirectory[5][1]);
        assertEquals("ebriggs", facultyDirectory[5][2]);

        assertEquals("Norman", facultyDirectory[6][0]);
        assertEquals("Brady", facultyDirectory[6][1]);
        assertEquals("nbrady", facultyDirectory[6][2]);

        assertEquals("Lacey", facultyDirectory[7][0]);
        assertEquals("Walls", facultyDirectory[7][1]);
        assertEquals("lwalls", facultyDirectory[7][2]);
    }

    /**
     * Tests getFacultyById().
     */
    @Test
    public void testGetFacultyById() {
        fd.newFacultyDirectory();
        fd.addFaculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", "pw", "pw", 2);
        assertNotNull(fd.getFacultyById("awitt"));
        assertNull(fd.getFacultyById("invalid"));
    }
}
