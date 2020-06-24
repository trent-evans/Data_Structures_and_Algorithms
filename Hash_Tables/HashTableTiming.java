package assignment06;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class HashTableTiming {

    public static void main(String[] args){
        // Uncomment and run as necessary, depending on the test to be run
//       runtimeCollisionsTests(); // Gives the runtime and collisions for the add method
       hashRuntimeTest(); // Gives runtime for the individual functors
    }

    /**
     * runtimeCollisionsTests gives the average run time and average number of collisions for incrementing array sizes
     * from 100 to 2000.  It runs the test on adding 2000 of the same words to the array.
     */
    public static void runtimeCollisionsTests(){
        ArrayList<String> wordList = readFromFile("dictionary.txt");
        System.out.println("Size\tRuntime\tCollisions");

        for(int x = 1; x <= 20; x++){

            int capacity = 100 * x;
            long startTime, midPointTime, endTime;
            int timesToLoop = 10000;

            HashFunctor functor = new GoodHashFunctor();

            startTime = System.nanoTime();
            while (System.nanoTime() - startTime < 1000000000) { // empty block
            }

            int totalCollisions = 0;
            int testCollisions = 0;

            startTime = System.nanoTime();

            for(int t = 0; t < timesToLoop; t++) {
                ChainingHashTable testTable = new ChainingHashTable(capacity, functor);
                testTable.addAll(wordList);
                totalCollisions += testTable.collisions();
            }

            midPointTime = System.nanoTime();

            for(int t = 0; t < timesToLoop; t++) {
                ChainingHashTable testTable = new ChainingHashTable(capacity, functor);
                testCollisions += testTable.collisions();
            }

            endTime = System.nanoTime();

            double averageTime = (double)((midPointTime - startTime) - (endTime - midPointTime)) / timesToLoop;
            int averageCollisions = totalCollisions / timesToLoop;
            System.out.println(capacity + "\t" + averageTime + "\t" + averageCollisions);
        }
    }

    /**
     * Generates a random string for testing purposes
     * Copied directly from my assignment03 RandomString class
     * @param length - Length of the random string
     * @return - String of random letters of length length
     */
    private static String generateRandomString(int length) {
        String ret = "";
        char[] letters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        for(int x = 0; x < length; x++) {
            int index = (int) (Math.random() * 26);
            ret += letters[index];
        }

        return ret;

    }

    /**
     * hashRuntimeTest is a driver function to run and discover the average runtimes for the different Hash Functors
     * It generates String of random letters for lengths 1,000 to 20,000 and passes that same string into each functor
     * test to verify accuracy between each functor.
     * There's also an initial test generated that has a random string of length 10 at the beginning because
     * the first timing test is always super slow when it's getting warmed up
     */
    private static void hashRuntimeTest(){

        System.out.println("Size\tBad\tMediocre\tGood");

        HashFunctor badFunctor = new BadHashFunctor();
        HashFunctor medFunctor = new MediocreHashFunctor();
        HashFunctor goodFunctor = new GoodHashFunctor();

        for(int x = 0; x <= 20; x++){
            String random = "";
            if(x == 0){
                random = generateRandomString(10);
            }else {
                random = generateRandomString(x * 1000);
            }
            double averageBadTime = hashTest(badFunctor,random);
            double averageMedTime = hashTest(medFunctor,random);
            double averageGoodTime = hashTest(goodFunctor,random);

            System.out.println(x*1000 + "\t" + averageBadTime + "\t" + averageMedTime + "\t" + averageGoodTime);

        }

    }

    /**
     * hashTest is the timing method for determining the runtime of the different hashFunctors
     * It runs 100,000 tests for each input to verify accuracy and reports the average runtime in nano seconds
     * @param functor - the HashFunctor that will e defining our hash values
     * @param randomString - The randomly generated string that will be hashed
     * @return - The average runtime for 100,000 tests of hashing the random string
     */
    private static double hashTest(HashFunctor functor, String randomString){
        long startTime, midpointTime, stopTime;
        int timesToLoop = 100000;

        // Run the BadHash tests
        startTime = System.nanoTime();
        while (System.nanoTime() - startTime < 1000000000) { // empty block
        }
        startTime = System.nanoTime();

        for(int b = 0; b < timesToLoop; b++){
            functor.hash(randomString);
        }

        midpointTime = System.nanoTime();

        for(int b = 0; b < timesToLoop; b++){
        }

        stopTime = System.nanoTime();

        return ((double)((midpointTime - startTime) - (stopTime - midpointTime)) / timesToLoop);

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
                if(words.size() == 2000){
                    break;
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("File " + filename + " cannot be found.");
        }
        return words;
    }

}
