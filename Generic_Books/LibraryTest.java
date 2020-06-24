package assignment02;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * Testing class for Library.
 * 
 * 
 */
public class LibraryTest {

  public static void main(String[] args) {
    // test an empty library
    Library lib = new Library();

    if (lib.lookup(978037429279L) != null)
      System.err.println("TEST FAILED -- empty library: lookup(isbn)");
    ArrayList<LibraryBook> booksCheckedOut = lib.lookup("Jane Doe");
    if (booksCheckedOut == null || booksCheckedOut.size() != 0)
      System.err.println("TEST FAILED -- empty library: lookup(holder)");
    if (lib.checkout(978037429279L, "Jane Doe", 1, 1, 2008))
      System.err.println("TEST FAILED -- empty library: checkout");
    if (lib.checkin(978037429279L))
      System.err.println("TEST FAILED -- empty library: checkin(isbn)");
    if (lib.checkin("Jane Doe"))
      System.err.println("TEST FAILED -- empty library: checkin(holder)");

    // test a small library
    lib.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
    lib.add(9780330351690L, "Jon Krakauer", "Into the Wild");
    lib.add(9780446580342L, "David Baldacci", "Simple Genius");

    if (lib.lookup(9780330351690L) != null)
      System.err.println("TEST FAILED -- small library: lookup(isbn)");
    if (!lib.checkout(9780330351690L, "Jane Doe", 1, 1, 2008))
      System.err.println("TEST FAILED -- small library: checkout");
    booksCheckedOut = lib.lookup("Jane Doe");
    if (booksCheckedOut == null || booksCheckedOut.size() != 1
        || !booksCheckedOut.get(0).equals(new Book(9780330351690L, "Jon Krakauer", "Into the Wild"))
        || !booksCheckedOut.get(0).getHolder().equals("Jane Doe")
        || !booksCheckedOut.get(0).getDueDate().equals(new GregorianCalendar(2008, 1, 1)))
      System.err.println("TEST FAILED -- small library: lookup(holder)");
    if (!lib.checkin(9780330351690L))
      System.err.println("TEST FAILED -- small library: checkin(isbn)");
    if (lib.checkin("Jane Doe"))
      System.err.println("TEST FAILED -- small library: checkin(holder)");

    // test a medium library
    lib.addAll("Mushroom_Publishing.txt");

    // FILL IN
    
    long book1isbn = 9781843190004L;
    long book2isbn = 9781843190011L;
    long book3isbn = 9781843190028L;
    long book4isbn = 9781843190042L;
    long book5isbn = 9781843193319L;
    
    // Library book checkout testing
    if(!lib.checkout(book1isbn, "Trent Evans", 11, 16, 2019)) {
    	System.err.println("TEST FAILED - medium library: checkout 1");
    }
    if(!lib.checkout(book2isbn, "Trent Evans", 11, 17, 2019)) {
    	System.err.println("TEST FAILED - medium library: checkout 2");
    }
    if(!lib.checkout(book3isbn, "Eric Stout", 11, 1, 1993)) {
    	System.err.println("TEST FAILED - medium library: checkout new user");
    }
    if(!lib.checkout(book4isbn, "Ashley Wright", 11, 15, 2019)) {
    	System.err.println("TEST FAILED - medium library: checkout third user");
    }
    if(lib.checkout(book4isbn, "Trent Evans", 11, 16, 2019)) {
    	System.err.println("TEST FAILED - medium library: trying to checkout an already checked out book");
    }
    
    // Testing holder lookup with multiple different holders
    String holderLookupTest1 = lib.lookup(book1isbn);
    if(!holderLookupTest1.equals("Trent Evans")) {
    	System.err.println("TEST FAILED -- medium library: lookup(isbn)");
    }
    String holderLookupTest2 = lib.lookup(book3isbn);
    if(!holderLookupTest2.equals("Eric Stout")) {
    	System.err.println("TEST FAILED -- medium library: lookup(isbn) - new user");
    }
    String holderLookupTest3 = lib.lookup(book5isbn);
    if(holderLookupTest3 != null) {
    	System.err.println("TEST FAILED -- medium library: null lookup(isbn)");
    }
    String holderLookupTest4 = lib.lookup(book4isbn);
    if(!holderLookupTest4.equals("Ashley Wright")) {
    	System.err.println("TEST FAILED -- medium library: lookup(isbn) - another user");
    }
    
    // Testing book lookup with different holders
    ArrayList<LibraryBook> trentList = lib.lookup("Trent Evans");
    if(trentList.size() != 2) {
    	System.err.println("TEST FAILED - medium library: lookup(holder) - returned wrong size");
    }
    ArrayList<LibraryBook> aubriList = lib.lookup("Aubri Evans");
    if(!aubriList.isEmpty()) {
    	System.err.println("TEST FAILED - medium library: lookup(holder) - did not return empty array list");
    }
    ArrayList<LibraryBook> ericList = lib.lookup("Eric Stout");
    if(ericList.size() != 1) {
    	System.err.println("TEST FAILED - medium library: lookup(holder) - returned size != 1");
    }
    
    // Whole getting test suite for holder with multiple books
    if(		   !trentList.get(0).getHolder().equals("Trent Evans") 
    		|| !trentList.get(0).getAuthor().equals("Moyra Caldecott")
    		|| !trentList.get(0).getTitle().equals("Weapons of the Wolfhound")
    		|| !trentList.get(0).getDueDate().equals(new GregorianCalendar(2019,11,16))
    		|| trentList.get(0).getIsbn() != book1isbn
    		
    		|| !trentList.get(1).getHolder().equals("Trent Evans") 
    		|| !trentList.get(1).getAuthor().equals("Moyra Caldecott")
    		|| !trentList.get(1).getTitle().equals("The Eye of Callanish")
    		|| !trentList.get(1).getDueDate().equals(new GregorianCalendar(2019,11,17))
    		|| trentList.get(1).getIsbn() != book2isbn
    		) {
    	System.err.println("TEST FAILED - medium library: searching all relavent book & holder information for multiple book holder");
    	System.err.println("getHolder, getDueDate, getAuthor, getIsbn, getTitle");
    }
    
    // Getting test suite for holder with a single book
    if( 	   !ericList.get(0).getHolder().equals("Eric Stout")
    		|| !ericList.get(0).getAuthor().equals("Moyra Caldecott")
    		|| !ericList.get(0).getTitle().equals("Crystal Legends")
    		|| !ericList.get(0).getDueDate().equals(new GregorianCalendar(1993,11,1))
    		|| ericList.get(0).getIsbn() != book3isbn
    		) {
    	System.err.println("TEST FAILED - medium library: single book holder relavent information lookup");
    }
    
    // Testing book check in process
    lib.checkin(book3isbn);
    
    if( 	   ericList.get(0).getHolder() != null
    		|| !ericList.get(0).getAuthor().equals("Moyra Caldecott")
    		|| !ericList.get(0).getTitle().equals("Crystal Legends")
    		|| ericList.get(0).getDueDate()!= null
    		|| ericList.get(0).getIsbn() != book3isbn
    		) {
    	System.err.println("TEST FAILED - medium library: single book check in via ISBN");
    }
    
    lib.checkin("Trent Evans");
    
    if(		   trentList.get(0).getHolder() != null
    		|| !trentList.get(0).getAuthor().equals("Moyra Caldecott")
    		|| !trentList.get(0).getTitle().equals("Weapons of the Wolfhound")
    		|| trentList.get(0).getDueDate() != null
    		|| trentList.get(0).getIsbn() != book1isbn
    		
    		|| trentList.get(1).getHolder() != null
    		|| !trentList.get(1).getAuthor().equals("Moyra Caldecott")
    		|| !trentList.get(1).getTitle().equals("The Eye of Callanish")
    		|| trentList.get(1).getDueDate() != null
    		|| trentList.get(1).getIsbn() != book2isbn
    		) {
    	System.err.println("TEST FAILED - medium library: searching all relavent book & holder information for multiple book holder");
    	System.err.println("getHolder, getDueDate, getAuthor, getIsbn, getTitle");
    }
    
    if(lib.checkin("Trent Evans")) {
    	System.err.println("TEST FAILED - medium library: checking books in from a holder who recently returned all books");
    }
    if(lib.checkin("Aubri Evans")) {
    	System.err.println("TEST FAILED - medium library: checking books in from a holder who never checked books out");
    }
    
    // Testing the .equals method
    lib.checkout(book1isbn, "Ashley Wright", 1, 1, 2001);
    ArrayList<LibraryBook> ashleyList = lib.lookup("Ashley Wright");
    LibraryBook testBook1 = ashleyList.get(0);
    LibraryBook testBook2 = ashleyList.get(1);
    LibraryBook testBook3 = ashleyList.get(0);
    
    if(testBook1.equals(testBook2)) {
    	System.err.println("TEST FAILED - medium library: equals book comparison - books are not equal");
    }
    if(!testBook1.equals(testBook3)) {
    	System.err.println("TEST FAILED - medium library: equals book comparsion - books are equal");
    }
    String testString = "This is not a book";
    if(testBook1.equals(testString)) {
    	System.err.println("TEST FAILED - medium library: equals book comparison - book and string");
    }
    
    // Testing the toString method
    if(testBook1.toString().equals(testBook2.toString())) {
    	System.err.println("TEST FAILED - medium library: toString comparison - strings don't match");
    }
    if(!testBook1.toString().equals(testBook3.toString())) {
    	System.err.println("TEST FAILED - medium library: toString comparison - strings do match");
    }
    
    // Testing the Hashcode method
    
    if(testBook1.hashCode() == testBook2.hashCode()) {
    	System.err.println("TEST FAILED - medium library: hashCode comparison - hash codes don't match");
    }
    if(testBook1.hashCode() != testBook3.hashCode()) {
    	System.err.println("TEST FAILED - medium library: hashCode comparison - hash codes do match");
    }

    System.out.println("Testing done.");
  }

  /**
   * Returns a library of "dummy" books (random ISBN and placeholders for author
   * and title).
   * 
   * Useful for collecting running times for operations on libraries of varying
   * size.
   * 
   * @param size
   *          -- size of the library to be generated
   */
  public static ArrayList<LibraryBook> generateLibrary(int size) {
    ArrayList<LibraryBook> result = new ArrayList<LibraryBook>();

    for (int i = 0; i < size; i++) {
      // generate random ISBN
      Random randomNumGen = new Random();
      String isbn = "";
      for (int j = 0; j < 13; j++)
        isbn += randomNumGen.nextInt(10);

      result.add(new LibraryBook(Long.parseLong(isbn), "An author", "A title"));
    }

    return result;
  }

  /**
   * Returns a randomly-generated ISBN (a long with 13 digits).
   * 
   * Useful for collecting running times for operations on libraries of varying
   * size.
   */
  public static long generateIsbn() {
    Random randomNumGen = new Random();

    String isbn = "";
    for (int j = 0; j < 13; j++)
      isbn += randomNumGen.nextInt(10);

    return Long.parseLong(isbn);
  }
}
