package assignment06;

import org.junit.Assert;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class HashTableTest {

    public static void main(String[] args){
        // Super basic, just to make sure that everything works the way it should
        System.out.println("Functor\tCollisions - Initial tests");
        runTests(new ReallyBadHashFunctor(),"Really Bad");
        // Further testing for each one I implement
        runTests(new BadHashFunctor(), "Bad");
        runTests(new MediocreHashFunctor(), "Mediocre");
        runTests(new GoodHashFunctor(), "Good");
        System.out.println("");
        System.out.println("All tests passed!");
        System.out.println("");

        System.out.println("Functor\tCollisions");
        ArrayList<String> wordList = readFromFile("dictionary.txt");
        collisionTest(new BadHashFunctor(),"Bad",wordList);
        collisionTest(new MediocreHashFunctor(), "Mediocre", wordList);
        collisionTest(new GoodHashFunctor(), "Good", wordList);
    }

    /**
     * runTests is a test method that allows us to pass a functor (and its name as a string) into that allows running
     * the same battery of tests repeatedly for different functors.  This way I don't have to copy and paste code
     * over and over for different scenarios, but still test different functors
     * @param functor - The HashFunctor used for creating the hash values
     * @param functorName - The string name of the functor. Ex: "Really Bad", "Bad", "Mediocre", "Good"
     *                    - The functorName only serves for the System.out.println at the end to say the tests passed
     */
    private static void runTests(HashFunctor functor, String functorName){

        // Functor hash values test - the same input should always generate the same value
        Assert.assertEquals(functor.hash("trent"), functor.hash("trent"));

        // Create a Chaining Hash Table based on the ReallyBadHashFunctor provided.
        ChainingHashTable myHashTable = new ChainingHashTable(10,functor);

        // Single add tests
        Assert.assertTrue(myHashTable.isEmpty());
        Assert.assertTrue(myHashTable.add("eric"));
        Assert.assertFalse(myHashTable.isEmpty());
        Assert.assertFalse(myHashTable.add("eric"));
        Assert.assertEquals(1,myHashTable.size());
        Assert.assertTrue(myHashTable.add("trent"));
        Assert.assertEquals(2,myHashTable.size());

        // addAll tests
        ArrayList<String> names = new ArrayList<>(Arrays.asList("andrew","ashley","warner","katie","will","austin","varun","ben","leslie"));
        Assert.assertTrue(myHashTable.addAll(names));
        Assert.assertEquals(11,myHashTable.size());
        Assert.assertFalse(myHashTable.addAll(names));
        Assert.assertEquals(11,myHashTable.size());
        names.add("matt");
        Assert.assertTrue(myHashTable.addAll(names));
        Assert.assertEquals(12,myHashTable.size());

        // contains and containsAll tests
        Assert.assertTrue(myHashTable.contains("eric"));
        Assert.assertFalse(myHashTable.contains("bob"));
        Assert.assertTrue(myHashTable.containsAll(names));
        names.add("joe");
        Assert.assertFalse(myHashTable.containsAll(names));

        // remove and removeAllTests

        Assert.assertTrue(myHashTable.remove("eric"));
        Assert.assertEquals(11,myHashTable.size());
        Assert.assertFalse(myHashTable.remove("bob"));
        Assert.assertTrue(myHashTable.removeAll(names));
        Assert.assertEquals(1,myHashTable.size());
        Assert.assertFalse(myHashTable.removeAll(names));

        // clear test
        Assert.assertFalse(myHashTable.isEmpty());
        myHashTable.clear();
        Assert.assertTrue(myHashTable.isEmpty());
        Assert.assertEquals(0,myHashTable.size());


        System.out.println(functorName + "\t" + myHashTable.collisions());

    }

    private static void collisionTest(HashFunctor functor, String functorName, ArrayList<String> wordArray){
        ChainingHashTable myHashTable = new ChainingHashTable(100,functor);
        myHashTable.addAll(wordArray);
        System.out.println(functorName + "\t" + myHashTable.collisions());
    }

    /**
     * Returns a list of the words contained in the specified file. (Note that
     * symbols, digits, and spaces are ignored.)
     * I copied and pasted this from our SpellChecker in the assignment05 files
     * I figured it was easier than creating
     *
     * @param filename
     *          - the file name to be read as a string
     * @return a List of the Strings in the input file
     */
    private static ArrayList<String> readFromFile(String filename) {
        ArrayList<String> words = new ArrayList<String>();

        try (Scanner fileInput = new Scanner(new File(filename))) {
            fileInput.useDelimiter("\\s*[^a-zA-Z]\\s*");

            while (fileInput.hasNext()) {
                String s = fileInput.next();
                if (!s.equals("")) {
                    words.add(s.toLowerCase());
                }
                if(words.size() == 100){
                    break;
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("File " + filename + " cannot be found.");
        }
        return words;
    }


}
