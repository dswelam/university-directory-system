package edu.ncsu.csc216.pack_scheduler.catalog;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Tests CourseCatalog.
 * 
 * @author Dania Swelam
 * 
 */
public class CourseCatalogTest {

	/** Valid course records */
	private final String validTestFile = "test-files/course_records.txt";

	/**
	 * Resets course_records.txt for use in other tests.
	 * 
	 * @throws Exception if something fails during setup.
	 */
	@Before
	public void setUp() throws Exception {
		// Reset course_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_course_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "course_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}

	/**
	 * Tests CourseCatalog().
	 */
	@Test
	public void testCourseCatalog() {
		// Test that the CourseCatalog is initialized to an empty list
		CourseCatalog cc = new CourseCatalog();
		assertFalse(cc.removeCourseFromCatalog("CSC216", "001"));
		assertEquals(0, cc.getCourseCatalog().length);
	}

	/**
	 * Tests CourseCatalog.testNewCourseCatalog().
	 */
	@Test
	public void testNewCourseCatalog() {
		// Test that if there are courses in the catalog, they are removed after calling
		// newCourseCatalog().
		CourseCatalog cc = new CourseCatalog();

		cc.loadCoursesFromFile(validTestFile);
		assertEquals(13, cc.getCourseCatalog().length);

		cc.newCourseCatalog();
		assertEquals(0, cc.getCourseCatalog().length);
	}

	/**
	 * Tests CourseCatalog.loadCoursesFromFile().
	 */
	@Test
	public void testLoadCoursesFromFile() {
		CourseCatalog cc = new CourseCatalog();

		// Test valid file
		cc.loadCoursesFromFile(validTestFile);
		assertEquals(13, cc.getCourseCatalog().length);
	}

	/**
	 * Tests CourseCatalog.loadCoursesFromFile() with an invalid file.
	 */
	@Test
	public void testLoadCoursesFromFileInvalid() {
		CourseCatalog cc = new CourseCatalog();

		// Test invalid file
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			cc.loadCoursesFromFile("test-files/nonexisting_course_records.txt");
		});

		assertEquals("Cannot find file.", exception.getMessage());
	}

	/**
	 * Tests adding a course to the catalog.
	 */
	@Test
	public void testAddCourseToCatalog() {
		CourseCatalog catalog = new CourseCatalog();
		assertTrue(catalog.addCourseToCatalog("CSC116", "Intro to Programming - Java", "004", 3, "jdyoung2", 10, "MW",
				910, 1100));
	}

	/**
	 * Tests CourseCatalog.removeCourseFromCatalog().
	 */
	@Test
	public void testRemoveCourseFromCatalog() {
		CourseCatalog cc = new CourseCatalog();

		cc.addCourseToCatalog("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1330,
				1445);
		assertTrue(cc.removeCourseFromCatalog("CSC216", "001"));
		assertFalse(cc.removeCourseFromCatalog("CSC216", "001"));
	}

	/**
	 * Tests CourseCatalog.getCourseFromCatalog().
	 */
	@Test
	public void testGetCourseFromCatalog() {
		CourseCatalog cc = new CourseCatalog();

		cc.addCourseToCatalog("CSC216", "Software Development", "001", 3, "sesmith5", 10, "MW", 1330, 1445);
		Course c = cc.getCourseFromCatalog("CSC216", "001");
		assertNotNull(c);
		assertEquals("CSC216", c.getName());
		assertNull(cc.getCourseFromCatalog("CSC226", "001"));
	}

	/**
	 * Tests CourseCatalog.getCourseCatalog().
	 */
	@Test
	public void testGetCourseCatalog() {
		CourseCatalog cc = new CourseCatalog();

		cc.addCourseToCatalog("CSC216", "Software Development", "001", 3, "sesmith5", 10, "MW", 1330, 1445);
		String[][] catalogArray = cc.getCourseCatalog();
		assertEquals(1, catalogArray.length);
		assertEquals("CSC216", catalogArray[0][0]);
	}

	/**
	 * Tests saving the catalog of courses to a file.
	 */
	@Test
	public void testSaveCourseCatalog() {
		CourseCatalog catalog = new CourseCatalog();
		catalog.addCourseToCatalog("CSC116", "Intro to Programming - Java", "001", 4, "instructorId", 10, "MW", 1330,
				1445);
		catalog.saveCourseCatalog("test-files/expected_empty_export.txt");
		assertTrue(new File("test-files/expected_empty_export.txt").exists());
	}

	/**
	 * Tests CourseCatalog.saveCourseCatalog() with an invalid file.
	 */
	@Test
	public void testSaveCourseCatalogInvalid() {
		CourseCatalog cc = new CourseCatalog();

		cc.addCourseToCatalog("CSC216", "Software Development", "001", 3, "sesmith5", 10, "MW", 1330, 1445);

		Exception e1 = assertThrows(IllegalArgumentException.class, () -> cc.saveCourseCatalog(""));
		assertEquals("Unable to write to file ", e1.getMessage());
	}
}