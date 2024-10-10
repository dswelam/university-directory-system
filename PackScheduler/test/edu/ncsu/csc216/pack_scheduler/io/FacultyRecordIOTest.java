package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Tests FacultyRecordIO
 * 
 * @author Dania Swelam
 * 
 */
class FacultyRecordIOTest {

	/** Valid faculty records */
	private final String validTestFile = "test-files/faculty_records.txt";
	/** Invalid faculty records */
	private final String invalidTestFile = "test-files/invalid_faculty_records.txt";
	/** Valid Faculty Input */
	private String validFaculty0 = "Ashely,Witt,awitt,mollis@Fuscealiquetmagna.net,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,2";
	/** Valid Faculty Input */
	private String validFaculty1 = "Fiona,Meadows,fmeadow,pharetra.sed@et.org,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,3";
	/** Valid Faculty Input */
	private String validFaculty2 = "Brent,Brewer,bbrewer,sem.semper@orcisem.co.uk,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,1";
	/** Valid Faculty Input */
	private String validFaculty3 = "Halla,Aguirre,haguirr,Fusce.dolor.quam@amalesuadaid.net,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,3";
	/** Valid Faculty Input */
	private String validFaculty4 = "Kevyn,Patel,kpatel,risus@pellentesque.ca,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,1";
	/** Valid Faculty Input */
	private String validFaculty5 = "Elton,Briggs,ebriggs,arcu.ac@ipsumsodalespurus.edu,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,3";
	/** Valid Faculty Input */
	private String validFaculty6 = "Norman,Brady,nbrady,pede.nonummy@elitfermentum.co.uk,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,1";
	/** Valid Faculty Input */
	private String validFaculty7 = "Lacey,Walls,lwalls,nascetur.ridiculus.mus@fermentum.net,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,2";

	/** Array of Full Valid Faculty Input */
	private String[] validFaculties = { validFaculty0, validFaculty1, validFaculty2, validFaculty3, validFaculty4,
			validFaculty5, validFaculty6, validFaculty7 };
	/** Hashed Password */
	private String pw = "MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=";
	/** Faculty's password */
	private String hashPW;
	/** Hash Algorithim for password */
	private static final String HASH_ALGORITHM = "SHA-256";

	@BeforeEach
	public void setUp() {
		try {
			String password = "pw";
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(password.getBytes());
			hashPW = Base64.getEncoder().encodeToString(digest.digest());

			for (int i = 0; i < validFaculties.length; i++) {
				validFaculties[i] = validFaculties[i].replace(",pw,", "," + hashPW + ",");
			}
		} catch (NoSuchAlgorithmException e) {
			fail("Unable to create hash during setup");
		}
	}

	/**
	 * Tests studentRecordIOConstructor() to verify that the class exists
	 */
	@Test
	public void testFacultyRecordIOConstructor() {
		assertAll(() -> new FacultyRecordIO());
	}

	/**
	 * Tests readFacultyRecords().
	 */
	@Test
	void testReadFacultyRecordsValid() {
		try {
			final LinkedList<Faculty> faculties = FacultyRecordIO.readFacultyRecords(validTestFile);
			assertEquals(8, faculties.size());

			String[] validFaculty = {
					"Ashely,Witt,awitt,mollis@Fuscealiquetmagna.net,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,2",
					"Fiona,Meadows,fmeadow,pharetra.sed@et.org,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,3",
					"Brent,Brewer,bbrewer,sem.semper@orcisem.co.uk,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,1",
					"Halla,Aguirre,haguirr,Fusce.dolor.quam@amalesuadaid.net,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,3",
					"Kevyn,Patel,kpatel,risus@pellentesque.ca,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,1",
					"Elton,Briggs,ebriggs,arcu.ac@ipsumsodalespurus.edu,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,3",
					"Norman,Brady,nbrady,pede.nonummy@elitfermentum.co.uk,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,1",
					"Lacey,Walls,lwalls,nascetur.ridiculus.mus@fermentum.net,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,2" };

			for (int i = 0; i < validFaculty.length; i++) {
				assertEquals(validFaculty[i], faculties.get(i).toString());
			}

		} catch (final FileNotFoundException e) {
			fail("Unexpected error reading " + validTestFile);
		}
	}

	/**
	 * Tests invalid file reading for readFacultyRecords().
	 */
	@Test
	void testReadFacultyRecordsInvalid() {
		LinkedList<Faculty> faculties;
		try {
			faculties = FacultyRecordIO.readFacultyRecords(invalidTestFile);
			assertEquals(0, faculties.size());

		} catch (final FileNotFoundException e) {
			fail("Unexpected FileNotFoundException");
		}
	}

	/**
	 * Tests writeFacultyRecords().
	 */
	@Test
	void testWriteFacultyRecords() {
		final LinkedList<Faculty> faculties = new LinkedList<Faculty>();
		faculties.add(new Faculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", pw, 2));
		faculties.add(new Faculty("Fiona", "Meadows", "fmeadow", "pharetra.sed@et.org", pw, 3));
		faculties.add(new Faculty("Brent", "Brewer", "bbrewer", "sem.semper@orcisem.co.uk", pw, 1));

		try {
			FacultyRecordIO.writeFacultyRecords("test-files/actual_faculty_records.txt", faculties);
		} catch (final IOException e) {
			fail("Cannot write to faculty records file");
		}

		checkFiles("test-files/expected_faculty_records.txt", "test-files/actual_faculty_records.txt");
	}

	/**
	 * Helper method to compare two files for the same contents
	 * 
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new FileInputStream(expFile));
				Scanner actScanner = new Scanner(new FileInputStream(actFile));) {

			while (expScanner.hasNextLine() && actScanner.hasNextLine()) {
				String exp = expScanner.nextLine();
				String act = actScanner.nextLine();
				assertEquals(exp, act, "Expected: " + exp + " Actual: " + act);
				// The third argument helps with debugging!
			}
			if (expScanner.hasNextLine()) {
				fail("The expected results expect another line " + expScanner.nextLine());
			}
			if (actScanner.hasNextLine()) {
				fail("The actual results has an extra, unexpected line: " + actScanner.nextLine());
			}

			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

}
