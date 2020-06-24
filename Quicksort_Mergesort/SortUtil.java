package assignment04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SortUtil<T> {
	
	public static <T> void mergesort(ArrayList<T> array, Comparator <? super T> comparator) {
		
	}
	
	
	/**
	 * quicksort is the driver method for calling recursive quickSortRecursive (which is a private method of SortUtil
	 * @param <T> Type of ArrayList to be sorted
	 * @param array - ArrayList to be sorted
	 * @param comparator - comparator for comparing items
	 */
	public static <T> void quicksort(ArrayList<T> array, Comparator <? super T> comparator) {
		
		// Pivot indicator can be a value of 1, 2, or anything else
		// 1 = pick the first element - worst case
		// 2 = pick a random element - better case
		// another other value (ideally 3) = pick n random elements and find the median as the pivot
		// => if an even number of elements is selected, quickSortRecursive will subtract 1 from the number of elements
		int pivotNumber = 2;
		
		quickSortRecursive(array,0,(array.size()-1),comparator,pivotNumber);
	}
	
	/**
	 * quickSortRecursive performs a quicksort on the region indicated by left and right
	 * when quicksort is done swapping, quicksort will be called again on both the lower partition and the upper partition of the array
	 * @param <T> Type of ArrayList to be sorted
	 * @param array - ArrayList to be sorted
	 * @param left - leftmost bound of the partition to be sorted
	 * @param right - rightmost bound of the partition to be sorted
	 * @param comparator - comparator used to compare elements
	 * @param pivotIndicator - indicates what type of pivot will be chosen
	 */
	private static <T> void quickSortRecursive(ArrayList<T> array, int left, int right, Comparator <? super T> comparator, int pivotNumber) {
		
		if(left >= right) {
			// Cease recursing
		}else {
			int pivotIdx = 0;
			T pivot = null;
			if(pivotNumber == 1) { // Select the first element
				pivot = array.get(pivotIdx);
			}else if(pivotNumber == 2){ // Select a random element
				pivotIdx = left + (int)(Math.random()*(right-left-1));
				pivot = array.get(pivotIdx);
			}else { // Median of n random elements
			
				// Adjust the pivotNumber if there's an attempt to make it even
				if(pivotNumber%2 == 0) {
					pivotNumber--;
				}
	
				// Pull out the indices and corresponding values
				int[] indexList = new int[pivotNumber];
				T[] valueList = (T[]) new Object[pivotNumber];
				for(int x = 0; x < pivotNumber; x++) {
					indexList[x] = left + (int)(Math.random()*(right-left-1));
					valueList[x] = array.get(indexList[x]);
				}
				pivotIdx = getMedianPivotIndex(valueList,indexList,comparator);
				pivot = array.get(pivotIdx);
			}			

			// Swap out the pivot
			Collections.swap(array, pivotIdx, right);
			
			int L = left;
			int R = right-1;
			
			while(L <= R) {
				if(comparator.compare(array.get(L), pivot) < 0) {
					L++; continue;
				}
				if(comparator.compare(array.get(R), pivot) > 0) {
					R--; continue;
				}
				Collections.swap(array, L, R);
				L++; R--;
			}
			
			// Swap the pivot back in
			Collections.swap(array, L, right);
			
			// Sort the new partitions
			quickSortRecursive(array,left,L-1,comparator,pivotNumber); // Left partition
			quickSortRecursive(array,L+1,right,comparator,pivotNumber); // Right partition
		}
	}
	
	
	/**
	 * getMedianPivotIndex performs insertion sort on two arrays, according to the natural ordering of the first array
	 * this allows us to keep both the index and value of the array together when sorting to make sure that the correct value is returned
	 * 
	 * @param <T> - Type of the values passed in
	 * @param valueArray - Array of possible pivot values -- should always be an odd number of values
	 * @param indexArray - Indices of corresponding pivot values -- should always be the same number of values as valueArray
	 * @param comparator - comparator for related type of values
	 * @return - index of the median pivot value
	 */
	public static <T> int getMedianPivotIndex(T[] valueArray, int[] indexArray, Comparator <? super T> comparator) {
		// Should only ever take in odd numbers of elements
		for(int x = 1; x < valueArray.length; x++) {
			T val = (T)valueArray[x];
			int idx = indexArray[x];
			int y = x;
			while(y>0 && comparator.compare(valueArray[y-1],val)>0) {
				valueArray[y] = valueArray[y-1];
				indexArray[y] = indexArray[y-1];
				y--;
			}
			valueArray[y] = val;
			indexArray[y] = idx;
		}
		return indexArray[(valueArray.length-1)/2]; // Return the middle index
	}
	
	/**
	 * Generates an ArrayList of int values from 1 to size in ascending order
	 * @param size - integer denoting the number of elements as well as the maximum element of the array
	 * @return ArrayList with values from 1 to size in ascending order
	 */
	public static ArrayList<Integer> generateBestCase(int size){
		ArrayList<Integer> bestCase = new ArrayList<>();
		for(int x = 1; x <= size; x++) {
			bestCase.add((Integer)x);
		}
		return bestCase;
	}
	
	/**
	 * Generates an ArrayList of int values from 1 to size in a pseudorandom order
	 * @param size - integer denoting the number of elements as well as the maximum element of the array
	 * @return ArrayList with values from 1 to size, though in a random order
	 */
	public static ArrayList<Integer> generateAverageCase(int size){
		ArrayList<Integer> avgCase = generateBestCase(size);
		Random ran = new Random();
		ran.setSeed(123); // Standard seed
		for(int x = 0; x < size; x++) {
			int swapIndex = ran.nextInt(size-1); // Conveniently, the swap index is also the value
			// Swap the two values 
			Collections.swap(avgCase, x, swapIndex);
		}
		return avgCase;
	}
	
	/**
	 * Generates an ArrayList of int values from 1 to size in descending order
	 * @param size - integer denoting the number of elements as well as the maximum element of the array
	 * @return ArrayList with values from 1 to size in descending order
	 */
	public static ArrayList<Integer> generateWorstCase(int size){
		ArrayList<Integer> worstCase = new ArrayList<>();
		for(int x = size; x >= 1; x--) {
			worstCase.add((Integer)x);
		}
		return worstCase;
	}
	
}
