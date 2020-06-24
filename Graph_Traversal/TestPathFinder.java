package assignment07;

import org.junit.Assert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InvalidObjectException;
import java.nio.file.Path;
import java.util.Scanner;

public class TestPathFinder {

  public static void main(String[] args) throws FileNotFoundException {

    /*
     * The basic structure for these tests is the following:
     * 1) Solve the maze
     * 2) Solve the maze backwards
     * 3) Assert that both solutions are equal
     * 4) Assert whether the solution and the original are equal or not
     *    - Because I have to test both solvable and unsolvable cases, sometimes I'll assert that they are the same and
     *      sometimes I'll assert that they are not
     */

    // Little maze test
    PathFinder.solveMaze("mazes/tinyMaze.txt", "mazeSols/tinyMazeOutput.txt");
    PathFinder.reverseSolveMaze("mazes/tinyMaze.txt", "mazeSols/tinyMazeOutputRev.txt");
    Assert.assertTrue(mazeCheck("mazeSols/tinyMazeOutput.txt","mazeSols/tinyMazeOutputRev.txt"));
    Assert.assertFalse(mazeCheck("mazes/tinyMaze.txt","mazeSols/tinyMazeOutput.txt"));

    // Straight maze test
    PathFinder.solveMaze("mazes/straight.txt","mazeSols/straightOutput.txt");
    PathFinder.reverseSolveMaze("mazes/straight.txt","mazeSols/straightOutputRev.txt");
    Assert.assertTrue(mazeCheck("mazeSols/straightOutput.txt","mazeSols/straightOutputRev.txt"));
    Assert.assertFalse(mazeCheck("mazes/straight.txt","mazeSols/straightOutput.txt"));

    // Really big maze test
    PathFinder.solveMaze("mazes/bigMaze.txt","mazeSols/bigMazeOutput.txt");
    PathFinder.reverseSolveMaze("mazes/bigMaze.txt","mazeSols/bigMazeOutputRev.txt");
    Assert.assertTrue(mazeCheck("mazeSols/bigMazeOutput.txt","mazeSols/bigMAzeOutputRev.txt"));
    Assert.assertFalse(mazeCheck("mazes/bigMaze.txt","mazeSols/bigMazeOutput.txt"));

    // Unsolvable maze test - tests to make sure that you can't access the goal diagonally
    PathFinder.solveMaze("mazes/goalDiagonal.txt","mazeSols/goalDiagonalOutput.txt");
    PathFinder.reverseSolveMaze("mazes/goalDiagonal.txt","mazeSols/goalDiagonalOutputRev.txt");
    Assert.assertTrue(mazeCheck("mazeSols/goalDiagonalOutput.txt","mazeSols/goalDiagonalOutputRev.txt"));
    Assert.assertTrue(mazeCheck("mazes/goalDiagonal.txt","mazeSols/goalDiagonalOutput.txt"));

    // Unsolvable maze test - same as above, but start and goal are flipped
    PathFinder.solveMaze("mazes/startDiagonal.txt","mazeSols/startDiagonalOutput.txt");
    PathFinder.reverseSolveMaze("mazes/startDiagonal.txt","mazeSols/startDiagonalOutputRev.txt");
    Assert.assertTrue(mazeCheck("mazeSols/startDiagonalOutput.txt","mazeSols/startDiagonalOutputRev.txt"));
    Assert.assertTrue(mazeCheck("mazes/startDiagonal.txt","mazeSols/startDiagonalOutputRev.txt"));

    // Larger Unsolvable maze
    PathFinder.solveMaze("mazes/unsolvable.txt","mazeSols/unsolvableOutput.txt");
    PathFinder.reverseSolveMaze("mazes/unsolvable.txt","mazeSols/unsolvableOutputRev.txt");
    Assert.assertTrue(mazeCheck("mazeSols/unsolvableOutput.txt","mazeSols/unsolvableOutputRev.txt"));
    Assert.assertTrue(mazeCheck("mazes/unsolvable.txt","mazeSols/unsolvableOutput.txt"));

    // Small open maze
    PathFinder.solveMaze("mazes/tinyOpen.txt","mazeSols/tinyOpenOutput.txt");
    PathFinder.reverseSolveMaze("mazes/tinyOpen.txt","mazeSols/tinyOpenOutputRev.txt");
    Assert.assertTrue(mazeCheck("mazeSols/tinyOpenOutput.txt","mazeSols/tinyOpenOutputRev.txt"));
    Assert.assertFalse(mazeCheck("mazes/tinyOpen.txt","mazeSols/tinyOpenOutputRev.txt"));

    // Open maze
    PathFinder.solveMaze("mazes/demoMaze.txt","mazeSols/demoMazeOutput.txt");
    PathFinder.reverseSolveMaze("mazes/demoMaze.txt","mazeSols/demoMazeOutputRev.txt");
    Assert.assertTrue(mazeCheck("mazeSols/demoMazeOutput.txt","mazeSols/demoMazeOutputRev.txt"));
    Assert.assertFalse(mazeCheck("mazes/demoMaze.txt","mazeSols/demoMazeOutput.txt"));

    // Small turn
    PathFinder.solveMaze("mazes/smallTurn.txt","mazeSols/smallTurnOutput.txt");
    PathFinder.reverseSolveMaze("mazes/smallTurn.txt","mazeSols/smallTurnOutputRev.txt");
    Assert.assertTrue(mazeCheck("mazeSols/smallTurnOutput.txt","mazeSols/smallTurnOutputRev.txt"));
    Assert.assertFalse(mazeCheck("mazes/smallTurn.txt","mazeSols/smallTurnOutputRev.txt"));

    // Big turn
    PathFinder.solveMaze("mazes/turn.txt","mazeSols/turnOutput.txt");
    PathFinder.reverseSolveMaze("mazes/turn.txt","mazeSols/turnOutputRev.txt");
    Assert.assertTrue(mazeCheck("mazeSols/turnOutput.txt","mazeSols/turnOutputRev.txt"));
    Assert.assertFalse(mazeCheck("mazes/turn.txt","mazeSols/turnOutput.txt"));

    // Start and Goal are on the edges
    PathFinder.solveMaze("mazes/edges.txt","mazeSols/edgesOutput.txt");
    PathFinder.reverseSolveMaze("mazes/edges.txt","mazeSols/edgesOutputRev.txt");
    Assert.assertTrue(mazeCheck("mazeSols/edgesOutput.txt","mazeSols/edgesOutputRev.txt"));
    Assert.assertFalse(mazeCheck("mazes/edges.txt","mazeSols/edgesOutputRev.txt"));

    // Edges are open, and traversal must happen along the edges
    /*
     * Interestingly, probably due to the properties of .txt files, the entire right side of the maze must be enclosed
     * in order for traversal to occur. Otherwise the file cannot be read in properly
     */
    PathFinder.solveMaze("mazes/openEdges.txt","mazeSols/openEdgesOutput.txt");
    PathFinder.reverseSolveMaze("mazes/openEdges.txt","mazeSols/openEdgesOutputRev.txt");
    Assert.assertTrue(mazeCheck("mazeSols/openEdgesOutput.txt","mazeSols/openEdgesOutputRev.txt"));
    Assert.assertFalse(mazeCheck("mazes/openEdges.txt","mazeSols/openEdgesOutput.txt"));

    // Large open maze a large gap between the two pieces of the maze
    PathFinder.solveMaze("mazes/largeOpenEdges.txt","mazeSols/largeOpenEdgesOutput.txt");
    PathFinder.reverseSolveMaze("mazes/largeOpenEdges.txt","mazeSols/largeOpenEdgesOutputRev.txt");
    Assert.assertTrue((mazeCheck("mazeSols/largeOpenEdgesOutput.txt","mazeSols/largeOpenEdgesOutputRev.txt")));
    Assert.assertFalse(mazeCheck("mazes/largeOpenEdges.txt","mazeSols/largeOpenEdgesOutput.txt"));

    // Completely open maze (no walls) with start and goal on the edges
    PathFinder.solveMaze("mazes/completelyOpen.txt","mazeSols/completelyOpenOutput.txt");
    PathFinder.reverseSolveMaze("mazes/completelyOpen.txt","mazeSols/completelyOpenOutputRev.txt");
    Assert.assertTrue(mazeCheck("mazeSols/completelyOpenOutput.txt","mazeSols/completelyOpenOutputRev.txt"));
    Assert.assertFalse(mazeCheck("mazes/completelyOpen.txt","mazeSols/completelyOpenOutput.txt"));

    // One for fun
    PathFinder.solveMaze("mazes/starWars.txt","mazeSols/starWarsOutput.txt");
    PathFinder.reverseSolveMaze("mazes/starWars.txt","mazeSols/starWarsOutputRev.txt");
    Assert.assertTrue(mazeCheck("mazeSols/starWarsOutput.txt","mazeSols/starWarsOutputRev.txt"));
    Assert.assertFalse(mazeCheck("mazes/starWars.txt","mazeSols/starWarsOutput.txt"));

    /*
     * This portion is for error testing which includes:
     *  - File doesn't exist
     *  - Non-integer inputs for the dimensions (or no inputs at all)
     *  - Mazes with no start or end
     *  - Mazes with multiple start or end points
     */

    // File doesn't exist
    try{
      PathFinder.solveMaze("mazes/noFile.txt","mazeSols/noFileOutput.txt");
    }catch(FileNotFoundException e){
      System.out.println("TEST PASSED - File does not exist");
    }

    // Non-integer number of rows
    try{
      PathFinder.solveMaze("badMazes/badRowsDim.txt","mazeSols/badRowsDimOut.txt");
    }catch(NumberFormatException e){
      System.out.println("TEST PASSED - Number of rows not a valid integer");
    }

    // Non-integer number of columns
    try{
      PathFinder.solveMaze("badMazes/badColsDim.txt","mazeSols/badColsDimOut.txt");
    }catch(NumberFormatException e){
      System.out.println("TEST PASSED - Number of columns not a valid integer");
    }

    // Multiple starts
    try{
      PathFinder.solveMaze("badMazes/multipleStart.txt","mazeSols/multipleStartOutput.txt");
    }catch(Exception e){
      System.out.println("TEST PASSED - Multiple starts included in the file");
    }

    // Multiple goals
    try{
      PathFinder.solveMaze("badMazes/multipleGoal.txt","mazeSols/multipleGoalOutput.txt");
    }catch(Exception e){
      System.out.println("TEST PASSED - Multiple goals included in the file");
    }

    // No goal
    try{
      PathFinder.solveMaze("badMazes/noGoal.txt","mazeSols/noGoalOutput.txt");
    }catch(Exception e){
      System.out.println("TEST PASSED - No goal included in the file");
    }

    // No start
    try{
      PathFinder.solveMaze("badMazes/noStart.txt","mazeSols/noStartOutput.txt");
    }catch (Exception e){
      System.out.println("TEST PASSED - No start included in the file");
    }

    // No start or goal
    try{
      PathFinder.solveMaze("badMazes/noStartOrGoal.txt","mazeSols/noStartOrGoalOutput.txt");
    }catch (Exception e){
      System.out.println("TEST PASSED - No start or goal included in the file");
    }

    System.out.println("");
    System.out.println("All tests passed!");
  }

  /**
   * mazeCheck is a test method used to verify if maze files are the same
   * It takes in two inputs (both filenames) of .txt files that are mazes for our Pac-Man assignment
   * Ideally the maze files are different, and the method will warn the user if the same filename is entered for
   * forwardSolve and reverseSolve
   * @param forwardSolve - The file name of the forward solved maze
   * @param reverseSolve - The file name of the reverse solved maze
   * @return true if the maze solutions are identical, false if otherwise
   * @throws FileNotFoundException - Throws an exception if the file is not found in the path directory
   */
  public static boolean mazeCheck(String forwardSolve, String reverseSolve)throws FileNotFoundException{

    try {
      if (forwardSolve.equals(reverseSolve)) {
        System.err.println("The file names for forward and reverse are the same - use different files for proper verification");
        // I mean, it's not ideal, but the mazes are still equal
        return true;
      }

      Scanner forwardScan = new Scanner(new File(forwardSolve));
      Scanner reverseScan = new Scanner(new File(reverseSolve));

      while (forwardScan.hasNext() && reverseScan.hasNext()) {
        if (!forwardScan.nextLine().equals(reverseScan.nextLine())) {
          return false;
        }
      }
      return true;
    }catch(FileNotFoundException e){
      e.printStackTrace();
      throw new FileNotFoundException();
    }
  }

}

