package assignment06;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class ChainingHashTable implements Set<String>{

    private LinkedList<String>[] storage;
    private HashFunctor functor;
    private int capacity;
    private int size;
    private int collisions;

    public ChainingHashTable(int capacity, HashFunctor functor){
        storage = (LinkedList<String>[]) new LinkedList[capacity];
        size = 0;
        this.functor = functor;
        this.capacity = capacity;
        collisions = 0;
    }

    /**
     * Ensures that this set contains the specified item.
     *
     * @param item - the item whose presence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if
     * the input item was actually inserted); otherwise, returns false
     */
    @Override
    public boolean add(String item) {
        int hashIdx = functor.hash(item)%capacity;
        if(storage[hashIdx] == null){ // If the LinkedList doesn't exist yet
            LinkedList<String> newList = new LinkedList<>();
            newList.add(item);
            storage[hashIdx] = newList;
            size++;
            return true;
        }else if(storage[hashIdx].contains(item)) { // If the value is already in there
            return false;
        }else{ // Otherwise add it
            storage[hashIdx].add(item);
            size++;
            collisions++;
            return true;
        }
    }

    /**
     * Ensures that this set contains all items in the specified collection.
     *
     * @param items - the collection of items whose presence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if
     * any item in the input collection was actually inserted); otherwise,
     * returns false
     */
    @Override
    public boolean addAll(Collection<? extends String> items) {
        boolean added = false;
        Object[] itemList =  items.toArray();
        for(int x = 0; x < itemList.length; x++){
            if(add((String) itemList[x])) {
                added = true;
            }
        }
        return added;
    }

    /**
     * Removes all items from this set. The set will be empty after this method
     * call.
     */
    @Override
    public void clear() {
        storage = (LinkedList<String>[]) new LinkedList[capacity];
        size = 0;
    }

    /**
     * Determines if there is an item in this set that is equal to the specified
     * item.
     *
     * @param item - the item sought in this set
     * @return true if there is an item in this set that is equal to the input item;
     * otherwise, returns false
     */
    @Override
    public boolean contains(String item) {
        int hashIdx = functor.hash(item)%capacity;
        if(storage[hashIdx] == null){ // If the LinkedList doesn't exist yet
            return false;
        }else if(!storage[hashIdx].contains(item)) { // If the value is already in there
            return false;
        }else{ // Otherwise add it
            return true;
        }
    }

    /**
     * Determines if for each item in the specified collection, there is an item in
     * this set that is equal to it.
     *
     * @param items - the collection of items sought in this set
     * @return true if for each item in the specified collection, there is an item
     * in this set that is equal to it; otherwise, returns false
     */
    @Override
    public boolean containsAll(Collection<? extends String> items) {
        Object[] itemList = items.toArray();
        for(int x = 0; x < itemList.length; x++){
            if(!contains((String) itemList[x])){ // If a single value isn't present, returns false
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if this set contains no items.
     */
    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Ensures that this set does not contain the specified item.
     *
     * @param item - the item whose absence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if
     * the input item was actually removed); otherwise, returns false
     */
    @Override
    public boolean remove(String item) {
        int hashIdx = functor.hash(item)%capacity;
        if(storage[hashIdx] == null){ // If the LinkedList doesn't exist yet
            return false;
        }else if(!storage[hashIdx].contains(item)) { // If the value is not in a list already
            return false;
        }else{ // Otherwise remove it
            storage[hashIdx].remove(item);
            size--;
            if(storage[hashIdx].isEmpty()){ // Set the array to null if there are no values after the remove
                storage[hashIdx] = null;
            }
            return true;
        }
    }

    /**
     * Ensures that this set does not contain any of the items in the specified
     * collection.
     *
     * @param items - the collection of items whose absence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if
     * any item in the input collection was actually removed); otherwise,
     * returns false
     */
    @Override
    public boolean removeAll(Collection<? extends String> items) {
        boolean anyRemoved = false;
        Object[] itemList = items.toArray();
        for(int x = 0; x < itemList.length; x++){
            if(remove((String) itemList[x])){
                anyRemoved = true;
            }
        }
        return anyRemoved;
    }

    /**
     * Returns the number of items in this set.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the number of collisions that have occurred during the run
     */
    public int collisions(){
        return collisions;
    }
}
