package lab06;

import org.junit.Assert;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoubLinkListTest {

    public static void main(String[] args){

        System.out.println(" ");
        // Create
        DoublyLinkedList<String> stringTest1 = new DoublyLinkedList<>();

        // >--- Test for Error Handling ---<

        try{
            stringTest1.getFirst();
        }catch (NoSuchElementException e){
            System.out.println("PASSED - trying to access element that doesn't exist");
        }
        try{
            stringTest1.getLast();
        }catch (NoSuchElementException e){
            System.out.println("PASSED - trying to access element that doesn't exist");
        }
        try{
            stringTest1.removeLast();
        }catch (NoSuchElementException e){
            System.out.println("PASSED - trying to remove element that doesn't exist");
        }
        try{
            stringTest1.removeFirst();
        }catch (NoSuchElementException e){
            System.out.println("PASSED - trying to remove element that doesn't exist");
        }
        try{
            stringTest1.add(2,"Pam");
        }catch(IndexOutOfBoundsException e){
            System.out.println("PASSED - adding out of bounds");
        }
        try{
            String test1 = stringTest1.get(2);
        }catch(NoSuchElementException e){
            System.out.println("PASSED - getting out of bounds");
        }
        try{
            stringTest1.get(-1);
        }catch(NoSuchElementException e){
            System.out.println("PASSED - getting out of bounds");
        }

        // Test negative responses and stuff on empty arrays
        Assert.assertEquals(-1,stringTest1.indexOf("Michael")); // Value not present
        Assert.assertEquals(-1,stringTest1.lastIndexOf("Michael")); // Value not present
        Assert.assertEquals(true, stringTest1.isEmpty()); // Empty array
        Assert.assertEquals(0,stringTest1.size());

        Object[] emptyStringTest = stringTest1.toArray();
        Assert.assertEquals(0,emptyStringTest.length); // toArray test

        // Now that all the negative cases are done, let's get to real tests for multiple data types
        // First off, strings!
        stringTest1.addFirst("Jim");

        // Single element in the DoublyLinkedList
        Assert.assertEquals("Jim",stringTest1.getFirst()); // Test to make sure the head is correct
        Assert.assertEquals("Jim",stringTest1.getLast()); // Tail is the same because it's a single array
        Assert.assertEquals(stringTest1.getFirst(),stringTest1.getLast()); // Values should be the same
        Assert.assertEquals("Jim",stringTest1.get(0)); // Try the get method
        Assert.assertEquals(1,stringTest1.size()); // Check the size and make sure it's equal to 1
        Assert.assertEquals(0,stringTest1.lastIndexOf("Jim")); // Test my index ofs
        Assert.assertEquals(0,stringTest1.indexOf("Jim"));
        Object[] singleStringTest = stringTest1.toArray(); // Convert to Array
        Assert.assertEquals("Jim",singleStringTest[0]);

        // Add more elements using first and last
        stringTest1.addFirst("Pam"); // {Pam, Jim}
        stringTest1.addLast("Oscar"); // {Pam, Jim, Oscar}
        Assert.assertEquals(3,stringTest1.size());
        Object[] threeStringTest = stringTest1.toArray();
        Assert.assertEquals("Pam",threeStringTest[0]);
        Assert.assertEquals("Jim",threeStringTest[1]);
        Assert.assertEquals("Oscar",threeStringTest[2]);
        Assert.assertEquals(3,stringTest1.size());
        // Verify that first and last values are correct
        Assert.assertEquals("Oscar",stringTest1.getLast());
        Assert.assertEquals("Pam",stringTest1.getFirst());

        // Add more elements using add, test indexOf and lastIndexOf, test size, make sure things are good, etc.
        stringTest1.add(2,"Michael"); // {Pam, Jim, Michael, Oscar}
        Assert.assertEquals("Michael",stringTest1.get(2));
        Assert.assertEquals(4,stringTest1.size());
        stringTest1.add(2,"Kevin"); // {Pam, Jim, Kevin, Michael, Oscar}
        Assert.assertEquals("Kevin", stringTest1.get(2));
        Assert.assertEquals("Michael",stringTest1.get(3));
        stringTest1.add(3,"Jim");
        stringTest1.addLast("Jim"); // {Pam, Jim, Kevin, Jim, Michael, Oscar, Jim}
        Assert.assertEquals(1,stringTest1.indexOf("Jim"));
        Assert.assertEquals(6,stringTest1.lastIndexOf("Jim"));

        // Test some removes, removeLast, and removeFirst
        String removeLastTest1 = stringTest1.removeLast(); // {Pam, Jim, Kevin, Jim, Michael, Oscar}
        Assert.assertEquals("Jim",removeLastTest1);
        Assert.assertEquals(6,stringTest1.size());
        String removeFirstTest1 = stringTest1.removeFirst(); // {Jim, Kevin, Jim, Michael, Oscar}
        Assert.assertEquals("Pam",removeFirstTest1);
        Assert.assertEquals(5,stringTest1.size());
        String removeTest1 = stringTest1.remove(2); // {Jim, Kevin, Michael, Oscar}
        Assert.assertEquals("Jim", removeTest1);
        Object[] stringArrayTest = stringTest1.toArray();
        Assert.assertEquals("Jim",stringArrayTest[0]);
        Assert.assertEquals("Kevin",stringArrayTest[1]);
        Assert.assertEquals("Michael",stringArrayTest[2]);
        Assert.assertEquals("Oscar",stringArrayTest[3]);
        Assert.assertEquals(4,stringTest1.size());
        String removeAsRemoveFirst = stringTest1.remove(0); // {Kevin, Michael, Oscar};
        Assert.assertEquals("Jim",removeAsRemoveFirst);
        Assert.assertEquals(3,stringTest1.size());
        String removeAsRemoveLast = stringTest1.remove(2); // {Kevin, Michael}
        Assert.assertEquals("Oscar",removeAsRemoveLast);
        Assert.assertEquals(2,stringTest1.size());
        Assert.assertEquals("Kevin",stringTest1.getFirst());
        Assert.assertEquals("Michael",stringTest1.getLast());
        String removeLast2 = stringTest1.removeLast();
        Assert.assertEquals("Michael",removeLast2);
        // After we've played with the array and removed and added and all sorts of stuff, will the first element get set as the head?
        Assert.assertEquals("Kevin",stringTest1.getLast()); // Yes, yes it does
        Assert.assertEquals(1,stringTest1.size());
        // Add some more stuff, check values again
        stringTest1.addFirst("Pam"); // Make sure addFirst will push the only value into tail, not keep it as the head or something weird
        Assert.assertEquals("Pam",stringTest1.getFirst());
        Assert.assertEquals("Kevin",stringTest1.getLast());
        Assert.assertEquals(2,stringTest1.size());

        // Test to make sure the clear method works
        stringTest1.clear();
        try{
            stringTest1.getFirst();
        }catch(NoSuchElementException e){
            System.out.println("PASSED Array Clear Test - No first element");
        }
        try{
            stringTest1.getLast();
        }catch(NoSuchElementException e){
            System.out.println("PASSED Array Clear Test - No last element");
        }
        try{
            stringTest1.get(0);
        }catch(NoSuchElementException e){
            System.out.println("PASSED Array Clear Test - Can't get(0)");
        }
        Assert.assertEquals(0,stringTest1.size());
        Assert.assertEquals(true,stringTest1.isEmpty());

        // Test the iterator
        stringTest1.addFirst("Pam");
        stringTest1.add(1,"Jim"); // Should tack it on the end
        Assert.assertEquals("Jim",stringTest1.getLast());
        stringTest1.add(2,"Michael");
        stringTest1.add(3,"Dwight");
        stringTest1.add(4,"Kevin"); // {Pam, Jim, Michael, Dwight, Kevin}
        Assert.assertEquals(5,stringTest1.size());

        Iterator<String> stringIterator = stringTest1.iterator();

        Assert.assertEquals(true,stringIterator.hasNext());
        Assert.assertEquals("Pam",stringIterator.next());
        Assert.assertEquals(true,stringIterator.hasNext());
        Assert.assertEquals("Jim",stringIterator.next());
        Assert.assertEquals(true,stringIterator.hasNext());
        Assert.assertEquals("Michael",stringIterator.next());
        Assert.assertEquals(true,stringIterator.hasNext());
        stringIterator.remove(); // {Pam, Jim, Dwight, Kevin}
        Assert.assertEquals(4,stringTest1.size());
        try{
            stringIterator.remove();
        }catch(IllegalStateException e){
            System.out.println("PASSED Trying to Iterator remove when not allowed");
        }
        Assert.assertEquals("Dwight",stringIterator.next());
        Assert.assertEquals(true,stringIterator.hasNext());
        stringIterator.remove(); // {Pam, Jim, Kevin}
        Assert.assertEquals(3,stringTest1.size());
        Assert.assertEquals("Kevin",stringIterator.next());
        Assert.assertEquals(false,stringIterator.hasNext());

        // It all works!! Huzzah!!
        stringTest1.clear();


        // >--- Test for Integers ---<

        DoublyLinkedList<Integer> intTest = new DoublyLinkedList<>();

        // Adding tests
        intTest.add(0,7); // {7} -> adding test when there are no values => should set both the head and tail
        Assert.assertEquals(intTest.getLast(),intTest.getFirst());
        Assert.assertEquals((Integer)7,intTest.getFirst());
        intTest.addLast(5); // {7,5}
        intTest.addFirst(3); // {3,7,5}
        Assert.assertEquals(intTest.get(0),intTest.getFirst());
        Assert.assertEquals(intTest.get(0),(Integer)3);
        Assert.assertEquals(intTest.get(2),intTest.getLast());
        Assert.assertEquals(intTest.get(2),(Integer)5);
        Assert.assertEquals(intTest.get(1),(Integer)7);
        intTest.addLast(3); // {3,7,5,3}
        intTest.addLast(9); // {3,7,5,3,9}
        Assert.assertEquals(0,intTest.indexOf(3));
        Assert.assertEquals(3,intTest.lastIndexOf(3));
        Assert.assertEquals(intTest.indexOf(5),intTest.lastIndexOf(5));
        Assert.assertEquals(2,intTest.indexOf(5));
        Assert.assertEquals(false,intTest.isEmpty());
        Assert.assertEquals(5,intTest.size());

        // Remove tests
        Assert.assertEquals((Integer)3,intTest.remove(3)); // {3,7,5,9}
        Assert.assertEquals(4,intTest.size());
        Assert.assertEquals((Integer)3,intTest.removeFirst()); // {7,5,9}
        Assert.assertEquals(3,intTest.size());
        Assert.assertEquals((Integer)9,intTest.removeLast()); // {7,5}
        Assert.assertEquals(2,intTest.size());
        Assert.assertEquals((Integer)7,intTest.getFirst());
        Assert.assertEquals((Integer)5,intTest.getLast());

        // It all works!!
        intTest.clear();
        Assert.assertEquals(true,intTest.isEmpty());

        // >--- Test for Custom Objects ---<

        DoublyLinkedList<Pokemon> pokedex = new DoublyLinkedList<>();

        Pokemon pikachu = new Pokemon("Pikachu",25);
        Pokemon charmander = new Pokemon("Charmander",4);
        Pokemon mewtwo = new Pokemon("Mewtwo",150);
        Pokemon mew = new Pokemon("Mew",151);
        Pokemon eternatus = new Pokemon("Eternatus",400);
        Pokemon bulbasaur = new Pokemon("Bulbasaur",1);

        // Single element
        pokedex.addLast(pikachu); // Should set both the head and tail even though there's no value in there
        Assert.assertEquals("Pikachu",pokedex.getFirst().getName());
        Assert.assertEquals(25,pokedex.getFirst().getIdNumber());
        Assert.assertEquals(pokedex.getLast().getName(),pokedex.getFirst().getName());
        Assert.assertEquals(pokedex.getLast().getIdNumber(),pokedex.getFirst().getIdNumber());

        // Two elements - check with index, indexOf, getFirst, and getLast
        pokedex.addLast(bulbasaur);
        Assert.assertEquals("Bulbasaur",pokedex.getLast().getName());
        Assert.assertEquals(1,pokedex.getLast().getIdNumber());
        Assert.assertEquals(0,pokedex.indexOf(pikachu));
        Assert.assertEquals(1,pokedex.indexOf(bulbasaur));
        Assert.assertEquals(0,pokedex.lastIndexOf(pikachu));
        Assert.assertEquals(1,pokedex.lastIndexOf(bulbasaur));
        Assert.assertEquals(-1,pokedex.indexOf(charmander));
        Assert.assertEquals(-1,pokedex.lastIndexOf(charmander));
        Assert.assertEquals("Pikachu",pokedex.getFirst().getName());
        Assert.assertEquals(25,pokedex.getFirst().getIdNumber());

        // Multiple elements
        pokedex.add(1,mewtwo); // {Pikachu, Mewtwo, Bulbasaur}
        pokedex.add(1,mew); // {Pikachu, Mew, Mewtwo, Bulbasaur}
        pokedex.add(2,eternatus); // {Pikachu, Mew, Eternatus, Mewtwo, Bulbasaur}
        Assert.assertEquals(eternatus,pokedex.get(2));
        Assert.assertEquals(mew,pokedex.get(1));
        Assert.assertEquals(mewtwo,pokedex.get(3));

        // Check toArray
        Pokemon[] pokeArray = {pikachu, mew, eternatus, mewtwo, bulbasaur};
        Object[] pokeToArray = pokedex.toArray();
        for(int x = 0; x < pokeArray.length; x++){
            Assert.assertEquals(pokeArray[x], pokeToArray[x]);
            Assert.assertEquals(pokeArray[x], pokeToArray[x]);
        }

        // isEmpty and size, along with removeFirst, remove, and removeLast
        Assert.assertEquals(pikachu,pokedex.removeFirst());
        Assert.assertEquals(mew,pokedex.removeFirst());
        Assert.assertEquals(false,pokedex.isEmpty());
        Assert.assertEquals(3,pokedex.size());
        Assert.assertEquals(mewtwo,pokedex.remove(1));
        Assert.assertEquals(eternatus,pokedex.getFirst());
        Assert.assertEquals(bulbasaur,pokedex.getLast());
        Assert.assertEquals(bulbasaur,pokedex.removeLast());
        Assert.assertEquals(pokedex.getLast(),pokedex.getFirst());

        // Everything works! Huzzah for custom classes!
        pokedex.clear();
        Assert.assertEquals(true, pokedex.isEmpty());

        // >--- Test for general Objects ---<
        // This, in general, is bad practice but given that it's a generic it should still work

        DoublyLinkedList<Object> objectTest = new DoublyLinkedList<>();

        objectTest.addFirst(charmander);
        objectTest.add(1,(Integer)7);
        objectTest.add(2,"Jim");
        objectTest.add(3,true);
        objectTest.addLast('q');
        // Resulting mixed-object matrix  = {Charmander, 7, "Jim", true, 'q'};

        // indexOf, lastIndexOf, get, getFirst, getLast
        Assert.assertEquals(charmander,objectTest.getFirst());
        Assert.assertEquals((Integer)7,objectTest.get(1));
        Assert.assertEquals("Jim",objectTest.get(2));
        Assert.assertEquals(true,objectTest.get(3));
        Assert.assertEquals('q',objectTest.getLast());
        Assert.assertEquals(objectTest.indexOf(charmander),objectTest.lastIndexOf(charmander));
        Assert.assertEquals(charmander,objectTest.getFirst());
        Assert.assertEquals(objectTest.getFirst(),objectTest.get(0));
        Assert.assertEquals(objectTest.indexOf(7),objectTest.lastIndexOf(7));
        Assert.assertEquals((Integer)7,objectTest.get(1));
        Assert.assertEquals(objectTest.indexOf("Jim"),objectTest.lastIndexOf("Jim"));
        Assert.assertEquals("Jim",objectTest.get(2));
        Assert.assertEquals(objectTest.indexOf(true),objectTest.lastIndexOf(true));
        Assert.assertEquals(true,objectTest.get(3));
        Assert.assertEquals(objectTest.indexOf('q'),objectTest.lastIndexOf('q'));
        Assert.assertEquals('q',objectTest.get(4));
        Assert.assertEquals('q',objectTest.getLast());

        // Removing tests
        Assert.assertEquals(7,objectTest.remove(1));
        Assert.assertEquals(charmander,objectTest.removeFirst());
        Assert.assertEquals('q',objectTest.getLast());
        Assert.assertEquals(3,objectTest.size());
        Assert.assertEquals(false,objectTest.isEmpty());
        Assert.assertEquals("Jim",objectTest.getFirst());
        Assert.assertEquals('q',objectTest.getLast());

        // It all works! Yippee!!
        // Thorough testing is rough, but boy does it make sure that things function as they should

        System.out.println("");
        System.out.println("All tests passed");
    }
}
