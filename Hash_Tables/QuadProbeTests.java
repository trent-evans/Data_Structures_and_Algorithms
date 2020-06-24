package assignment06;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;

public class QuadProbeTests {

    public static void main (String[] args){

        // Create the hash table and check to make sure the capacity got set correctly
        /*
         * I chose to use the bad hash functor because it is more likely to have collisions as opposed to my good hash
         * functor which does a pretty good job of spreading things out.
         * I figure that my bad hash functor just goes off the length of the word.  That makes it much easier for me to
         * make sure that hash values are easily duplicated, which will make it much easier to make sure the quadratic
         * probing functions as it should.
         */
        QuadProbeHashTable quadTest = new QuadProbeHashTable(10,new BadHashFunctor());
        // Lambda threshold should now be 12 elements

        // Tests
        primeTest();
        emptyTests(quadTest);
        addTests(quadTest);
        containsTests(quadTest);
        removeTest(quadTest);
        resizeRehashTest(quadTest);

        System.out.println("");
        System.out.println("All tests passed!");
        System.out.println("");
    }

    /**
     * isPrimeTest checks to see if the isPrime and generateNextPrimeCapacity methods are working
     */
    public static void primeTest(){

        QuadProbeHashTable testTable = new QuadProbeHashTable(10,new BadHashFunctor());

        // Basic isPrime tests
        Assert.assertTrue(testTable.isPrime(11));
        Assert.assertTrue(testTable.isPrime(23));
        Assert.assertTrue(testTable.isPrime(73));
        Assert.assertTrue(testTable.isPrime(2)); // It will never be the capacity, because that's ridiculous, but it's still good to check
        Assert.assertFalse(testTable.isPrime(12));
        Assert.assertFalse(testTable.isPrime(49));
        Assert.assertFalse(testTable.isPrime(105));

        // Check to make sure it generates the right prime capacity next
        Assert.assertEquals(testTable.generateNextPrimeCapacity(10),23);
        Assert.assertEquals(testTable.generateNextPrimeCapacity(12),29);
        Assert.assertEquals(testTable.generateNextPrimeCapacity(45),97);

        System.out.println("Prime tests passed");
    }

    /**
     * emptyTests runs tests on an empty Quad Probe Hash Table in order to make sure that things are good to go at the beginning
     * This test also makes use of the capacity, isEmpty, size, and contains methods
     * @param quadTest - The input QuadProbeHashTable
     */
    public static void emptyTests(QuadProbeHashTable quadTest){
        // Make sure capacity was changed
        Assert.assertFalse(10 == quadTest.capacity());
        Assert.assertEquals(23, quadTest.capacity());
        // Make sure that everything is empty
        Assert.assertTrue(quadTest.isEmpty());
        Assert.assertEquals(0,quadTest.size());
        Assert.assertFalse(quadTest.contains("Jim"));
        System.out.println("Empty tests passed");
    }

    /**
     * addTests runs test on an empty Quad Probe Hash Table to make sure that values are added using add and addAll
     * This test also makes use of the contains, containsAll, size, and capacity methods
     * @param quadTest - The input QuadProbeHashTable
     */
    public static void addTests(QuadProbeHashTable quadTest){

        // Add a single value
        Assert.assertTrue(quadTest.add("Jim"));
        Assert.assertFalse(quadTest.add("Jim")); // Add the same value again, will return false
        Assert.assertTrue(quadTest.contains("Jim"));
        Assert.assertFalse(quadTest.isEmpty());
        Assert.assertEquals(1,quadTest.size());

        // Add a few more values to see if it can handle multiples
        Assert.assertTrue(quadTest.add("Pam"));
        Assert.assertFalse(quadTest.add("Pam"));
        Assert.assertTrue(quadTest.contains("Pam"));
        Assert.assertEquals(2,quadTest.size());

        // Use addAll to test adding here
        // I'm purposely adding a lot of 5 character names to maximize the use of the quadratic probing
        ArrayList<String> names = new ArrayList<>(Arrays.asList("Oscar","Angela","Kevin","Mered","Creed","Michael","Dwigt")); // Add 7 values - 5 values w/ 5 characters
        Assert.assertTrue(quadTest.addAll(names));
        Assert.assertFalse(quadTest.addAll(names));
        Assert.assertTrue(quadTest.containsAll(names));
        // The size should have increased, but the capacity should be the same for now
        Assert.assertEquals(9,quadTest.size());
        Assert.assertEquals(23,quadTest.capacity());
        // Updates names to check the containsAll if not all values are present
        // Add a few more 5 character names - 7 total values each with 5 characters
        names.add("Kelly");
        names.add("Clark");
        Assert.assertFalse(quadTest.containsAll(names));
        Assert.assertTrue(quadTest.addAll(names));
        Assert.assertFalse(quadTest.addAll(names));
        // The value should be right underneath the threshold to do a rehash and resize
        Assert.assertEquals(11,quadTest.size());
        Assert.assertEquals(23,quadTest.capacity());
        Assert.assertTrue((double)quadTest.size()/quadTest.capacity() < 0.5);

        System.out.println("Add tests passed");
    }

    /**
     * Check to make sure the contains method works well
     * Truthfully, I did a pretty good job of that inside the add method, but it's still good to check in a separate test
     */
    public static void containsTests(QuadProbeHashTable quadTest){
        ArrayList<String> names = new ArrayList<>(Arrays.asList("Oscar","Angela","Kevin","Mered","Creed","Michael","Dwigt"));

        // Check contains on a couple single values - some added singly, and others added with addAll
        Assert.assertTrue(quadTest.contains("Jim"));
        Assert.assertTrue(quadTest.contains("Pam"));
        Assert.assertTrue(quadTest.contains("Dwigt"));
        Assert.assertTrue(quadTest.contains("Mered"));
        Assert.assertFalse(quadTest.contains("Andy"));
        Assert.assertFalse(quadTest.contains("Erin"));
        Assert.assertFalse(quadTest.contains("Toby"));
        Assert.assertFalse(quadTest.contains("Plop"));

        // Check containsAll on the same base array as in the addAll test
        // Plus I add and remove a couple values to make sure it's not just hard coded for certain lengths or something
        Assert.assertTrue(quadTest.containsAll(names));
        names.add("Phyllis");
        Assert.assertFalse(quadTest.containsAll(names));
        names.remove("Kevin");
        Assert.assertFalse(quadTest.containsAll(names));
        names.remove("Michael");
        Assert.assertFalse(quadTest.containsAll(names));
        names.remove("Phyllis");
        Assert.assertTrue(quadTest.containsAll(names));

        System.out.println("Contains tests passed");
    }

    /**
     * removeTest is a test method for testing my remove, removeAll, and my valueNotActive methods
     * valueNotActive is not a required method, but I wrote it to make testing the remove method more sound
     * this method also makes use of the size, and add methods
     * @param quadTest - The testing Quad Probe Hash Table
     */
    public static void removeTest(QuadProbeHashTable quadTest){

        // Remove a single value
        Assert.assertTrue(quadTest.remove("Jim"));
        Assert.assertFalse(quadTest.remove("Jim"));
        Assert.assertTrue(quadTest.valueNotActive("Jim"));
        Assert.assertEquals(10,quadTest.size());
        Assert.assertTrue(quadTest.remove("Pam"));
        Assert.assertFalse(quadTest.remove("Pam"));
        Assert.assertTrue(quadTest.valueNotActive("Pam"));
        Assert.assertEquals(9,quadTest.size());

        // Add them back in because I need them for a later test
        Assert.assertTrue(quadTest.add("Jim"));
        Assert.assertTrue(quadTest.add("Pam"));
        Assert.assertEquals(11, quadTest.size());

        // Check negative cases of valueNotActive
        Assert.assertFalse(quadTest.valueNotActive("Dwigt"));
        Assert.assertFalse(quadTest.valueNotActive("Michael"));
        Assert.assertFalse(quadTest.valueNotActive("Jim"));

        // Remove multiple values
        ArrayList<String> names = new ArrayList<>(Arrays.asList("Dwigt","Jim","Michael","Creed"));
        Assert.assertTrue(quadTest.removeAll(names));
        Assert.assertFalse(quadTest.removeAll(names));
        names.add("Kevin");
        Assert.assertTrue(quadTest.removeAll(names));
        Assert.assertFalse(quadTest.removeAll(names));

        // Add everything back in so that the array doesn't break
        Assert.assertTrue(quadTest.addAll(names));

        System.out.println("Remove tests passed");
    }

    /**
     * resizeRehashTest tests the resizing and rehashing capabilities of the Quad Probe Hash Table
     * In particular, it makes sure that all of the same values are in the new resized hash table as were in the smaller one
     * The other major test is to make sure that values that are lazy removed are not copied over into the new hash table
     * @param quadTest - the Quad Probe Hash Table that are used on the tests
     */
    public static void resizeRehashTest(QuadProbeHashTable quadTest){

        // Make sure everything is still coming in at the right valeus
        Assert.assertEquals(11,quadTest.size());
        Assert.assertEquals(23,quadTest.capacity());
        Assert.assertTrue((double)quadTest.size()/quadTest.capacity() < 0.5);

        // Remove a couple values to make sure that it doesn't copy over removed items
        Assert.assertTrue(quadTest.remove("Dwigt"));
        Assert.assertTrue(quadTest.remove("Mered"));
        Assert.assertTrue(quadTest.valueNotActive("Dwigt"));
        Assert.assertTrue(quadTest.valueNotActive("Mered"));

        // Add a value which should force it to resize
        Assert.assertTrue(quadTest.add("Dwight"));
        Assert.assertTrue(quadTest.add("Meredith"));
        quadTest.add("Toby");
        Assert.assertEquals(12,quadTest.size());
        Assert.assertEquals(47,quadTest.capacity());
        Assert.assertTrue(quadTest.contains("Toby"));
        Assert.assertTrue((double)quadTest.size()/quadTest.capacity() < 0.5);

        // Check that it contains all the previous stuff and not the stuff we pulled out
        ArrayList<String> names = new ArrayList<>(Arrays.asList("Oscar","Angela","Kevin","Meredith","Creed","Michael","Dwight"));
        names.add("Jim");
        names.add("Pam");
        names.add("Kelly");
        names.add("Clark");
        names.add("Toby");
        Assert.assertTrue(quadTest.containsAll(names));

        // Check to make sure the things we removed and were not active in the previous hash table are not present in this one
        Assert.assertFalse(quadTest.contains("Dwigt"));
        Assert.assertFalse(quadTest.contains("Mered"));
        Assert.assertFalse(quadTest.valueNotActive("Dwigt"));
        Assert.assertFalse(quadTest.valueNotActive("Mered"));

        System.out.println("Resize & Rehash tests passed");
    }



}
