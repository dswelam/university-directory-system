/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests the ArrayList class.
 * 
 * @param <E> Object element being stored in Generic Array List.
 * @author Dania Swelam
 */
class ArrayListTest<E> {

	/**
	 * Test method for ArrayList Constructor.
	 */
	@Test
	void testArrayListConstructor() {
		
		ArrayList<E> list = new ArrayList<>();
		assertEquals(0, list.size());
	}

	/**
	 * Test method for add().
	 */
	@Test
	void testAdd() {
		
		ArrayList<String> list = new ArrayList<String>();
		
		list.add(0, "apple");
		list.add(1, "orange");
		list.add(2, "banana");
		list.add(3, "grape");
		
		assertEquals(list.get(0), "apple");
		assertEquals(list.get(2), "banana");
		assertEquals(list.get(3), "grape");
		assertEquals(4, list.size());
		
		try {
            list.add(3, "apple");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }

        try {
            list.add(0, null);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }

        try {
            list.add(-1, "carrot");
            fail("Should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }

        try {
            list.add(8, "C");
            fail("Should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
	}

	/**
	 * Test method for remove(int).
	 */
	@Test
	void testRemoveInt() {
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		list.add(0, 2);
		list.add(1, 4);
		list.add(2, 6);
		list.add(3, 8);
		
		assertEquals(list.remove(2), 6);
		assertEquals(list.size(), 3);
		
		try {
            list.remove(10);
            fail("Should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
		
	}

	/**
	 * Test method for set().
	 */
	@Test
	void testSetIntE() {
		ArrayList<String> list = new ArrayList<>();
		list.add("apple");
        list.add("banana");
        list.add("guava");

        String oldElement = list.set(1, "mango");
        assertEquals("banana", oldElement);
        assertEquals("mango", list.get(1));

        assertThrows(NullPointerException.class, () -> {
            list.set(1, null);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.set(5, "peach");
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.set(-1, "strawberry");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            list.set(1, "apple");
        });
	}

	/**
	 * Test method for get().
	 */
	@Test
	void testGetInt() {
		ArrayList<String> list = new ArrayList<>();
        list.add("apple");
        list.add("banana");
        list.add("guava");

        String element = list.get(1);
        assertEquals("banana", element);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(5);
        });
        
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(-1);
        });
	}
	
	@Test
	void testGrowArray() {
		ArrayList<String> list = new ArrayList<>();
		
		 //Adding 10 elements to list.
		 list.add("apple");
	     list.add("banana");
	     list.add("guava");
	     list.add("pear");
	     list.add("peach");
	     list.add("mango");
	     list.add("orange");
	     list.add("strawberry");
	     list.add("blueberry");
	     list.add("kiwi");
	     list.add("grapefruit");
	     
	     assertEquals(11, list.size());
	}
	
	/**
	 * Test for Equals().
	 */
	@Test
	void testEquals() {
		
		ArrayList<String> list1 = new ArrayList<>();
        list1.add(0, "apple");
        list1.add(1, "banana");
        list1.add(2, "cherry");

        ArrayList<String> list2 = new ArrayList<>();
        list2.add(0, "apple");
        list2.add(1, "banana");
        list2.add(2, "cherry");

        ArrayList<String> list3 = new ArrayList<>();
        list3.add(0, "date");
        list3.add(1, "fig");
        list3.add(2, "grape");
        
        // Test same object
        assertTrue(list1.equals(list1));

        // Test null
        assertNotNull(list1);

        // Test equal objects
        assertTrue(list1.equals(list2));

        // Test different objects
        assertFalse(list1.equals(list3));

        // Test different sizes
        list2.add(3, "date");
        assertFalse(list1.equals(list2));
	}
	
	/**
	 * Test for HashCode().
	 */
	@Test
    public void testHashCode() {
		
		ArrayList<String> list1 = new ArrayList<>();
        list1.add(0, "apple");
        list1.add(1, "banana");
        list1.add(2, "cherry");

        ArrayList<String> list2 = new ArrayList<>();
        list2.add(0, "apple");
        list2.add(1, "banana");
        list2.add(2, "cherry");

        ArrayList<String> list3 = new ArrayList<>();
        list3.add(0, "date");
        list3.add(1, "fig");
        list3.add(2, "grape");
        
        // Test equal objects have same hash code
        assertEquals(list1.hashCode(), list2.hashCode());

        // Test different objects have different hash codes
        assertNotEquals(list1.hashCode(), list3.hashCode());
    }

}
