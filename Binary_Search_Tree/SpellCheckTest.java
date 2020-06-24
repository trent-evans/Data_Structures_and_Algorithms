package assignment05;

import java.io.File;
import java.util.List;

public class SpellCheckTest {

    public static void main(String[] args) {

        // I will admit, the testing isn't extensive, and it's mostly visual
        // but everything works!
        SpellChecker mySC = new SpellChecker(new File("dictionary.txt"));

        run_spell_check(mySC, "hello_world.txt");
        run_spell_check(mySC, "good_luck.txt");
        run_spell_check(mySC,"zelda.txt");

        // Test adding stuff
        mySC.addToDictionary("zelda");
        mySC.addToDictionary("link");
        mySC.addToDictionary("triforce");

        // Visually check
        run_spell_check(mySC,"zelda.txt");

        // Test removing stuff
        mySC.removeFromDictionary("thief");
        mySC.removeFromDictionary("macrosimulation");
        mySC.removeFromDictionary("luck");
        mySC.removeFromDictionary("dagger");
        mySC.removeFromDictionary("yawning");
        mySC.removeFromDictionary("salesperson");
        mySC.removeFromDictionary("cachepot");
        mySC.removeFromDictionary("abandonded");
        mySC.removeFromDictionary("fat");

        // Visually check
        run_spell_check(mySC,"dictionary.txt");

        // Everything checked out!

    }

    private static void run_spell_check(SpellChecker sc, String documentFilename) {

        File doc = new File(documentFilename);
        List<String> misspelledWords = sc.spellCheck(doc);
        if (misspelledWords.size() == 0) {
            System.out.println("There are no misspelled words in file " + doc + ".");
        } else {
            System.out.println("The misspelled words in file " + doc + " are:");
            for (String w : misspelledWords) {
                System.out.println("\t" + w);
            }
        }
    }


}
