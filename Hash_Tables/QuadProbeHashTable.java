package assignment06;

import java.util.Collection;

public class QuadProbeHashTable implements Set<String> {

    private String[] storage;
    private boolean[] isActive;
    private HashFunctor functor;
    private int size;
    private int capacity;
    private int collisions;

    /**
     * Basic constructor for the QuadHashTable
     * This constructor also contains a control of sorts to prevent entering a capacity that isn't prime
     * @param capacity - The capacity of the base array (should be prime, but the program will adjust if it isn't)
     * @param functor - The Hash Functor used for calculating hash values
     */
    QuadProbeHashTable(int capacity, HashFunctor functor){
        this.functor = functor;
        if(isPrime(capacity)){
            this.capacity = capacity;
        }else{
            this.capacity = generateNextPrimeCapacity(capacity);
        }
        storage = new String[this.capacity];
        isActive = new boolean[this.capacity];

        size = 0;
        collisions = 0;
    }

    // The idea for isPrime and generateNextPrimeCapacity came from this website
    // https://examples.javacodegeeks.com/java-basics/for-loop/generate-prime-numbers-with-for-loop/
    /**
     * isPrime tests to see if a value is prime or not by modulo dividing by all values from 2 to the square root of the value
     * @param value - The value to be tested to see if it is prime
     * @return - Boolean true if the value is prime, false if not
     */
    public boolean isPrime(int value){
        for(int x = 2; x <= (int)Math.sqrt(value); x++){
            if(value % x == 0){
                return false;
            }
        }
        return true;
    }

    /**
     * generateNextPrimeCapacity takes the old capacity of the Array and doubles it.  It then increments the capacity
     * until it find a prime number and then returns that prime number
     * @param capacity - current capacity to be set to the next prime
     * @return - The new prime capacity (which should be a prime number)
     */
    public int generateNextPrimeCapacity(int capacity){
        capacity *= 2; // Double the capacity of the array
        while(!isPrime(capacity)){
            capacity++;
        }
        return capacity;
    }

    /**
     * resizeAndRehash increases the size of the array to the next prime number after capacity * 2 and then adds all
     * old values that haven't been deleted into the array
     */
    public void resizeAndRehash(){
        int newCapacity = generateNextPrimeCapacity(capacity);
        this.capacity = newCapacity; // Reset the capacity
        this.size = 0; // Reset the size, because the add method below will increment the size as things are added

        // Store old values so we can access them for rehashing
        String[] oldStorage = storage;
        boolean[] oldActive = isActive;

        // Reset current member variables as new arrays
        storage = new String[this.capacity];
        isActive = new boolean[this.capacity];

        // Add old values into the new storage array (isActive is taken care of in the add method)
        for(int x = 0; x < oldStorage.length; x++){
            if(oldStorage[x] != null && oldActive[x]){ // Only copy values that haven't been lazy-deleted
                add(oldStorage[x]);
            }
        }
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
        int hashIdx = functor.hash(item) % capacity;

        if(contains(item)){ // If the value is already there
            return false;
        }else{ // Otherwise
            // If the index is empty -OR- if the index is full but the value is not active
            if(storage[hashIdx] == null || (storage[hashIdx] != null && !isActive[hashIdx])){
                storage[hashIdx] = item;
                isActive[hashIdx] = true;
                size++;
            }else{ // Quadratically probe until you find an empty index
                int count = 1;
                int tryIdx = (int) ((hashIdx + Math.pow(count,2)) % capacity); // Mod by the capacity in case it goes over the end of the array
                // These two values should never be equal, but it maximizes the number of possible iterations for the storage
                while(tryIdx != hashIdx){
                    // If the index is empty -OR- if the index is full but the value is not active
                    if(storage[tryIdx] == null || (storage[tryIdx] != null && !isActive[tryIdx])){
                        break;
                    }
                    // Increment the count and adjust the try index
                    count++;
                    tryIdx = (int) ((hashIdx + Math.pow(count,2)) % capacity);
                    collisions++;
                }
                storage[tryIdx] = item;
                isActive[tryIdx] = true;
                size++;
            }
        }
        double lambda = (double)size/capacity; // Recalculate lambda
        if(lambda > 0.5){ // If lambda exceeds the threshold
            resizeAndRehash();
        }
        return true;
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
        Object[] itemList = items.toArray();
        for(int x = 0; x < itemList.length; x++){
            if(add((String) itemList[x])){
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
        storage = new String[capacity];
        isActive = new boolean[capacity];
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
        int hashIdx = (functor.hash(item) % capacity);
        if(storage[hashIdx] == null){ // Stop at the hashIdx if it's null, because it can't be anywhere else if that's the case
            return false;
        }else if(storage[hashIdx].equals(item) && isActive[hashIdx]){
            return true;
        }else{
            int count = 1;
            int tryIdx = (int) ((hashIdx + Math.pow(count,2)) % capacity);
            while(tryIdx != hashIdx){
                if(storage[tryIdx] == null){ // Break on null values
                    return false;
                } else if(storage[tryIdx].equals(item) && isActive[tryIdx]){ // If the values are the same and it is active
                    return true;
                }
                count++;
                tryIdx = (int) ((hashIdx + Math.pow(count,2)) % capacity);
            }
        }
        return false;
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
        boolean elementNotMissing = true;
        Object[] itemList = items.toArray();
        for(int x = 0; x < itemList.length; x++){
            if(!contains((String)itemList[x])){
                elementNotMissing = false;
            }
        }
        return elementNotMissing;
    }

    /**
     * valueNotActive is a method mostly used for testing purposes
     * It returns true if a value is present in the array but it has been lazy deleted and thus has an inactive status
     * Mostly I'm using this to make sure that when a value is removed it remains inside the array, but is set to inactive
     * It's something of a companion method to
     * @param item - The String to search for
     * @return - true if the item is present and has an inactive status and false otherwise
     */
    public boolean valueNotActive(String item) {
        int hashIdx = functor.hash(item) % capacity;
        if (contains(item)) {
            return false;
        } else if (storage[hashIdx] != null && storage[hashIdx].equals(item)) {
            if (isActive[hashIdx]) {
                return false;
            }
            return true;
        } else {
            int count = 1;
            int tryIdx = (int) ((hashIdx + Math.pow(count, 2)) % capacity);
            while (tryIdx != hashIdx) {
                if(storage[tryIdx] == null){ // Break on null values
                    return false;
                } else if (storage[tryIdx].equals(item)) { // If the values are the same
                    if (isActive[tryIdx]) { // If the value is active
                        return false;
                    } else { // If the value is not active
                        return true;
                    }
                }
                count++;
                tryIdx = (int) ((hashIdx + Math.pow(count, 2)) % capacity);
            }
        }
        return false;
    }

    /**
     * Returns true if this set contains no items.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
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
        int hashIdx = (functor.hash(item) % capacity);
        if(!contains(item)){ // If the set doesn't contain the element, we can't remove it
            return false;
        }else if(storage[hashIdx].equals(item)){ // If it happens to be at the calculated hash index
            isActive[hashIdx] = false;
            size--;
            return true;
        }else{
            int count = 1;
            int tryIdx = (int) ((hashIdx + Math.pow(count,2)) % capacity);
            while(tryIdx != hashIdx){
                if(storage[tryIdx] == null){ // Break on null values
                    return false;
                } else if(storage[tryIdx].equals(item) && isActive[tryIdx]){ // If the index is full but the value is not active
                    break;
                }
                // Increment the count and adjust the try index
                count++;
                tryIdx = (int) ((hashIdx + Math.pow(count,2)) % capacity);
            }
            isActive[tryIdx] = false;
            size--;
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
        boolean removedAny = false;
        Object[] itemList = items.toArray();
        for(int x = 0; x < itemList.length; x++){
            if(remove((String) itemList[x])){
                removedAny = true;
            }
        }
        return removedAny;
    }

    /**
     * Returns the number of items in this set.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the capacity of the set.
     */
    public int capacity(){
        return capacity;
    }

    /**
     * Returns the number of collisions that have happened in the set.  I don't need it for any testing, but it could be fun
     */
    public int collisions(){
        return collisions;
    }

}
