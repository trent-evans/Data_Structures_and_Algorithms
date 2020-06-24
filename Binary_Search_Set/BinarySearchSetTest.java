package lab05;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import assignment05.BinarySearchTree;
import org.junit.Assert;

public class BinarySearchSetTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		Integer[] testArray = {1,3,4,5,7};

		BinarySearchSet<Integer> newSet = new BinarySearchSet<Integer>();
		
		
		// Test binarySearch	
//		Assert.assertEquals(newSet.binarySearch(testArray, 2), 1); // In the array
//		Assert.assertEquals(newSet.binarySearch(testArray, 3), -1); // Out of the array
//		Assert.assertEquals(newSet.binarySearch(testArray, -1), 0); // Goes before the array
//		Assert.assertEquals(newSet.binarySearch(testArray, 8), 4); // Goes after the array
//		Assert.assertEquals(newSet.binarySearch(testArray, 6), 4); // In the array
		
		// Note that both 6 and 8 provided the same index value.
		// This makes sense because of how my binarySearch is implemented 
		// Because of this, when implementing add, I'll need to make a special case for if the returned index is .size-1
		// Changes were made to the add code (you can see in the method) that make it so these asserts don't work anymore
		// However, they worked previously, which helped me to know that the binarySearch was working
		
		// Size and isEmpty stuff
		Assert.assertEquals(0, newSet.size());
		Assert.assertEquals(true, newSet.isEmpty());
		
		
		// First and last test on empty set
		try {
			newSet.first();
		}catch(NoSuchElementException e){
			System.out.println("TEST SUCESSFUL - First exception thrown on empty array");
		}
		
		try {
			newSet.last();
		}catch(NoSuchElementException e){
			System.out.println("TEST SUCESSFUL - Last exception thrown on empty array");
		}
		
		// Test add (also some first and last stuff here
		
		Assert.assertEquals(true, newSet.add((Integer)1)); // Add the first element
		
		Assert.assertEquals((Integer)1, newSet.first()); // Single element test for first
		Assert.assertEquals((Integer)1, newSet.last()); // Single element test for last
		Assert.assertEquals(newSet.first(), newSet.last()); // Ergo, the two elements should be the same
		
		Assert.assertEquals(true, newSet.add((Integer)4)); // Add the second element
		Assert.assertEquals(false, newSet.add((Integer)4)); // Try to repeat an element
		Assert.assertEquals(true, newSet.add((Integer)6)); // Add element on the end
		Assert.assertEquals(true, newSet.add((Integer)3)); // Add an element in the middle
		Assert.assertEquals(true, newSet.add((Integer)5)); // Add an element in the same index as the end
		
		// Resulting array should be {1,3,4,5,6}
		
		// Size and isEmpty retest since adding values
		Assert.assertEquals(5, newSet.size());
		Assert.assertEquals(false, newSet.isEmpty());
		
		// Make an array with toArray()
		Object[] ansArray = newSet.toArray();
		
		// Check to make sure each value is less than the next one to double check sorting
		for(int x = 0; x < ansArray.length-1; x++) {
			Assert.assertEquals(true,(Integer)ansArray[x] < (Integer)ansArray[x+1]);
		}
		
		// Contains check
		Assert.assertEquals(true, newSet.contains((Integer)4));
		Assert.assertEquals(false, newSet.contains((Integer)10));
		
		// Add all test
		ArrayList<Integer> addAllTest = new ArrayList<>();
		addAllTest.add((Integer)7);
		addAllTest.add((Integer)2);
		
		ArrayList<Integer> addAllTest2 = new ArrayList<>();
		addAllTest2.add((Integer)7);
		addAllTest2.add((Integer)20);
		
		Assert.assertEquals(true, newSet.addAll(addAllTest)); // Doesn't contain either number
		Assert.assertEquals(true, newSet.contains((Integer)7)); // Verify that things are in there
		Assert.assertEquals(true, newSet.contains((Integer)2));
		Assert.assertEquals(true, newSet.addAll(addAllTest2)); // Has one number but not the other
		Assert.assertEquals(true, newSet.contains((Integer)20));
		Assert.assertEquals(false, newSet.addAll(addAllTest)); // Already contains both numbers
		
		// Full containsAll testing
		ArrayList<Integer> containsAllTest1 = new ArrayList<>();
		containsAllTest1.add((Integer)2);
		containsAllTest1.add((Integer)7);
		containsAllTest1.add((Integer)10);
		
		ArrayList<Integer> containsAllTest2 = new ArrayList<>();
		containsAllTest2.add((Integer)10);
		containsAllTest2.add((Integer)15);
		
		Assert.assertEquals(true, newSet.containsAll(addAllTest)); // Does contain all elements
		Assert.assertEquals(false, newSet.containsAll(containsAllTest1)); // Contains some elements but not all
		Assert.assertEquals(false, newSet.containsAll(containsAllTest2)); // Does not contain any elements;
		
		// Resulting array should now be {1,2,3,4,5,6,7,20};
		
		// Check the size of the new array
		Assert.assertEquals(8, newSet.size());
		
		// Convert updated set so an array
		ansArray = newSet.toArray();
		
		// Make sure values are all in line with where they should be
		for(int x = 0; x < newSet.size()-1; x++) {
			// Visual confirmation of values
//			System.out.println(ansArray[x] + " "  + ansArray[x+1]);
			Assert.assertEquals(true, (Integer)ansArray[x] < (Integer)ansArray[x+1]);
		}
		
		// Remove tests
		Assert.assertEquals(false, newSet.remove(10)); // Element is not in the array
		Assert.assertEquals(true, newSet.remove(20)); // Element is in the array
		
		// Resulting array should now be {1,2,3,4,5,6,7};
		Assert.assertEquals(7,newSet.size()); // Verify that size changed
		
		// Verify values remain as {1,2,3,4,5,6,7};
		for(int x = 0; x < newSet.size(); x++) {
			Assert.assertEquals(true, (Integer)ansArray[x] == x+1);
		}
		
		// First and last tests on a bigger array
		Assert.assertEquals((Integer)1, newSet.first());
		Assert.assertEquals((Integer)7, newSet.last());
		
		ArrayList<Integer> removeAllTest1 = new ArrayList<>();
		removeAllTest1.add((Integer)1);
		removeAllTest1.add((Integer)7);
		
		ArrayList<Integer> removeAllTest2 = new ArrayList<>();
		removeAllTest2.add((Integer)5);
		removeAllTest2.add((Integer)10);
		
		
		// Remove all tests
		Assert.assertEquals(true, newSet.removeAll(removeAllTest1)); // All elements present in the array
		Assert.assertEquals(5, newSet.size()); // Size change
		Assert.assertEquals(false, newSet.removeAll(removeAllTest1)); // All elements not present in the array
		Assert.assertEquals(true, newSet.removeAll(removeAllTest2)); // Some elements present in the array
		Assert.assertEquals(4, newSet.size()); // Size change

		// Iterator tests

		BinarySearchSet<Integer> iteratorTest = new BinarySearchSet<>();

		Iterator iterator = iteratorTest.iterator();

		Assert.assertFalse(iterator.hasNext());
		try{
			iterator.next();
		}catch(NoSuchElementException e){
			System.out.println("TEST PASSED - Iterator.next() NoSuchElementException thrown");
		}
		try{
			iterator.remove();
		}catch (IllegalStateException e){
			System.out.println("TEST PASSED - Iterator.remove() IllegalStateException thrown");
		}

		iteratorTest.add(1);
		iteratorTest.add(2);
		iteratorTest.add(3);
		iteratorTest.add(4);
		iteratorTest.add(5);

		Assert.assertTrue(iterator.hasNext());
		Assert.assertEquals(1,iterator.next());
		Assert.assertEquals(2,iterator.next());
		Assert.assertEquals(3,iterator.next());
		iterator.remove();
		Assert.assertFalse(iteratorTest.contains(3));
		Assert.assertEquals(4,iterator.next());
		Assert.assertTrue(iterator.hasNext());
		iterator.remove();
		Assert.assertFalse(iteratorTest.contains(4));
		Assert.assertEquals(5,iterator.next());
		Assert.assertFalse(iterator.hasNext());

		Assert.assertEquals(3,iteratorTest.toArray().length);
		Object[] iteratorTestArray = iteratorTest.toArray();
		Assert.assertTrue(iteratorTestArray[0].equals(1));
		Assert.assertTrue(iteratorTestArray[1].equals(2));
		Assert.assertTrue(iteratorTestArray[2].equals(5));


		System.out.println("Testing complete");
	}
	
}
