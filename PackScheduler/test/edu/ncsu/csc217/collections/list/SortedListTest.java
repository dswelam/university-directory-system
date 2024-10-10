package edu.ncsu.csc217.collections.list;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test for SortedList Functionality.
 * 
 * @author Dania Swelam
 */
public class SortedListTest {

	/**
	 * Test the constructor of SortedList.
	 */
	@Test
	public void testSortedList() {
		SortedList<String> list = new SortedList<String>();
		// Tests that the SortedList constructor is empty and starts with an index of 0.
		assertEquals(0, list.size());
		assertTrue(list.isEmpty());
		assertFalse(list.contains("apple"));

		// Test that the list grows by adding at least 11 elements
		// Remember the list's initial capacity is 10
		for (int i = 1; i <= 11; i++) {
			list.add("Element " + i);
		}

		// The list should have size 11
		assertEquals(11, list.size());

		// Ensure that all elements are present
		for (int i = 1; i <= 11; i++) {
			assertTrue(list.contains("Element " + i));
		}
	}

	/**
	 * Test the add() method of SortedList.
	 */
	@Test
	public void testAdd() {
		SortedList<String> list = new SortedList<String>();

		// Add an element and test
		list.add("banana");
		assertEquals(1, list.size());
		assertEquals("banana", list.get(0));

		// Add elements to the front
		list.add("apple");
		assertEquals(2, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));

		// Add elements to the back
		list.add("orange");
		assertEquals(3, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));
		assertEquals("orange", list.get(2));

		// Add elements to the middle
		list.add("grape");
		assertEquals(4, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));
		assertEquals("grape", list.get(2));
		assertEquals("orange", list.get(3));

		// Test adding a null element
		try {
			list.add(null);
			fail("Adding null element should throw NullPointerException");
		} catch (NullPointerException e) {
			// Expected exception
		}

		// Test adding a duplicate element
		try {
			list.add("banana");
			fail("Adding duplicate element should throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			// Expected exception
		}

		// Ensure that size of SortedList is still correct
		assertEquals(4, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));
		assertEquals("grape", list.get(2));
		assertEquals("orange", list.get(3));
	}

	/**
	 * Test the get() method of SortedList.
	 */
	@Test
	public void testGet() {
		SortedList<String> list = new SortedList<String>();

		// Since get() is used throughout the tests to check the
		// contents of the list, we don't need to test main flow functionality
		// here. Instead this test method should focus on the error
		// and boundary cases.

		// Test getting an element from an empty list
		assertEquals(0, list.size());

		try {
			list.get(1);
			fail("Getting an element from an empty list should throw IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
			// Expected exception
		}

		// Add some elements to the list
		list.add("apple");
		list.add("banana");
		list.add("grape");
		list.add("orange");

		try {
			list.get(-1);
			fail("Getting an element from an index less than 0 should throw an IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
			// Expected exception
		}

		// Test getting an element at size
		assertEquals(4, list.size());

		try {
			list.get(list.size());
			fail("Getting an element from an index greater than list.size() should throw an IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
			// Expected exception
		}
	}

	/**
	 * Test the remove(int) method of SortedList.
	 */
	@Test
	public void testRemove() {
		SortedList<String> list = new SortedList<String>();

		// Test removing from an empty list
		assertEquals(0, list.size());
		try {
			list.remove(0);
			fail("Removing an element from an empty list should throw an IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
			// Expected exception
		}

		// Add some elements to the list - at least 4
		list.add("apple");
		list.add("banana");
		list.add("grape");
		list.add("orange");
		list.add("papaya");

		try {
			list.remove(-1);
			fail("Removing an element from an index less than 0 should throw IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
			// Expected exception
		}

		// Test removing an element at size
		assertEquals(5, list.size());

		try {
			list.remove(list.size());
			fail("Removing an element from an index greater than list.size() should throw an IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
			// Expected exception
		}

		// Test removing a middle element
		list.remove(2);
		assertEquals(4, list.size());
		assertEquals("orange", list.get(2));

		// Test removing the last element
		list.remove(3);
		assertEquals(3, list.size());
		assertEquals("orange", list.get(2));

		// Test removing the first element
		list.remove(0);
		assertEquals(2, list.size());
		assertEquals("banana", list.get(0));

		// Test removing the last element
		list.remove(1);
		assertEquals(1, list.size());
	}

	/**
	 * Test the indexOf(Object) method of SortedList.
	 */
	@Test
	public void testIndexOf() {
		SortedList<String> list = new SortedList<String>();

		// Test indexOf on an empty list
		assertTrue(list.isEmpty());
	    assertEquals(-1, list.indexOf("apple"));

	    try {
	        list.indexOf(null);
	        fail("Calling indexOf with null should throw a NullPointerException");
	    } catch (NullPointerException e) {
	        // Expected exception
	    }

		// Add some elements to the list
		list.add("apple");
		list.add("banana");
		list.add("grape");
		list.add("orange");
		list.add("papaya");
		list.add("strawberry");
		list.add("yuzu");
		list.add("zucchini");

		// Test various calls to indexOf for elements in the list
		assertEquals(0, list.indexOf("apple"));
		assertEquals(1, list.indexOf("banana"));
		assertEquals(2, list.indexOf("grape"));
		assertEquals(3, list.indexOf("orange"));
		assertEquals(4, list.indexOf("papaya"));
		assertEquals(5, list.indexOf("strawberry"));
		assertEquals(6, list.indexOf("yuzu"));
		assertEquals(7, list.indexOf("zucchini"));

		// Test indexOf for an element not in the list
		assertEquals(-1, list.indexOf("mango"));

		// Test indexOf for null
		try {
			assertEquals(-1, list.indexOf(null));
		} catch (NullPointerException e) {
			// If indexOf throws NullPointerException
		}
	}

	/**
	 * Test the clear() method of SortedList.
	 */
	@Test
	public void testClear() {
		SortedList<String> list = new SortedList<String>();

		// Add some elements
		list.add("apple");
		list.add("banana");
		list.add("grape");

		// Clear the list
		list.clear();

		// Test that the list is empty
		assertEquals(0, list.size());
	}

	/**
	 * Test the isEmpty() method of SortedList.
	 */
	@Test
	public void testIsEmpty() {
		SortedList<String> list = new SortedList<String>();

		// Test that the list starts empty
		assertTrue(list.isEmpty());

		// Add at least one element
		list.add("apple");

		// Check that the list is no longer empty
		assertFalse(list.isEmpty());
	}

	/**
	 * Test the contains(Object) method of SortedList.
	 */
	@Test
	public void testContains() {
		SortedList<String> list = new SortedList<String>();

		// Test the empty list case
		assertFalse(list.contains("apple"));

		// Add some elements
		list.add("apple");
		list.add("banana");
		list.add("grape");

		// Test some true and false cases
		assertTrue(list.contains("apple"));
		assertTrue(list.contains("banana"));
		assertTrue(list.contains("grape"));
		assertFalse(list.contains("pineapple"));
		assertFalse(list.contains("peach"));
	}

	/**
	 * Test the equals(Object) method of SortedList.
	 */
	@Test
	public void testEquals() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();

		// Make two lists the same and one list different
		list1.add("apple");
		list1.add("banana");
		list1.add("cherry");

		list2.add("apple");
		list2.add("banana");
		list2.add("cherry");

		list3.add("apple");
		list3.add("banana");
		list3.add("date");

		// Test for equality and non-equality
		assertTrue(list1.equals(list2));
		assertFalse(list1.equals(list3));
	}

	/**
	 * Test the hashCode() method of SortedList.
	 */
	@Test
	public void testHashCode() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();

		// Make two lists the same and one list different
		list1.add("apple");
		list1.add("banana");
		list1.add("cherry");

		list2.add("apple");
		list2.add("banana");
		list2.add("cherry");

		list3.add("apple");
		list3.add("banana");
		list3.add("date");

		// Test for the same and different hashCodes
		assertEquals("Lists with the same elements should have the same hash code", list1.hashCode(), list2.hashCode());
		assertNotEquals("Lists with different elements should have different hash codes", list1.hashCode(),
				list3.hashCode());
	}
}
