package assignment04;

import java.util.ArrayList;
import java.util.Comparator;

import org.junit.Assert;



public class SortUtilTest {
	
	public static <T> void main(String[] args) {
	
		
		/* Test case creation */
		
		// Best case test
		ArrayList<Integer> bestTest = SortUtil.generateBestCase(10);
		
		// Check to make sure all values are in order for the bestCase
		for(int x = 0; x < bestTest.size()-1; x++) {
			Assert.assertEquals(true, bestTest.get(x) < bestTest.get(x+1));
		}
		
		// Worst case test
		// Check to make sure all values are in order for worstCase
		ArrayList<Integer> worstTest = SortUtil.generateWorstCase(10);
		for(int x = 0; x < worstTest.size()-1; x++) {
			Assert.assertEquals(true, worstTest.get(x) > worstTest.get(x+1));
		}
		
		// Random case test
		// Check to make sure all values are present for the avgTest
		ArrayList<Integer> avgTest = SortUtil.generateAverageCase(10);
		ArrayList<Integer> avgTest2 = SortUtil.generateAverageCase(10);
		for(int x = 1; x <= avgTest.size(); x++) {
			// Visual confirmation that it works
//			System.out.println(avgTest.get(x-1) + " " + avgTest2.get(x-1));
			Assert.assertEquals(true, avgTest.contains(x)); // Must contain all the numbers
			Assert.assertEquals(true, avgTest2.contains(x)); // Must contain all the numbers
			Assert.assertEquals(avgTest.get(x-1), avgTest2.get(x-1)); // Arrays must be equal - indicating that we're getting the same "random" sequence every time
		}
		
		// getMediumPivotIndex test
		
		Comparator<String> comparator = new OrderElements<>();
		
		// Desired value is in first position
		String[] names = { "Jim", "Zelda", "Abe"};
		int[] index = {1,4,13};
		
		Assert.assertEquals(1, SortUtil.getMedianPivotIndex(names, index, comparator));
		
		// Desired value is in middle
		String[] names2 = {"Zelda","Link","Alpheus"};
		int[] index2 = {100,20,100};
		
		Assert.assertEquals(20, SortUtil.getMedianPivotIndex(names2, index2, comparator));
		
		// Desired value is at the end
		
		String[] names3 = {"Zelda","Alpheus","Jim"};
		int[] index3 = {100,100,32};
		
		Assert.assertEquals(32, SortUtil.getMedianPivotIndex(names3, index3, comparator));
		
		String[] biggerName = {"Eric","Ashley","Bob","Trent","Matt"};
		int[] biggerIndex = {27,32,41,29,80};
		
		Assert.assertEquals(27, SortUtil.getMedianPivotIndex(biggerName, biggerIndex, comparator));
		
		// quickSort tests
		Comparator<Integer> comparator2 = new OrderElements<>();
		
		// Best test for quicksort
		// Just to make sure it doesn't screw anything up
		
		ArrayList<Integer> bestTest2 = SortUtil.generateBestCase(40);
		
		SortUtil.quicksort(bestTest2, comparator2);
		
		for(int x = 1; x < bestTest2.size(); x++) {
			// Visual confirmation
//			System.out.println(bestTest2.get(x));
			Assert.assertEquals(true, bestTest2.get(x-1) < bestTest2.get(x));
		}
		
		
		// Average test for quicksort
		ArrayList<Integer> avgTest3 = SortUtil.generateAverageCase(40);
		
		SortUtil.quicksort(avgTest3, comparator2); // Sort the array
		
		for(int x = 1; x < avgTest3.size(); x++) {
			// Visual confirmation
//			System.out.println(avgTest3.get(x)); 
			Assert.assertEquals(true, avgTest3.get(x-1) < avgTest3.get(x));
		}
		
		// Worst test for quicksort
		ArrayList<Integer> worstTest1 = SortUtil.generateWorstCase(40);
		
		SortUtil.quicksort(worstTest1,comparator2);
		
		for(int x = 1; x < worstTest1.size(); x++) {
			// Visual confirmation
//			System.out.println(worstTest1.get(x));
			Assert.assertEquals(true,worstTest1.get(x-1) < worstTest1.get(x));
		}
		
		
		
		System.out.println("");
		System.out.println("Testing complete");
	}		
}

class OrderElements<T> implements Comparator<T>{
	@Override
	public int compare(T lhs, T rhs) {
		return ((Comparable<T>) lhs).compareTo(rhs);
	}
}

