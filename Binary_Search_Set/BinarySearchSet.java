package lab05;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class BinarySearchSet<E> implements SortedSet<E>, Iterable<E> {

	private E[] baseArray;
	private int size;
	private int capacity;
	private Comparator<? super E> thisComparator;

	/**
	 * Basic constructor for BinarySearchSet without a comparator
	 * Sets size value as 0, the base array as an empty array, and the capcity at 0
	 * comaprator is set to null
	 */
	@SuppressWarnings("unchecked")
	public BinarySearchSet() {
		this.baseArray = (E[]) new Object[5];
		this.size = 0;
		this.capacity = 5;
		this.thisComparator = null;
	}
	
	/**
	 * Basic constructor for BinarySearchSet without a comparator
	 * Sets size value as 0, the base array as an empty array, and the capcity at 0
	 * comaprator is set to the input
	 */
	public BinarySearchSet(Comparator <? super E> comparator) {
		this.baseArray = (E[]) new Object[5];
		this.size = 0;
		this.capacity = 5;
		this.thisComparator = comparator;
	}
	
	/**
	 * @return The comparator used to order the elements in this set, or null if
	 *         this set uses the natural ordering of its elements (i.e., uses
	 *         Comparable).
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Comparator<? super E> comparator() {
		return this.thisComparator;
	}

	/**
	 * @return the first (lowest, smallest) element currently in this set
	 * @throws NoSuchElementException if the set is empty
	 */
	@Override
	public E first() throws NoSuchElementException {
		if(this.size == 0) {
			throw new NoSuchElementException();
		}
		return this.baseArray[0];
	}

	/**
	 * @return the last (highest, largest) element currently in this set
	 * @throws NoSuchElementException if the set is empty
	 */
	@Override
	public E last() throws NoSuchElementException {
		if(this.size == 0) {
			throw new NoSuchElementException();
		}
		return this.baseArray[this.size-1];
	}

	/**
	 * Adds the specified element to this set if it is not already present and
	 * not set to null.
	 * 
	 * @param element element to be added to this set
	 * @return true if this set did not already contain the specified element
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean add(E element) {
		// First value
		if(this.size == 0) {
			baseArray[0] = element;
		
		// Repeated values
		}else if(contains(element)) { //
			return false;
			
		// Adding any other values
		}else {
			
			// If the array needs to get bigger
			if(this.size == this.capacity) {
				growArray();
			}
			
			// Find the insertion point
			int addIndex = binarySearch(this.baseArray,element);
			
			// If the insertion point is at the far end of the array
			if(addIndex == this.size-1) { 
				// If the value to insert is less than the final value
				if(compareObjects(element,this.baseArray[this.size-1]) < 0) {
					E temp = this.baseArray[this.size-1];
					this.baseArray[this.size-1] = element;
					this.baseArray[size] = temp;
				}else{ // If the value of the insert is more than the final value
					this.baseArray[size] = element;
				}
			
			// If the insertion point is not at the far end
			}else{
				
				// Create new array and copy things in 
				E[] temp = (E[]) new Object[capacity];
				for(int x = 0; x <= this.size; x++) {
					if(x < addIndex) { // Old elements
						temp[x] = this.baseArray[x];
					}else if(x == addIndex) { // New element
						temp[x] = element;
					}else { // Old elements in place + 1
						temp[x] = this.baseArray[x-1];
					}
				}
				this.baseArray = temp;
			}
		}
		this.size++;	
		return true;
	}

	/**
	 * Adds all of the elements in the specified collection to this set if they
	 * are not already present and not set to null.
	 * 
	 * @param elements -  collection containing elements to be added to this set
	 * @return true if this set changed as a result of the call
	 */
	@Override
	public boolean addAll(Collection<? extends E> elements) {
		Iterator<E> addIterate = (Iterator<E>) elements.iterator();
		boolean collectionChanged = false;
		while(addIterate.hasNext()) {
			if(add(addIterate.next())) {
				collectionChanged = true;
			}
		}
		return collectionChanged;
	}
	
	/**
	 * growArray doubles the capacity of the current array with double the capacity
	 * it then sets the copy with double capacity to the baseArray member variable
	 * @param - None
	 * @return - None
	 */
	private void growArray() {
		this.capacity *= 2; // Double capacity
		E[] temp = (E[]) new Object[capacity];
		for(int x = 0; x < this.size; x++) {
			temp[x] = this.baseArray[x];
		}
		this.baseArray = temp;
	}

	/**
	 * Removes all of the elements from this set. The set will be empty after
	 * this call returns.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void clear() {
		this.baseArray = (E[]) new Object[5];
		this.size = 0;
		this.capacity = 5;
	}

	/**
	 * @param element element whose presence in this set is to be tested
	 * @return true if this set contains the specified element
	 */
	@Override
	public boolean contains(Object element) {
		if(binarySearch(this.baseArray,(E)element) == -1) {
			return true;
		}
		return false;
	}

	/**
	 * @param elements collection to be checked for containment in this set
	 * @return true if this set contains all of the elements of the specified
	 *         collection
	 */
	@Override
	public boolean containsAll(Collection<?> elements) {
		Iterator<E> containsIterate = (Iterator<E>) elements.iterator();
		boolean containsAll = true;
		while(containsIterate.hasNext()) {
			if(!contains(containsIterate.next())) {
				containsAll = false;
			}
		}
		
		return containsAll;
	}

	/**
	 * @return true if this set contains no elements
	 */
	@Override
	public boolean isEmpty() {
		return this.size == 0;
	}

	/**
	 * @return an iterator over the elements in this set, where the elements are
	 *         returned in sorted (ascending) order
	 */
	@Override
	public Iterator<E> iterator() {
		return new iteratorClass();
	}

	/**
	 * Removes the specified element from this set if it is present.
	 * 
	 * @param element object to be removed from this set, if present
	 * @return true if this set contained the specified element
	 */
	@Override
	public boolean remove(Object element) {
		if(!contains(element)) {
			return false;
		}
		boolean readyToRemove = false;
		for(int x = 0; x < baseArray.length -1; x++) {
			if(baseArray[x] == element) {
				readyToRemove = true;
			}
			if(readyToRemove) {
				baseArray[x] = baseArray[x+1];
			}else {
				baseArray[x] = baseArray[x];
			}
		}
		baseArray[baseArray.length-1] = null;
		size--;
		return true;
	}

	/**
	 * Removes from this set all of its elements that are contained in the
	 * specified collection.
	 * 
	 * @param elements collection containing elements to be removed from this set
	 * @return true if this set changed as a result of the call
	 */
	@Override
	public boolean removeAll(Collection<?> elements) {
		Iterator<E> removeIterate = (Iterator<E>) elements.iterator();
		boolean itemRemoved = false;
		while(removeIterate.hasNext()) {
			if(remove(removeIterate.next())) {
				itemRemoved = true;
			}
		}
		return itemRemoved;
	}

	/**
	 * @return the number of elements in this set
	 */
	@Override
	public int size() {
		return this.size;
	}

	/**
	 * @return an array containing all of the elements in this set, in sorted
	 *         (ascending) order.
	 */
	@Override
	public Object[] toArray() {
		Object[] outArray = new Object[size];
		int idx = 0;
		for(int x = 0; x < outArray.length; x++){
			if(baseArray[x] != null){
				outArray[idx] = baseArray[x];
				idx++;
			}
		}
		return outArray;
	}
	
	/**
	 * binarySearch performs a binary search on the whole array and returns either the value where the goal item should be or -1 if the item is already in the array
	 * @param arr - array of generic E objects
	 * @param goal - generic E object to find in the array
	 * @return - -1 if the value is already in the array OR the index where it should reside if it is not in the array
	 */
	public int binarySearch(E[] arr, E goal) { // Modeled after the binarySearch from lab04
	    int left = 0; 
	    // Actual functionality
	    int right = this.size - 1;
	    // Used for assertEquals on testArrays
//	    int right = arr.length -1;
	    int middlePos = 0;
	    while (left <= right) {
	      middlePos = (left + right) / 2;
	      if (compareObjects((E)arr[middlePos],goal) == 0) {
	    	  // -1 indicates that the element is in the array
	        return -1;
	      } else if (compareObjects((E)arr[middlePos],goal) > 0) {
	        right = middlePos - 1;
	      } else {
	        left = middlePos + 1;
	      }
	    }
	    // any other number (must be positive) will be the index of where the item should be
	    // Used for assertEquals on testArrays
//	    return middlePos; 
	    // Used for actual functionality
	    return left;
	}
	
	/**
	 * compareObjects serves to 1) check if the BinarySearchSet has a member comparator
	 * 2) if it does not, check to see if the items are comparable
	 * 3) compare the items using the compareTo method inherent in the comparable interface
	 * @param lhs of our generic type E
	 * @param rhs of our generic type E
	 * @return the standard compare/compareTo value of an int
	 */
	@SuppressWarnings({ "unchecked" })
	private int compareObjects(E lhs, E rhs) {
		if(this.thisComparator == null) {
			if(lhs instanceof Comparable && rhs instanceof Comparable) {
				return ((Comparable) lhs).compareTo(rhs);
			}
		}
		return thisComparator.compare(lhs, rhs);
	}
	
	/**
	 * @author trentevans
	 * Separate iterator class
	 */
	public class iteratorClass implements Iterator<E>{
		private int position = 0;
		private boolean canRemove = false;

		/**
		 * hasNext checks to see if there is another value inside the array
		 */
		@Override
		public boolean hasNext() {
			if(position < size) {
				return true;
			}
			return false;
		}

		/**
		 * next returns the current value of the baseArray and prepare to return the next value the next time it is called
		 * @throws NoSuchElementException if there are no more elements in the array
		 */
		@Override
		public E next() {
			if(this.hasNext()) {
				canRemove = true;
				return baseArray[position++];
				
			}else {
				System.err.println("Array size exceeded - No Such Element");
				throw new NoSuchElementException();
			}
		}
		
		/**
		 * remove calls the BinarySearchSet remove method for the indicated value
		 * @throws IllegalStateException if next has not been called already, or again after remove has been called
		 */
		@Override
		public void remove() {
			if(canRemove) {
				BinarySearchSet.this.remove(baseArray[position-1]);
				position--;
				canRemove = false;
			}else{
				System.err.println("Cannot remove without calling .next() again");
				throw new IllegalStateException();
			}
		}
		
		
	}

	

}

