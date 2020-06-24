package assignment02;

import java.util.GregorianCalendar;

public class LibraryBook extends Book{

	private String holderName;
	private GregorianCalendar dueDate;
	
	
	/*
	 * LibraryBook creates a Book object with an ISBN number, author, and title
	 * 
	 * @param long isbn, String author, String title
	 * @return void
	 */
	public LibraryBook(long isbn, String author, String title) {
		super(isbn, author, title);
		holderName = null;
		dueDate = null;
	}
	
	/*
	 *  getHolder returns the holder either by String or PhoneNumber
	 *  
	 *  @param none
	 *  @return String or PhoneNumber
	 */
	
	public String getHolder() {
		return holderName;
	}
	
	/*
	 *  getDueDate returns the GregorianCalendar representation of the due date
	 *  
	 *  @param none
	 *  @return GregorianCalendar
	 */
	
	public GregorianCalendar getDueDate() {
		return dueDate;
	}
	
	/*
	 * checkBookIn sets the member values of holder and dueDate to null
	 * 
	 * @param none
	 * @return void
	 */
	
	public void checkBookIn() {
		holderName = null;
		dueDate = null;
	}
	
	/*
	 * checkBookOut sets the member values of the holder and dueDate
	 * 				
	 * @param holder, month, day, year
	 * @return void
	 */
	
	public void checkBookOut(String holder, int month, int day, int year) {
		holderName = holder;
		dueDate = new GregorianCalendar(year, month, day);
	}
	
	
	
	
}
