package assignment04;

import java.util.ArrayList;
import java.util.Comparator;

public class QuickSortTiming {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("Size\tTime(ns)");
		for (int j = 1; j <= 10; j++) {

            int size = 2000 * j;
            ArrayList<Integer> arr = SortUtil.generateBestCase(size);

            OrderElements c = new OrderElements();

            long startTime, midpointTime, stopTime;

            // First, spin computing stuff until one second has gone by.
            // This allows this thread to stabilize.

            startTime = System.nanoTime();
            while (System.nanoTime() - startTime < 1000000000) { // empty block
            }

            // Now, run the test.

            long timesToLoop = 10000;

            startTime = System.nanoTime();

            for (long i = 0; i < timesToLoop; i++) { // block to subtract
                ArrayList<Integer> temp = (ArrayList<Integer>) arr.clone();

                SortUtil.quicksort(temp, c);
            }

            midpointTime = System.nanoTime();

            // Run an empty loop to capture the cost of running the loop.

            for (long i = 0; i < timesToLoop; i++) {
                ArrayList<Integer> temp = (ArrayList<Integer>) arr.clone();
            }

            stopTime = System.nanoTime();

            // Compute the time, subtract the cost of running the loop
            // from the cost of running the loop and computing square roots.
            // Average it over the number of runs.

            double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime)) / timesToLoop;

            System.out.println(arr.size() + "\t" + averageTime);
//            System.out.println("It takes exactly " + averageTime + " nanoseconds to execute the mergesort() method for an ArrayList of size " + arr.size() + ".");
        }

		
		
		
	}

}

//class OrderElements<T> implements Comparator<T>{
//	@Override
//	public int compare(T lhs, T rhs) {
//		return ((Comparable<T>) lhs).compareTo(rhs);
//	}
//}
