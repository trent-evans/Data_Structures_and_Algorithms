package assignment02;

import java.util.ArrayList;

import java.util.GregorianCalendar;

/**
 * Testing class for LibraryGeneric.
 *
 */
public class LibraryGenericTest {

  public static void main(String[] args) {

	    long book1isbn = 9781843190004L;
	    long book2isbn = 9781843190011L;
	    long book3isbn = 9781843190028L;
	    long book4isbn = 9781843190042L;
	    long book5isbn = 9781843193319L;
	    long book6isbn = 9781843190677L;
	    long book7isbn = 9781843190394L;
	    long book8isbn = 9781843192701L;
	    long book9isbn = 9781843190479L;
	  
	  
	  
    // test a library that uses names (String) to id patrons
    LibraryGeneric<String> lib1 = new LibraryGeneric<String>();
    lib1.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
    lib1.add(9780330351690L, "Jon Krakauer", "Into the Wild");
    lib1.add(9780446580342L, "David Baldacci", "Simple Genius");

    String patronString = "Jane Doe";

    if (!lib1.checkout(9780330351690L, patronString, 1, 1, 2008))
      System.err.println("TEST FAILED: first checkout");
    if (!lib1.checkout(9780374292799L, patronString, 1, 1, 2008))
      System.err.println("TEST FAILED: second checkout");
    ArrayList<LibraryBookGeneric<String>> booksCheckedOut1 = lib1.lookup(patronString);
    if (booksCheckedOut1 == null || booksCheckedOut1.size() != 2
        || !booksCheckedOut1.contains(new Book(9780330351690L, "Jon Krakauer", "Into the Wild"))
        || !booksCheckedOut1.contains(new Book(9780374292799L, "Thomas L. Friedman", "The World is Flat"))
        || !booksCheckedOut1.get(0).getHolder().equals(patronString)
        || !booksCheckedOut1.get(0).getDueDate().equals(new GregorianCalendar(2008, 1, 1))
        || !booksCheckedOut1.get(1).getHolder().equals(patronString)
        || !booksCheckedOut1.get(1).getDueDate().equals(new GregorianCalendar(2008, 1, 1)))
      System.err.println("TEST FAILED: lookup(holder)");
    if (!lib1.checkin(patronString)) // Books have been checked in by patron name
      System.err.println("TEST FAILED: checkin(holder)");
    
    lib1.addAll("Mushroom_Publishing.txt");
    
    
    if(!lib1.checkout(book1isbn, "Trent Evans", 11, 1, 2019)) {
    	System.err.println("TEST FAILED: first checkout - new patron");
    }
    if(!lib1.checkout(book2isbn, "Trent Evans", 11, 1, 2019)) {
    	System.err.println("TEST FAILED: second checkout - new patron");
    }
    if(!lib1.checkout(book3isbn, "Trent Evans", 11, 15, 2019)) {
    	System.err.println("TEST FAILED: third checkout - new patron");
    }
    
    ArrayList<LibraryBookGeneric<String>> booksCheckedOutString2 = lib1.lookup("Trent Evans");
    if(booksCheckedOutString2.size() != 3) {
    	System.err.println("TEST FAILED: create checkout list for Trent Evans via lookup");
    }
    
    // REMEMBER - The array size will not change.  However, the holder and due date values will change
    
    if(lib1.checkin(patronString)) {
    	System.err.println("TEST FAILED: check in for a holder that already has books checked in - should return false");
    }
    
    if(		   booksCheckedOut1.size() != 2
    		|| booksCheckedOut1.get(0).getHolder() != null
    		|| booksCheckedOut1.get(0).getDueDate() != null
    		|| booksCheckedOut1.get(1).getHolder() != null
    		|| booksCheckedOut1.get(1).getDueDate() != null
    		) {
    	System.err.println("TEST FAILED: checkin verification for Jane Doe - Holders & Due Dates not set to null");
    }
    
    if(!lib1.lookup(book1isbn).equals("Trent Evans")){
    	System.err.println("TEST FAILED: lookup - holder via ISBN");
    }
    if(lib1.lookup(book5isbn) != null) {
    	System.err.println("TEST FAILED: lookup - holder via ISBN when book has never been checked out");
    }
    if(!lib1.checkin(book1isbn)) {
    	System.err.println("TEST FAILED: check in via isbn - isbn 1 failed");
    }
    if(!lib1.checkin(book3isbn)) {
    	System.err.println("TEST FAILED: check in via isbn - isbn 3 failed");
    }
    if(lib1.lookup(book1isbn) != null) {
    	System.err.println("TEST FAILED: lookup - holder via ISBN after book has been turned in");
    }
    
    
    if(		   booksCheckedOutString2.size() != 3
    		|| booksCheckedOutString2.get(0).getHolder() != null
    		|| booksCheckedOutString2.get(0).getDueDate() != null
    		|| !booksCheckedOutString2.get(0).getAuthor().equals("Moyra Caldecott")
    		|| !booksCheckedOutString2.get(0).getTitle().equals("Weapons of the Wolfhound")
    		|| booksCheckedOutString2.get(0).getIsbn() != book1isbn
    		|| booksCheckedOutString2.get(1).getHolder() != "Trent Evans"
    		|| !booksCheckedOutString2.get(1).getDueDate().equals(new GregorianCalendar(2019,11,1))
    		|| !booksCheckedOutString2.get(1).getAuthor().equals("Moyra Caldecott")
    		|| !booksCheckedOutString2.get(1).getTitle().equals("The Eye of Callanish")
    		|| booksCheckedOutString2.get(1).getIsbn() != book2isbn
    		|| booksCheckedOutString2.get(2).getHolder() != null
    		|| booksCheckedOutString2.get(2).getDueDate() != null
    		) {
    	System.err.println("TEST FAILED - check in double check - make sure those checked in no longer have holders/due dates");
    	System.err.println("ALSO - verifying other book information such as author, title, and isbn");
    }
    
    	/** Phone Number Testing **/
    // test a library that uses phone numbers (PhoneNumber) to id patrons
    LibraryGeneric<PhoneNumber> lib2 = new LibraryGeneric<PhoneNumber>();
    lib2.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
    lib2.add(9780330351690L, "Jon Krakauer", "Into the Wild");
    lib2.add(9780446580342L, "David Baldacci", "Simple Genius");

    PhoneNumber patronPhone = new PhoneNumber("801.555.1234");

    if (!lib2.checkout(9780330351690L, patronPhone, 1, 1, 2008))
      System.err.println("TEST FAILED: first checkout");
    if (!lib2.checkout(9780374292799L, patronPhone, 1, 1, 2008))
      System.err.println("TEST FAILED: second checkout");
    ArrayList<LibraryBookGeneric<PhoneNumber>> booksCheckedOut2 = lib2.lookup(patronPhone);
    if (booksCheckedOut2 == null || booksCheckedOut2.size() != 2
        || !booksCheckedOut2.contains(new Book(9780330351690L, "Jon Krakauer", "Into the Wild"))
        || !booksCheckedOut2.contains(new Book(9780374292799L, "Thomas L. Friedman", "The World is Flat"))
        || !booksCheckedOut2.get(0).getHolder().equals(patronPhone)
        || !booksCheckedOut2.get(0).getDueDate().equals(new GregorianCalendar(2008, 1, 1))
        || !booksCheckedOut2.get(1).getHolder().equals(patronPhone)
        || !booksCheckedOut2.get(1).getDueDate().equals(new GregorianCalendar(2008, 1, 1)))
      System.err.println("TEST FAILED: lookup(holder)");
    if (!lib2.checkin(patronPhone))
      System.err.println("TEST FAILED: checkin(holder)");
    
    lib2.addAll("Mushroom_Publishing.txt");
    
    PhoneNumber patronPhone2 = new PhoneNumber("801.123.4567");
    
    if(!lib2.checkout(book1isbn, patronPhone2, 11, 1, 2019)) {
    	System.err.println("TEST FAILED - phone number checkout: new patron");
    }
    if(!lib2.checkout(book2isbn, patronPhone2, 11, 2, 2019)) {
    	System.err.println("TEST FAILED - phone number checkout: new patron 2");
    }
    lib2.checkout(book3isbn, patronPhone2, 11, 3, 2019);
    if(lib2.checkout(book3isbn, patronPhone2, 11, 3, 2019)) {
    	System.err.println("TEST FAILED - phone number chekout: new patron - book already checked out");
    }
    if(!lib2.lookup(book3isbn).equals(patronPhone2)) {
    	System.err.println("TEST FAILED - lookup ISBN: phone numbers are not equivalent");
    }
    if(lib2.lookup(book5isbn) != null) {
    	System.err.println("TEST FAILED - lookup ISBN: lookup isbn that has not been checked out");
    }
    ArrayList<LibraryBookGeneric<PhoneNumber>> booksCheckedOutPhoneNumber2 = lib2.lookup(patronPhone2);
    if(booksCheckedOutPhoneNumber2.size() != 3) {
    	System.err.println("TEST FAILED - lookup PhoneNumber: ArrayList size is incorrect");
    }
    if(		   !booksCheckedOutPhoneNumber2.get(0).getHolder().equals(patronPhone2)
    		|| !booksCheckedOutPhoneNumber2.get(0).getDueDate().equals(new GregorianCalendar(2019,11,1))
    		|| !booksCheckedOutPhoneNumber2.get(0).getAuthor().equals("Moyra Caldecott")
    		|| !booksCheckedOutPhoneNumber2.get(0).getTitle().equals("Weapons of the Wolfhound")
    		|| booksCheckedOutPhoneNumber2.get(0).getIsbn() != book1isbn
    		
    		) {
    	System.err.println("TEST FAILED - PhoneNumber: Holder, due date, other info search turned out wrong for book 1");
    }
  
	  if(	   !booksCheckedOutPhoneNumber2.get(1).getHolder().equals(patronPhone2)
	  		|| !booksCheckedOutPhoneNumber2.get(1).getDueDate().equals(new GregorianCalendar(2019,11,2))
	  		|| !booksCheckedOutPhoneNumber2.get(1).getAuthor().equals("Moyra Caldecott")
	  		|| !booksCheckedOutPhoneNumber2.get(1).getTitle().equals("The Eye of Callanish")
	  		|| booksCheckedOutPhoneNumber2.get(1).getIsbn() != book2isbn
	  		
	  		) {
	  	System.err.println("TEST FAILED - PhoneNumber: Holder, due date, other info search turned out wrong for book 2");
	  }
	  if(!lib2.checkin(book2isbn)){
		  System.err.println("TEST FAILED - check in by ISBN");
	  }
	  if(	       booksCheckedOutPhoneNumber2.get(1).getHolder() != null
		  		|| booksCheckedOutPhoneNumber2.get(1).getDueDate() != null
		  		|| !booksCheckedOutPhoneNumber2.get(1).getAuthor().equals("Moyra Caldecott")
		  		|| !booksCheckedOutPhoneNumber2.get(1).getTitle().equals("The Eye of Callanish")
		  		|| booksCheckedOutPhoneNumber2.get(1).getIsbn() != book2isbn
		  		
		  		) {
		  	System.err.println("TEST FAILED - PhoneNumber: Holder, due date, other info search turned out wrong for book 2 after checked in");
		  }
	  if(!lib2.checkin(patronPhone2)) {
		  System.err.println("TEST FAILED - check in by PhoneNumber after a single ISBN check in");
	  }
	  if(	   booksCheckedOutPhoneNumber2.get(0).getHolder() != null
	  		|| booksCheckedOutPhoneNumber2.get(0).getDueDate() != null
	  		|| !booksCheckedOutPhoneNumber2.get(0).getAuthor().equals("Moyra Caldecott")
	  		|| !booksCheckedOutPhoneNumber2.get(0).getTitle().equals("Weapons of the Wolfhound")
	  		|| booksCheckedOutPhoneNumber2.get(0).getIsbn() != book1isbn
	  		
	  		) {
	  	System.err.println("TEST FAILED - PhoneNumber: Holder, due date, other info search turned out wrong for book 1");
	  }
	    
	  // *** Sorting Tests ***
	  
	  /*
	   * I do a lot of for loops in the next blocks of code
	   * My purpose behind this is to try and both automate and improve my testing
	   * My reasoning is that if the arrays are sorted, we should be able to expect the values in a certain way
	   * Because of that, I can create a for loop that can literally check the entire array way faster than I can type them out by hand
	   * This way, I can make sure that every single point is correct without having to write 1000 lines of tests
	   */
	  
	  // Sorting with Strings
	  LibraryGeneric<String> libSortString = new LibraryGeneric<String>();
	  libSortString.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
	  libSortString.add(9780330351690L, "Jon Krakauer", "Into the Wild");
	  libSortString.add(9780446580342L, "David Baldacci", "Simple Genius");
	  
	  ArrayList<LibraryBookGeneric<String>> inventorySmallStringLib = libSortString.getInventoryList();
	  
	  if(		 !inventorySmallStringLib.get(1).getAuthor().equals("Thomas L. Friedman")
			  || !inventorySmallStringLib.get(1).getTitle().equals("The World is Flat")
			  || inventorySmallStringLib.get(1).getIsbn() != 9780374292799L
			  || inventorySmallStringLib.get(1).getDueDate() != null
			  || inventorySmallStringLib.get(1).getHolder() != null
			  
			  ) {
		  System.err.println("TEST FAILURE - Inventory List: second book String");
		 
	  }
	  // Check to make sure the ISBNs are in order
	  for(int x = 0; x < inventorySmallStringLib.size()-1; x++) {
		  if(inventorySmallStringLib.get(x).getIsbn() > inventorySmallStringLib.get(x+1).getIsbn()) {
			  System.err.println("TEST FAILURE - String Inventory List: ISBN Comparator " + x + " ISBN is greater than " + (x+1));
		  }
	  }
	
	  ArrayList<LibraryBookGeneric<String>> authorSmallStringLib = libSortString.getOrderedByAuthor();
	  
	  for(int x = 0; x < authorSmallStringLib.size()-1; x++) { // No matching authors here, will test in the larger library
		  if(authorSmallStringLib.get(x).getAuthor().compareTo(authorSmallStringLib.get(x+1).getAuthor()) > 0){
			  System.err.println("TEST FAILURE - String Automated ordering by author " + x + " & " + (x+1) + "out of order");
		  }
	  }
	  
	  libSortString.addAll("Mushroom_Publishing.txt");
	  
	  ArrayList<LibraryBookGeneric<String>> inventoryMediumStringLib = libSortString.getInventoryList();
	  
	  for(int x = 0; x < inventoryMediumStringLib.size()-1; x++) {
		  if(inventoryMediumStringLib.get(x).getIsbn() > inventoryMediumStringLib.get(x+1).getIsbn()) {
			  System.err.println("TEST FAILURE - String Inventory List Medium Library: ISBN Index " + x + " & " + (x+1));
		  }
	  }
	  
	  ArrayList<LibraryBookGeneric<String>> authorMediumStringLib = libSortString.getOrderedByAuthor();
	  
	  for(int x = 0; x < authorMediumStringLib.size()-1; x++) {
		  int compareValue = authorMediumStringLib.get(x).getAuthor().compareTo(authorMediumStringLib.get(x+1).getAuthor());
		  if(compareValue == 0) { // If authors are the same
			  if(authorMediumStringLib.get(x).getTitle().compareTo(authorMediumStringLib.get(x+1).getTitle()) > 0) {
				  System.err.println("TEST FAILURE - String Author Medium Library same author: Author Index " + x + " & " + (x+1));
			  }
		  }else if(compareValue > 0) {
			  System.err.println("TEST FAILURE - String Author Medium Library error: Author Index " + x + " & " + (x+1));
		  }
	  }
	  
	  // Overdue dates
	  
	  libSortString.checkout(book1isbn, "Trent Evans", 11, 1, 2019); // Should trigger
	  libSortString.checkout(book3isbn, "Trent Evans", 11, 5, 2019); 
	  libSortString.checkout(book4isbn, "Eric Stout", 11, 2, 2019); // Should not trigger overdue - edge case
	  libSortString.checkout(book5isbn, "Eric Stout", 11, 4, 2019);
	  libSortString.checkout(book7isbn, "Trent Evans", 10, 31, 2019); // Should trigger
	  libSortString.checkout(book8isbn, "Eric Stout", 10, 30, 2019); // Should trigger
	  libSortString.checkout(book9isbn, "Eric Stout", 10, 31, 2019); // Should trigger
	  
	  ArrayList<LibraryBookGeneric<String>> overdueMediumStringLib = libSortString.getOverdueList(11, 2, 2019);
	  GregorianCalendar overdueStringDate = new GregorianCalendar(2019,11,2);
	  
	  if(overdueMediumStringLib.size() != 4) {
		  System.err.println("TEST FAILURE - String overdue array: array is wrong size");
	  }
	  
	  for(int x = 0; x < overdueMediumStringLib.size()-1; x++) { // Make sure dates are sorted
		  if(overdueMediumStringLib.get(x).getDueDate().compareTo(overdueMediumStringLib.get(x+1).getDueDate()) > 0) {
			  System.err.println("TEST FAILURE - String overdue array: dates are not less than the due date");
		  }
	  }

	  // Check to make sure everything is before the due date
	  for(int x = 0; x < overdueMediumStringLib.size(); x++){
	  	if(overdueMediumStringLib.get(x).getDueDate().compareTo(overdueStringDate) >= 0){
	  		System.err.println("TEST FAILURE - String overdue array: due date is past or equal to the due date NOT before it");
		}
	  }

	  ArrayList<LibraryBookGeneric<String>> overdueMediumStringLibEmpty = libSortString.getOverdueList(11, 2, 2018);
	  
	  if(!overdueMediumStringLibEmpty.isEmpty()) {
		  System.err.println("TEST FAILURE - Empty String overdue array: the array is not empty");
	  }
	  
	  
	  // Sorting with PhoneNumbers
	  LibraryGeneric<PhoneNumber> libSortPhone = new LibraryGeneric<PhoneNumber>();
	  libSortPhone.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
	  libSortPhone.add(9780330351690L, "Jon Krakauer", "Into the Wild");
	  libSortPhone.add(9780446580342L, "David Baldacci", "Simple Genius");
	  
	  ArrayList<LibraryBookGeneric<PhoneNumber>> inventorySmallPhoneLib = libSortPhone.getInventoryList();
	  
	  if(		 !inventorySmallPhoneLib.get(1).getAuthor().equals("Thomas L. Friedman")
			  || !inventorySmallPhoneLib.get(1).getTitle().equals("The World is Flat")
			  || inventorySmallPhoneLib.get(1).getIsbn() != 9780374292799L
			  || inventorySmallPhoneLib.get(1).getDueDate() != null
			  || inventorySmallPhoneLib.get(1).getHolder() != null
			  
			  ) {
		  System.err.println("TEST FAILURE - Inventory List: second book Phone Number");
	  }
	  // Check to make sure that the ISBNs are in order
	  for(int x = 0; x < inventorySmallPhoneLib.size()-1; x++) {
		  if(inventorySmallPhoneLib.get(x).getIsbn() > inventorySmallPhoneLib.get(x+1).getIsbn()) {
			  System.err.println("TEST FAILURE - Phone Inventory List: ISBN Comparator " + x + " ISBN is greater than " + (x+1));
		  }
	  }
	  
	  ArrayList<LibraryBookGeneric<PhoneNumber>> authorSmallPhoneLib = libSortPhone.getOrderedByAuthor();
	  
	  for(int x = 0; x < authorSmallPhoneLib.size()-1; x++) {
		  if (authorSmallPhoneLib.get(x).getAuthor().compareTo(authorSmallPhoneLib.get(x + 1).getAuthor()) > 0) {
			  System.err.println("TEST FAILURE - Phone Automated ordering by author " + x + " & " + (x + 1) + "out of order");
		  }
	  }
	 
	  libSortPhone.addAll("Mushroom_Publishing.txt");
	  
	  ArrayList<LibraryBookGeneric<PhoneNumber>> inventoryMediumPhoneLib = libSortPhone.getInventoryList();
	  
	  for(int x = 0; x < inventoryMediumPhoneLib.size()-1; x++) {
		  if(inventoryMediumPhoneLib.get(x).getIsbn() > inventoryMediumPhoneLib.get(x+1).getIsbn()) {
			  System.err.println("TEST FAILURE - Phone Inventory List Medium Library: ISBN Index " + x + " & " + (x+1));
		  }
	  }
	  
	  ArrayList<LibraryBookGeneric<PhoneNumber>> authorMediumPhoneLib = libSortPhone.getOrderedByAuthor();
	  
	  for(int x = 0; x < authorMediumPhoneLib.size()-1; x++) {
		  int compareValue = authorMediumPhoneLib.get(x).getAuthor().compareTo(authorMediumPhoneLib.get(x+1).getAuthor());
		  if(compareValue == 0) { // If authors are the same
			  if(authorMediumPhoneLib.get(x).getTitle().compareTo(authorMediumPhoneLib.get(x+1).getTitle()) > 0) {
				  System.err.println("TEST FAILURE - Phone Author Medium Library same author: Author Index " + x + " & " + (x+1));
			  }
		  }else if(compareValue > 0) {
			  System.err.println("TEST FAILURE - Phone Author Medium Library error: Author Index " + x + " & " + (x+1));
		  }
	  }
	  
	  // Overdue stuff
	  PhoneNumber patronTest1 = new PhoneNumber("801.123.5678");
	  PhoneNumber patronTest2 = new PhoneNumber("801.765.4321");
	  
	  libSortPhone.checkout(book1isbn, patronTest1, 11, 1, 2019); // Should trigger
	  libSortPhone.checkout(book2isbn, patronTest1, 11, 3, 2019); 
	  libSortPhone.checkout(book3isbn, patronTest1, 11, 5, 2019); 
	  libSortPhone.checkout(book4isbn, patronTest2, 11, 2, 2019); // Should not trigger overdue - edge case
	  libSortPhone.checkout(book5isbn, patronTest2, 11, 4, 2019); 
	  libSortPhone.checkout(book6isbn, patronTest2, 11, 6, 2019);
	  libSortPhone.checkout(book7isbn, patronTest1, 10, 31, 2019); // Should trigger
	  libSortPhone.checkout(book8isbn, patronTest2, 10, 30, 2019); // Should trigger

	  ArrayList<LibraryBookGeneric<PhoneNumber>> overdueMediumPhoneLib = libSortPhone.getOverdueList(11, 2, 2019);
	  GregorianCalendar overduePhoneDate = new GregorianCalendar(2019,11,2);

	  if(overdueMediumPhoneLib.size() != 3) {
		  System.err.println("TEST FAILURE - Phone overdue array: array is wrong size");
	  }

	  // Make sure the dates are sorted
	  for(int x = 0; x < overdueMediumPhoneLib.size()-1; x++) {
		  if(overdueMediumPhoneLib.get(x).getDueDate().compareTo(overdueMediumPhoneLib.get(x+1).getDueDate()) > 0) {
			  System.err.println("TEST FAILURE - Phone overdue array: dates are not less than the due date");
		  }
	  }

	  // Make sure dates are less than the due date
	  for(int x = 0; x < overdueMediumPhoneLib.size(); x++){
	  	if(overdueMediumPhoneLib.get(x).getDueDate().compareTo(overduePhoneDate) >= 0){
	  		System.err.println("TEST FAILURE - Phone overdue array: Due date is equal to or past the overdue date NOT before");
		}
	  }
	  
	  ArrayList<LibraryBookGeneric<PhoneNumber>> overdueMediumPhoneLibEmpty = libSortPhone.getOverdueList(11, 2, 2018);
	  
	  if(!overdueMediumPhoneLibEmpty.isEmpty()) {
		  System.err.println("TEST FAILURE - Empty Phone overdue array: due date array should be empty");
	  }
	  
	    System.out.println("Testing done.");
	  }

  }
