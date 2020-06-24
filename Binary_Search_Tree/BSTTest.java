package assignment05;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class BSTTest<T extends Comparable<? super T>> {

    public static void main(String[] args){

        BinarySearchTree<Integer> intTree = new BinarySearchTree<>();
        ArrayList<Integer> intNullTest = new ArrayList<>();
        intNullTest.add(1);
        intNullTest.add(2);
        intNullTest.add(null);
        intNullTest.add(3);
        intNullTest.add(4);
        // Exception test
        exceptionTest(intTree,intNullTest);
        // Add tests
        addFirstTest(intTree,20);
        ArrayList<Integer> addAllInt = new ArrayList<Integer>(Arrays.asList(10, 30, 35, 25, 15, 5));
        ArrayList<Integer> addSomeInt = new ArrayList<Integer>(Arrays.asList(10, 7, 21, 25, 11, 30, 32, 28));
        addAllTests(intTree,addAllInt,addSomeInt);
        // Contains tests
        ArrayList<Integer> containsPositiveInt = new ArrayList<Integer>(Arrays.asList(20,30,21,11,32,28));
        ArrayList<Integer> containsSomeInt = new ArrayList<Integer>(Arrays.asList(10,7,20,17,25,11,30));
        ArrayList<Integer> containsNoneInt = new ArrayList<Integer>(Arrays.asList(17,39,4,29));
        containsTrueTest(intTree,5,containsPositiveInt);
        containsFalseTest(intTree,45,containsNoneInt,containsSomeInt);
        // toArray Test
        toArrayTest(intTree);
        // Remove tests
        removeLeafTest(intTree,11);
        removeSingleBranchTest(intTree,5);
        removeDoubleBranchTest(intTree,30);
        removeRootTest(intTree);
        ArrayList<Integer> removeSomeInt = new ArrayList<>(Arrays.asList(7,10,40));
        removeSomeTest(intTree,removeSomeInt);
        ArrayList<Integer> removeAllInt = new ArrayList<>(Arrays.asList(15,35,21));
        removeAllTest(intTree,removeAllInt);

        System.out.println(" ");
        System.out.println(">---   Integer tests passed!   ---<");
        System.out.println(" ");

        // See the SpellChecker for tests on String values

        System.out.println("Testing complete!");
    }

    /**
     * exceptionTest is built to make sure that all the possible errors should be thrown and to catch them and make sure that they do throw
     * @param testTree - An empty Binary Search Tree on which we're running tests
     *                 It must be empty to catch the final exceptions of calling first, last, and root on an empty tree
     *                 The other method tests will work if the BST is not empty, but for comprehensive testing purposes
     *                   all exception testing is handled here
     * @param testCollection - an ArrayList of the same type as the testTree
     *                       Must contain null at some point within the ArrayList
     *                       Otherwise exceptions won't be thrown which will make the tests meaningless
     */
    public static <T extends Comparable<? super T>> void exceptionTest(BinarySearchTree<T> testTree, ArrayList<T> testCollection){
        try{
            testTree.add(null);
        }catch (NullPointerException e){
            System.out.println("TEST PASSED - Null Pointer Exception caught: Cannot add null");
        }
        try{
            testTree.remove(null);
        }catch (NullPointerException e){
            System.out.println("TEST PASSED - Null Pointer Exception caught: Cannot remove null");
        }
        try{
            testTree.contains(null);
        }catch (NullPointerException e){
            System.out.println("TEST PASSED - Null Pointer Exception caught: Cannot contain null");
        }
        try{
            testTree.addAll(testCollection);
        }catch (NullPointerException e){
            System.out.println("TEST PASSED - Null Pointer Exception caught: Cannot add null in an ArrayList");
        }
        try{
            testTree.removeAll(testCollection);
        }catch (NullPointerException e){
            System.out.println("TEST PASSED- - Null Pointer Exception caught: Cannot remove null in an ArrayList");
        }
        try{
            testTree.containsAll(testCollection);
        }catch (NullPointerException e){
            System.out.println("TEST PASSED - Null Pointer Exception caught: Cannot contain null in an ArrauList");
        }
        try{
            T first = testTree.first();
        }catch (NoSuchElementException e){
            System.out.println("TEST PASSED - No Such Element Exception caught: Cannot call first on an empty Binary Search Tree");
        }
        try{
            T last = testTree.last();
        }catch (NoSuchElementException e){
            System.out.println("TEST PASSED - No Such Element Excpetion caught: Cannot call last on an empty Binary Search Tree");
        }
        try{
            T root = testTree.root();
        }catch (NullPointerException e){
            System.out.println("TEST PASSED - Null Pointer Excpetion caught: Cannot access the root value of an empty Binary Search Tree");
        }
        System.out.println("Exception tests passed");
    }


    /**
     * addFistTest is made to test adding the root value to a Binary Search Tree using both the add and addAll methods
     *
     *  this method also makes use of the size, first, last, root, and isEmpty methods
     *
     * @param testTree - Empty Binary Search Tree on which to run tests
     * @param item - Single Item to be added to the Binary Search Tree
     * @param <T> - Type of the Binary Search Tree
     */
    public static <T extends Comparable<? super T>> void addFirstTest(BinarySearchTree testTree, T item){

        // Add one item and then make sure that item is the right item, that the size is right, and that the tree isn't empty
        Assert.assertTrue(testTree.add(item)); // Add a single value => true
        Assert.assertFalse(testTree.add(item)); // Try to add it again => false
        Assert.assertEquals(testTree.root(),item);
        Assert.assertEquals(testTree.first(),item);
        Assert.assertEquals(testTree.last(),item);
        Assert.assertEquals(1,testTree.size());
        Assert.assertFalse(testTree.isEmpty());

        // Clear the tree and verify that it's cleared
        testTree.clear();
        Assert.assertEquals(0,testTree.size());
        try{ testTree.root(); } catch (NullPointerException e){} // Because tree root should be null

        // Attempt to add a single value using addAll and verify that it's correct
        ArrayList<T> itemArray = new ArrayList<>();
        itemArray.add(item);
        Assert.assertTrue(testTree.addAll(itemArray));
        Assert.assertFalse(testTree.addAll(itemArray));
        Assert.assertEquals(item,testTree.root());
        Assert.assertEquals(item,testTree.first());
        Assert.assertEquals(testTree.first(),testTree.last());
        Assert.assertEquals(1,testTree.size());
        Assert.assertFalse(testTree.isEmpty());

        System.out.println("Add First value tests passed");
    }

    /**
     * addAllTests runs tests on a BinarySearchTree using addAll in the following ways:
     *  - It will attempt to add all values of addAllTest (which should not have any values previously in the BST)
     *  - It will attempt to add all values of addSomeTest (which should have some, but not all values previously in the BST)
     *  - It checks to make sure that the sizes are correct between the beginning and the end
     *  - It will check to make sure that the first and last values are equal to the min and max added in the array
     *      - Therefore: new minimum and maximum values of the BST should be added during this step
     * this test also makes use of the size, first, and last methods
     *
     * @param testTree - Binary Search Tree upon which we will be running tests
     * @param addAllTest - ArrayList of values NOT ALREADY IN THE TREE to be added to the BinarySearchTree
     * @param addSomeTest - ArrayList of values (some of which are in the tree, some of which are not) to be added to the tree
     * @param <T> - Type of the testTree, addAllTest ArrayList, and addSomeTest ArrayList
     */
    public static <T extends Comparable<? super T>> void addAllTests(BinarySearchTree testTree, ArrayList<T> addAllTest, ArrayList<T> addSomeTest){

        int sizePreAdd = testTree.size();

        // Do an addAll on an ArrayList of values where none are contained, and an addAll on an ArrayList where some are contained
        Assert.assertTrue(testTree.addAll(addAllTest)); // Will add all values
        Assert.assertFalse(testTree.addAll(addAllTest)); // All values already added - should return false
        Assert.assertTrue(testTree.addAll(addSomeTest)); // Will add some of the values - should still return true
        Assert.assertFalse(testTree.addAll(addSomeTest)); // All values already added - should return false

        // Join the lists and find the minimum and maximum
        ArrayList<T> joinedList = new ArrayList<>();
        for(T item: addAllTest){
            joinedList.add(item);
        }
        for(T item: addSomeTest){
            if(!joinedList.contains(item)) { // Prevent duplicate items
                joinedList.add(item);
            }
        }

        // Also, check size to make sure they're the same
        Assert.assertEquals(sizePreAdd + joinedList.size(), testTree.size()); // Pre-size + joined list size = current size

        T min = joinedList.get(0);
        T max = joinedList.get(0);
        for(int x = 0; x < joinedList.size(); x++){
            if(joinedList.get(x).compareTo(min) < 0){
                min = joinedList.get(x);
            }
            if(joinedList.get(x).compareTo(max) > 0){
                max = joinedList.get(x);
            }
        }

        // Check first and last for the min and the max as well as other
        Assert.assertEquals(min,testTree.first());
        Assert.assertEquals(max,testTree.last());
        System.out.println("Add All Tests Passed");
    }

    /**
     * containsTrueTest is a test method written to check the positive responses (i.e. true) from the contains and containsAll methods
     *
     * @param testTree - Binary Search Tree that we're going to test on
     * @param element - Element that the Binary Search Tree contains
     * @param elementArray - ArrayList of values, all of which are inside the Binary Search Tree
     * @param <T> - Type of the Binary Search Tree, element, and array
     */
    public static <T extends Comparable<? super T>> void containsTrueTest(BinarySearchTree testTree, T element, ArrayList<T> elementArray){
        Assert.assertTrue(testTree.contains(element));
        Assert.assertTrue(testTree.containsAll(elementArray));
        System.out.println("Contains True Test Passed");
    }

    /**
     * containsFalseTest is a test method written to check the negative responses (i.e. false) from the contains and containsAll methods
     *
     * @param testTree - Binary Search Tree that we're going to test on
     * @param element - Element that the Binary Search Tree does not contain
     * @param allElementArray - ArrayList of values, none of which are inside the Binary Search Tree
     * @param someElementArray - ArrayList of values, some of which are inside the Binary Search Tree
     * @param <T> - Type of the Binary Search Tree, element, and arrays
     */
    public static <T extends Comparable<? super T>> void containsFalseTest(BinarySearchTree testTree, T element, ArrayList<T> allElementArray, ArrayList<T> someElementArray){
        Assert.assertFalse(testTree.contains(element));
        Assert.assertFalse(testTree.containsAll(allElementArray));
        Assert.assertFalse(testTree.containsAll(someElementArray));
        System.out.println("Contains false Test Passed");
    }

    /**
     * toArrayTest is a test method that checks to make sure the every value in the array is less than the value after it
     *  it also tests to make sure that the sizes are the same between the Binary Search Tree and the resulting ArrayList
     * @param testTree - the Binary Search Tree on which to run tests
     * @param <T> - The type of the Binary Search Tree
     */
    public static <T extends Comparable<? super T>> void toArrayTest(BinarySearchTree<T> testTree){
        ArrayList<T> testArray = testTree.toArrayList();
        for(int x = 0; x < testArray.size()-1; x++){
            Assert.assertTrue(testArray.get(x).compareTo(testArray.get(x+1)) < 0); // Each subsequent value should be less than the one before
            // Visual confirmation as well
//            System.out.println(x + " " + testArray.get(x));
        }
//        System.out.println(testArray.size()-1 + " " + testTree.last());
        // Verify that sizes are the same between the Binary Search Tree and the array version of the tree
        Assert.assertEquals(testArray.size(),testTree.size());
        System.out.println("toArray Test Passed");
    }

    /**
     * removeLeafTest goes over all the testing and verification of being able to remove a leaf node from a the Binary
     * Search tree.
     * The test method will also pop the value back into the tree at the appropriate location to make further testing easier
     * @param testTree - the Binary Search Tree upon which to test
     * @param leafToRemove - The value of a leaf node that we want to test removing
     * @param <T>
     */
    public static <T extends Comparable<? super T>> void removeLeafTest(BinarySearchTree<T> testTree, T leafToRemove){
        // Gather pre-removal data
        int sizePreRemove = testTree.size();

        // Make sure the input is valid
        Assert.assertTrue(testTree.contains(leafToRemove));
        toArrayTest(testTree);

        // Remove and compare with pre-removal array/size
        Assert.assertTrue(testTree.remove(leafToRemove));
        Assert.assertFalse(testTree.remove(leafToRemove));

        Assert.assertEquals(sizePreRemove-1,testTree.size());
        ArrayList<T> postRemoveList = testTree.toArrayList();
        // Visual confirmation
//        for(int x = 0; x < postRemoveList.size(); x++){
//            if(postRemoveList.get(x) == leafToRemove){
//                System.out.println("*** " + x + " " + postRemoveList.get(x) + " ***");
//            }else {
//                System.out.println(x + " " + postRemoveList.get(x));
//            }
//        }
        Assert.assertFalse(testTree.contains(leafToRemove));
        // Pop the value back in to make it easier to test in the future
        testTree.add(leafToRemove);
        System.out.println("Leaf remove test Passed");
    }

    /**
     * removeSingleBranchTest is a test method used for checking if the remove method works for a case of removing a single
     * value that has a leaf or sub-branch
     * @param testTree - the test Binary Search Tree on which to run tests
     * @param valueToRemove - The value to remove ** This value must have one sub-branch or item for the test to be effective **
     * @param <T> - Type of the value and the test Tree
     */
    public static <T extends Comparable<? super T>> void removeSingleBranchTest(BinarySearchTree<T> testTree, T valueToRemove) {

        // Pull the size pre-removal
        int preRemoveSize = testTree.size();

        // Make sure everything is kosher ahead of time
        Assert.assertTrue(testTree.contains(valueToRemove));
        toArrayTest(testTree);

        // Remove the element
        Assert.assertTrue(testTree.remove(valueToRemove));
        Assert.assertFalse(testTree.remove(valueToRemove));

        // Test to make sure all is well
        Assert.assertFalse(testTree.contains(valueToRemove));
        Assert.assertEquals(testTree.size(),preRemoveSize-1);

        ArrayList<T> testArray = testTree.toArrayList();
//        System.out.println(" " + valueToRemove + " ");
        for(int x = 0; x < testTree.size()-1; x++){
            // Visual confirmation
//            System.out.println(x + " " + testArray.get(x));
            // Verify that each value is still less than the previous one in the array
            Assert.assertTrue(testArray.get(x).compareTo(testArray.get(x+1)) < 0);
        }
//        System.out.println(testTree.size()-1 + " " + testTree.last());

//        System.out.println(" ");
        System.out.println("Single branch remove test Passed");
//        System.out.println(" ");
    }

    /**
     * removeDoubleBranchTest checks to make sure that you can remove a value from a double branched location on a Binary Search Tree
     * @param testTree - The Binary Search Tree on which we're running tests
     * @param valueToRemove - The value to be removed from the BST ** The value to remove must have two subtrees in order for the test to be effective **
     * @param <T> - Type of the Binary Search Tree and the valueToRemove
     */
    public static <T extends Comparable<? super T>> void removeDoubleBranchTest(BinarySearchTree<T> testTree, T valueToRemove) {
        int preRemoveSize = testTree.size();
//        System.out.println(preRemoveSize);
        Assert.assertTrue(testTree.contains(valueToRemove));
        toArrayTest(testTree);

        // Remove the elements
        Assert.assertTrue(testTree.remove(valueToRemove));
        Assert.assertFalse(testTree.remove(valueToRemove));

        // Test to make sure all is bueno
        Assert.assertFalse(testTree.contains(valueToRemove));
        Assert.assertEquals(preRemoveSize-1,testTree.size());

        ArrayList<T> testArray = testTree.toArrayList();
//        System.out.println(" ");
        for(int x = 0; x < testArray.size()-1; x++){
            // Visual confirmation
//            System.out.println(x + " " + testArray.get(x));
            Assert.assertTrue(testArray.get(x).compareTo(testArray.get(x+1))<0);
        }
//        System.out.println(testTree.size()-1 + " " + testTree.last());

        System.out.println("Double branch remove test Passed");
    }

    /**
     * removeRootTest tests to make sure we can safely remove the root from the input Binary Search Tree
     * @param testTree - The Binary Search Tree from which we will remove the root
     * @param <T> - The type of the Binary Search Tree
     */
    public static <T extends Comparable<? super T>> void removeRootTest(BinarySearchTree<T> testTree) {

        int preRemoveSize = testTree.size();
        T firstRoot = testTree.root();

        Assert.assertTrue(testTree.contains(firstRoot));
        toArrayTest(testTree);

        // Pull that root
        Assert.assertTrue(testTree.remove(firstRoot));
        Assert.assertFalse(testTree.remove(firstRoot));

        // Check size and contains
        Assert.assertFalse(testTree.contains(firstRoot));
        Assert.assertFalse(testTree.root() == firstRoot);
        Assert.assertEquals(preRemoveSize-1,testTree.size());

        ArrayList<T> testArray = testTree.toArrayList();
        for(int x = 0; x < testArray.size()-1; x++){
            Assert.assertTrue(testArray.get(x).compareTo(testArray.get(x+1)) < 0);
        }

        System.out.println("Root remove test Passed");
    }

    /**
     * removeSomeTest runs tests on a BinarySearchTree with an ArrayList of values, some of which are in the BST, some of which are not
     * @param testTree - The Binary Search Tree on which we're running tests
     * @param removeSomeTest - The ArrayList with values to be removed.  Some should be in the BST, some should not
     * @param <T> - They type of the BST and the ArrayList
     */
    public static <T extends Comparable<? super T>> void removeSomeTest(BinarySearchTree<T> testTree, ArrayList<T> removeSomeTest) {
        // Just to make sure there's no shenanigans with the size
        int preRemoveSize = testTree.size();
        Assert.assertTrue(testTree.size() == preRemoveSize);

        // Run the test
        Assert.assertTrue(testTree.removeAll(removeSomeTest));
        Assert.assertFalse(testTree.removeAll(removeSomeTest));
        Assert.assertTrue(testTree.size() != preRemoveSize);

        ArrayList<T> testArray = testTree.toArrayList();
        for(int x = 0; x < testArray.size() -1; x++){
            Assert.assertTrue(testArray.get(x).compareTo(testArray.get(x+1)) < 0);
        }

        System.out.println("Remove some test passed");

    }

    /**
     * removeAllTest tests the removeAll method with an ArrayList of values, all of which should be present in the BST
     *  - In an ideal situtaiton, the removeAllList should have values that satisfy each of the three remove cases:
     *      1) Remove a leaf
     *      2) Remove a node with a single sub-branch
     *      3) Remove a node with two sub-branches
     * @param testTree - BST to run tests on
     * @param removeAllList - List of values all of which are in the BST and of which at least one satisfies each removal case
     * @param <T> - The type of both the BST and the removeAllList
     */
    public static <T extends Comparable<? super T>> void removeAllTest(BinarySearchTree<T> testTree, ArrayList<T> removeAllList) {
        // Make sure there's nothing wrong with the size
        int preRemoveSize = testTree.size();
        Assert.assertTrue(testTree.size() == preRemoveSize);

        // Run the test
        Assert.assertTrue(testTree.removeAll(removeAllList));
        Assert.assertFalse(testTree.removeAll(removeAllList));
        Assert.assertTrue(testTree.size() != preRemoveSize);

        ArrayList<T> testArray = testTree.toArrayList();
        for(int x = 0; x < testArray.size() -1; x++){
            Assert.assertTrue(testArray.get(x).compareTo(testArray.get(x+1)) < 0);
        }
        System.out.println("Remove all test Passed");
    }

}
