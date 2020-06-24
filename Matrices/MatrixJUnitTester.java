/*
 * Here is a starting point for your Matrix tester. You will have to fill in the rest with
 * more code to sufficiently test your Matrix class. We will be using our own MatrixTester for grading. 
*/
package assignment01;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MatrixJUnitTester {

  Matrix threeByTwo, twoByThree, twoByTwoResult, twoByTwoEquals, twoByTwoFalse, twoByThreePlusTwoByThree;
  /* Initialize some matrices we can play with for every test! */

  @Before
  public void setup() {
    twoByThree = new Matrix(new int[][] { { 1, 2, 3 }, { 2, 5, 6 } });
    threeByTwo = new Matrix(new int[][] { { 4, 5 }, { 3, 2 }, { 1, 1 } });
    // this is the known correct result of multiplying M1 by M2
    twoByTwoResult = new Matrix(new int[][] { { 13, 12 }, { 29, 26 } });
    twoByTwoEquals = new Matrix(new int [][] { {13,12}, {29,26} });
    twoByTwoFalse = new Matrix(new int [][]  { {13,12}, {29,25} });
    twoByThreePlusTwoByThree = new Matrix(new int[][] { {2,4,6},{4,10,12}});
  }

  @Test
  public void twoByTwoEqualsTest() {
	  // Matrices are equal
	  Assert.assertEquals(true, twoByTwoResult.equals(twoByTwoEquals));
  }
  
  @Test
  public void twoByTwoNotEquals() {
	  // Matrices have the same dimensions but are not equal
	  Assert.assertEquals(false, twoByTwoResult.equals(twoByTwoFalse));
  }
  
  @Test
  public void differentRowsEquqlsTest() {
	  Assert.assertEquals(false, threeByTwo.equals(twoByTwoEquals));
  }
  
  @Test
  public void differentColumnsEqualsTest() {
	
	  Assert.assertEquals(false, twoByThree.equals(twoByTwoEquals));
  }
  
  @Test
  public void twoByTwoResultToString() {
	  String testAnswer = "13 12 \n29 26 \n";
	  Assert.assertEquals(testAnswer, twoByTwoResult.toString());
  }
  
  @Test
  public void threeByTwoToString() {
	  String testAnswer = "4 5 \n3 2 \n1 1 \n";
	  Assert.assertEquals(testAnswer, threeByTwo.toString());
  }
  
  @Test
  public void timesWithBalancedDimensions() {
    Matrix matrixProduct = twoByThree.times(threeByTwo);
    Assert.assertTrue(twoByTwoResult.equals(matrixProduct));
  }
  
  @Test
  public void timesWithUnbalancedDimensions() {
	  Assert.assertEquals(null, twoByThree.times(twoByThree));
  }
  
  @Test
  public void addWithBalancedDimensions() {
	  Assert.assertEquals(twoByThreePlusTwoByThree, twoByThree.plus(twoByThree));
  }
  
  @Test
  public void addWithUnbalancedDimensions() {
	  Assert.assertEquals(null, twoByThree.plus(twoByTwoResult));
  }
 
  
}
