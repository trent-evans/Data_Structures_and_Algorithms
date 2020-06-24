package assignment05;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

public class BinarySearchTree<T extends Comparable<? super T>> implements SortedSet<T> {

    private BinaryNode rootNode;
    private int size;

    /**
     * Default constructor
     * Sets initial rootNode to null
     * Sets initial size = 0
     */
    BinarySearchTree(){
        rootNode = null;
        size = 0;
    }

    /**
     * root returns the value of the root node of the Binary Search Tree
     * Not necessary to implement, but I thought it could be helpful for testing purposes
     * @return the value of the root node
     */
    public T root(){
        if(rootNode == null){
            System.err.println("Cannot get rootNode of an empty Binary Search Tree");
            throw new NullPointerException();
        }
        return (T) rootNode.data;
    }

    /**
     * Ensures that this set contains the specified item.
     *
     * @param item - the item whose presence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if
     * the input item was actually inserted); otherwise, returns false
     * @throws NullPointerException if the item is null
     */
    @Override
    public boolean add(T item) {
        if(item == null){
            System.err.println("Cannot insert a null item");
            throw new NullPointerException();
        }else if(size == 0){
            BinaryNode newNode = new BinaryNode();
            newNode.data = item;
            rootNode = newNode;
            size = 1;
            return true;
        }else{
            return addRecursive(item,rootNode);
        }
    }


    /**
     * addRecursive is a recursive method that allows us to traverse the Binary Search Tree and find the location where
     * an item should be added.  It is called in the add driver method if an item is not null and not the root
     *
     * @param item - item to be added to the Binary Search Tree
     * @param current - the current node of the Binary Search Tree
     * @return a recursive call if the current node has a left/right node to visit
     *         false if the item is already inside the Binary Search Tree
     *         true if the item is not in the Binary Search Tree and has been added
     */
    private boolean addRecursive(T item, BinaryNode<T> current){
        if(current.data.equals(item)) { // If the values are the same, return false because we don't want to duplicate items
            return false;
        }else if(item.compareTo(current.data) < 0){ // If the value is less than the current node
            // If the node does not exist, create it, set it, increment size, and return true
            if(current.left == null){
                BinaryNode newNode = new BinaryNode();
                newNode.data = item;
                current.left = newNode;
                size++;
                return true;
            }
            // Otherwise, traverse further to the left
            return addRecursive(item,current.left);

        }else{ // Otherwise the value must be greater
            // If the node does not exist, create it, set it, increment size, and return true
            if(current.right == null){
                BinaryNode newNode = new BinaryNode();
                newNode.data = item;
                current.right = newNode;
                size++;
                return true;
            }
            return addRecursive(item,current.right);
        }
    }

    /**
     * Ensures that this set contains all items in the specified collection.
     *
     * @param items - the collection of items whose presence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if
     * any item in the input collection was actually inserted); otherwise,
     * returns false
     * @throws NullPointerException if any of the items is null
     */
    @Override
    public boolean addAll(Collection<? extends T> items) {
        Object[] itemList = items.toArray();
        boolean itemAdded = false;

        // Check for null values first
        for(int y = 0; y < itemList.length; y++){
            if(itemList[y] == null){
                System.err.println("Cannot add null to the Binary Search Tree");
                throw new NullPointerException();
            }
        }

        // Add items
        for(int x = 0; x < itemList.length; x++){
            if(add((T) itemList[x])){
                itemAdded = true;
            }
        }
        return itemAdded;
    }

    /**
     * Removes all items from this set. The set will be empty after this method
     * call.
     */
    @Override
    public void clear() {
        rootNode = null;
        size = 0;
    }

    /**
     * Determines if there is an item in this set that is equal to the specified
     * item.
     *
     * @param item - the item sought in this set
     * @return true if there is an item in this set that is equal to the input item;
     * otherwise, returns false
     * @throws NullPointerException if the item is null
     */
    @Override
    public boolean contains(T item) {
        if(this.size == 0){
            return false;
        }
        if(item == null){
            System.err.println("Binary Search Tree cannot contain a null value");
            throw new NullPointerException();
        }
        BinaryNode<T> current = rootNode;
        while(!current.data.equals(item)){ // As long as we haven't found the data
            if(current.left == null && current.right == null){ // If we're at a leaf and inside the loop, the data cannot be inside the BST
                return false;
            }else if(item.compareTo(current.data) < 0){ // If the value is less than
                if(current.left == null){ // If it's less then and we can't go further to the left, it's not here
                    return false;
                }
                current = current.left;
            }else if(item.compareTo(current.data) > 0){ // If the value is greater than
                if(current.right == null){ // If it's greater than and we can't go further right, it's not here
                    return false;
                }
                current = current.right;
            }
        }
        // It's time to leaf :)
        return true;
    }

    /**
     * Determines if for each item in the specified collection, there is an item in
     * this set that is equal to it.
     *
     * @param items - the collection of items sought in this set
     * @return true if for each item in the specified collection, there is an item
     * in this set that is equal to it; otherwise, returns false
     * @throws NullPointerException if any of the items is null
     */
    @Override
    public boolean containsAll(Collection<? extends T> items) {
        Object[] itemArray = items.toArray();
        boolean containsAllItems = true;
        for(Object item: itemArray){
            if(item == null){
                System.err.println("Binary Search Tree cannot contain a null value");
                throw new NullPointerException();
            }
        }
        for(Object item: itemArray){
            if(!contains((T) item)){
                containsAllItems = false;
            }
        }
        return containsAllItems;
    }

    /**
     * Returns the first (i.e., smallest) item in this set.
     *
     * @throws NoSuchElementException if the set is empty
     */
    @Override
    public T first() throws NoSuchElementException {
        if(size == 0){
            System.err.println("Cannot access first element in an empty Binary Search Tree");
            throw new NoSuchElementException();
        }
        BinaryNode current = rootNode;
        while(current.left != null){
            current = current.left;
        }
        return (T) current.data;
    }

    /**
     * Returns true if this set contains no items.
     */
    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Returns the last (i.e., largest) item in this set.
     *
     * @throws NoSuchElementException if the set is empty
     */
    @Override
    public T last() throws NoSuchElementException {
        if(size == 0){
            System.err.println("Cannot access last element in an empty Binary Search Tree");
            throw new NoSuchElementException();
        }
        BinaryNode current = rootNode;
        while(current.right != null){
            current = current.right;
        }
        return (T) current.data;
    }

    /**
     * Ensures that this set does not contain the specified item.
     *
     * @param item - the item whose absence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if
     * the input item was actually removed); otherwise, returns false
     * @throws NullPointerException if the item is null
     */
    @Override
    public boolean remove(T item) {
        if(this.size == 0){
            return false;
        }
        if(item == null){
            System.err.println("Cannot remove a null item");
            throw new NullPointerException();
        }

        if(!contains(item)){
            return false;
        }

        BinaryNode<T> parentNode;
        BinaryNode<T> removeNode;

        if(rootNode.data.equals(item)){
            parentNode = rootNode;
            removeNode = rootNode;
        }else {
            parentNode = removeNodeParentFinder(rootNode, item);
            if(parentNode.left.data == item){
                removeNode = parentNode.left;
            }else{
                removeNode = parentNode.right;
            }
        }

        if((removeNode.left == null && removeNode.right == null)){ // Case 1 - at a leaf
            return leafRemove(parentNode,item);
        }else if(removeNode.left != null && removeNode.right != null){ // Case 3 - Node w/ two children/subtrees
            return doubleBranchRemove(parentNode,item);
        }else { // Case 2 - Node w/ one child/subtree => either the left or right are null, but not both
            return singleBranchRemove(parentNode,item);
        }
    }

    /**
     * removeNodeParentFinder is a recursive method that traverses the Binary Search Tree in order to find the parent of
     * the node to be removed
     * @param currentNode - node that we're currently on
     * @param item - the item we're looking for
     * @return - the node we're working on who is a parent of the node containing the value
     */
    private BinaryNode<T> removeNodeParentFinder(BinaryNode<T> currentNode, T item){
        if(currentNode.left.data.equals(item) || currentNode.right.data.equals(item)){
            return currentNode;
        }else if(item.compareTo(currentNode.data) < 0){
            currentNode = currentNode.left;
            return removeNodeParentFinder(currentNode,item);
        }else{
            currentNode = currentNode.right;
            return removeNodeParentFinder(currentNode,item);
        }
    }

    /**
     * leafRemove removes the indicated leaf node from the Binary Search Tree
     * @param parentNode - the parent node of the node to be removed
     * @param item - value to be removed
     * @return
     */
    private boolean leafRemove(BinaryNode<T> parentNode, T item){
        if(parentNode.left.data.equals(item)){
            parentNode.left = null;
        }else{
            parentNode.right = null;
        }
        size--;
        return true;
    }

    /**
     * singleBranchRemove removes the child node of the currentNode passed to the method and routes around it to maintain
     *  the structure of the Binary Search Tree
     * @param parentNode - the parent node of the node to be removed
     * @param item - value to be removed
     * @return - true when accomplished
     */
    private boolean singleBranchRemove(BinaryNode<T> parentNode, T item){
        if(parentNode.left.data.equals(item)){ // Remove the left node
            if(parentNode.left.left == null){ // If the left of the remove node is null
                parentNode.left = parentNode.left.right;
            }else{
                parentNode.left = parentNode.left.left;
            }
        }else{ // Remove the right node
            if(parentNode.right.left == null){ // If the left of the remove node is null
                parentNode.right = parentNode.right.right;
            }else{
                parentNode.right = parentNode.right.left;
            }
        }
        size--;
        return true;
    }

    /**
     * doubleBranchRemove is built to handle the case where we have to balance removing a node with two children or subbranches
     * @param parentNode - the parent node of the one we want to remove
     * @param item - value to be removed
     * @return - calls either the leafRemove or singleBranchRemove method, depending on what case we have to handle
     */
    private boolean doubleBranchRemove(BinaryNode<T> parentNode, T item){
        if(rootNode.data.equals(item)){
            parentNode = rootNode;
        }else if(parentNode.left.data.equals(item)){
            parentNode = parentNode.left;
        }else{
            parentNode = parentNode.right;
        }

        // Find the successor node
        BinaryNode<T> successorNode = parentNode.right; // Move to the right to begin finding successor
        BinaryNode<T> successorParent = parentNode; // Get the successor parent node
        int count = 0;
        while(successorNode.left != null){ // Check two down to the left (because we want the parent node of the successor)
            if(count == 0){ // Move the parent to the right at first
                successorParent = successorParent.right;
            }else{ // Then down to the left every time after that
                successorParent = successorParent.left;
            }
            successorNode = successorNode.left;
            count++;
        }

        parentNode.data = successorNode.data; // Pull the data from the successor node and set it in the current node

        if(successorNode.right == null){ // Case 1 - removing a leaf - because sucessorNode.left has to be null
            return leafRemove(successorParent,successorNode.data); // Remove the successorNode value
        }else{ // Case 2 - route around a node
            return singleBranchRemove(successorParent,successorNode.data); // Remove the successorNode value
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
     * @throws NullPointerException if any of the items is null
     */
    @Override
    public boolean removeAll(Collection<? extends T> items) {
        Object[] itemArray = items.toArray();
        boolean itemRemoved = false;
        for(Object item: itemArray){
            if(item == null){
                System.err.println("Binary Search Tree cannot remove a null value");
                throw new NullPointerException();
            }
        }
        for(Object item: itemArray){
            if(remove((T) item)){
                itemRemoved = true;
            }
        }
        return itemRemoved;
    }

    /**
     * Returns the number of items in this set.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns an ArrayList containing all of the items in this set, in sorted
     * order.
     */
    @Override
    public ArrayList<T> toArrayList() {
        ArrayList<T> treeArray = new ArrayList<>();
        arrayListDFT(treeArray,rootNode);
        return treeArray;
    }

    /**
     * arrayListDFT is a recursive method that is used to do inorder depth-first traversal
     * @param treeArray - the Binary Search Tree that we'll be converting to an array
     * @param current - The current node that we're on
     */
    private void arrayListDFT(ArrayList<T> treeArray, BinaryNode<T> current){
        if(current == null){
            return; // Time to leaf
        }
        arrayListDFT(treeArray,current.left);
        treeArray.add(current.data);
        arrayListDFT(treeArray,current.right);
    }

    /**
     * BinaryNode inner class
     * data is the data contained in the BinaryNode
     * left is a pointer to the left BinaryNode
     * right is a pointer to the right BinaryNode
     */
    private class BinaryNode<T extends Comparable<? super T>>{
        T data;
        BinaryNode left = null;
        BinaryNode right = null;
    }

}
