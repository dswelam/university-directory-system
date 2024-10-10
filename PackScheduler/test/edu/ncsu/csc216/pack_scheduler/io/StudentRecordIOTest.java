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
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Tests StudentRecordIO
 * 
 * @author Dania Swelam
 * 
 */
class StudentRecordIOTest {

	/** Valid student records */
	private final String validTestFile = "test-files/student_records.txt";
	/** Invalid student records */
	private final String invalidTestFile = "test-files/invalid_student_records.txt";
	/** Valid Student Input */
	private String validStudent0 = "Zahir,King,zking,orci.Donec@ametmassaQuisque.com,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,15";
	/** Valid Student Input */
	private String validStudent1 = "Cassandra,Schwartz,cschwartz,semper@imperdietornare.co.uk,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,4";
	/** Valid Student Input */
	private String validStudent2 = "Shannon,Hansen,shansen,convallis.est.vitae@arcu.ca,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,14";
	/** Valid Student Input */
	private String validStudent3 = "Demetrius,Austin,daustin,Curabitur.egestas.nunc@placeratorcilacus.co.uk,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,18";
	/** Valid Student Input */
	private String validStudent4 = "Raymond,Brennan,rbrennan,litora.torquent@pellentesquemassalobortis.ca,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,12";
	/** Valid Student Input */
	private String validStudent5 = "Emerald,Frost,efrost,adipiscing@acipsumPhasellus.edu,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,3";
	/** Valid Student Input */
	private String validStudent6 = "Lane,Berg,lberg,sociis@non.org,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,14";
	/** Valid Student Input */
	private String validStudent7 = "Griffith,Stone,gstone,porta@magnamalesuadavel.net,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,17";
	/** Valid Student Input */
	private String validStudent8 = "Althea,Hicks,ahicks,Phasellus.dapibus@luctusfelis.com,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,11";
	/** Valid Student Input */
	private String validStudent9 = "Dylan,Nolan,dnolan,placerat.Cras.dictum@dictum.net,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,5";
	/** Array of Full Valid Student Input */
	private String[] validStudents = { validStudent0, validStudent1, validStudent2, validStudent3, validStudent4,
			validStudent5, validStudent6, validStudent7, validStudent8, validStudent9 };
	/** Hashed Password */
	private String pw = "l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=";
	/** Student's password */
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

			for (int i = 0; i < validStudents.length; i++) {
				validStudents[i] = validStudents[i].replace(",pw,", "," + hashPW + ",");
			}
		} catch (NoSuchAlgorithmException e) {
			fail("Unable to create hash during setup");
		}
	}

	/**
	 * Tests studentRecordIOConstructor() to verify that the class exists
	 */
	@Test
	public void testStudentRecordIOConstructor() {
		assertAll(() -> new StudentRecordIO());
	}

	/**
	 * Tests readStudentRecords().
	 */
	@Test
	void testReadStudentRecordsValid() {
		try {
			final SortedList<Student> students = StudentRecordIO.readStudentRecords(validTestFile);
			assertEquals(10, students.size());

			String[] sortedValidStudents = {
					"Demetrius,Austin,daustin,Curabitur.egestas.nunc@placeratorcilacus.co.uk,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,18",
					"Lane,Berg,lberg,sociis@non.org,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,14",
					"Raymond,Brennan,rbrennan,litora.torquent@pellentesquemassalobortis.ca,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,12",
					"Emerald,Frost,efrost,adipiscing@acipsumPhasellus.edu,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,3",
					"Shannon,Hansen,shansen,convallis.est.vitae@arcu.ca,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,14",
					"Althea,Hicks,ahicks,Phasellus.dapibus@luctusfelis.com,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,11",
					"Zahir,King,zking,orci.Donec@ametmassaQuisque.com,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,15",
					"Dylan,Nolan,dnolan,placerat.Cras.dictum@dictum.net,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,5",
					"Cassandra,Schwartz,cschwartz,semper@imperdietornare.co.uk,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,4",
					"Griffith,Stone,gstone,porta@magnamalesuadavel.net,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,17", };

			for (int i = 0; i < sortedValidStudents.length; i++) {
				assertEquals(sortedValidStudents[i], students.get(i).toString());
			}

		} catch (final FileNotFoundException e) {
			fail("Unexpected error reading " + validTestFile);
		}
	}

	/**
	 * Tests invalid file reading for readStudentRecords().
	 */
	@Test
	void testReadStudentRecordsInvalid() {
		SortedList<Student> students;
		try {
			students = StudentRecordIO.readStudentRecords(invalidTestFile);
			assertEquals(0, students.size());
			

		} catch (final FileNotFoundException e) {
			fail("Unexpected FileNotFoundException");
		}
	}

	/**
	 * Tests writeStudentRecords().
	 */
	@Test
	void testWriteStudentRecords() {
		final SortedList<Student> students = new SortedList<Student>();
		students.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", pw, 15));

		try {
			StudentRecordIO.writeStudentRecords("test-files/actual_course_records.txt", students);
		} catch (final IOException e) {
			fail("Cannot write to student records file");
		}

		checkFiles("test-files/expected_student_records.txt", "test-files/actual_student_records.txt");
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
