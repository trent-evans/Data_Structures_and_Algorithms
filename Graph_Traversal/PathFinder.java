package assignment07;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InvalidObjectException;
import java.io.PrintWriter;
import java.util.*;

public class PathFinder {

    /**
     * solveMaze is a method used for solving mazes input in a .txt file.
     * It uses the inner Graph class to create and solve the maze (from start to goal), then outputs the file.
     * @param inputFile - The path name of the input file
     * @param outputFile - The path name for the output file
     * @throws FileNotFoundException - Will throw an exception if the input file can't be found in the path directory
     */
    public static void solveMaze(String inputFile, String outputFile) throws FileNotFoundException {

        Graph myMaze = new Graph(inputFile);
        myMaze.findShortestPath(myMaze.start,myMaze.goal);
        myMaze.writeToFile(outputFile);

    }

    /**
     * reverseSolveMaze is a method used for solving mazes input in a .txt file, but backwards from the solveMaze class.
     * It uses the inner Graph class to create and solve the maze (from goal to start), then outputs the file.
     * This method is mostly used to test maze solutions.  Because if it works forward, it should work backwards.
     * @param inputFile - The path name of the input file
     * @param outputFile - The path name for the output file
     * @throws FileNotFoundException - Will throw an exception if the input file can't be found in the path directory
     */
    public static void reverseSolveMaze(String inputFile, String outputFile) throws FileNotFoundException {
        Graph reverseMaze = new Graph(inputFile);
        reverseMaze.findShortestPath(reverseMaze.goal,reverseMaze.start);
        reverseMaze.writeToFile(outputFile);
    }

    private static class Graph{
        private Node[][] maze;
        private int rows;
        private int cols;
        public Node start;
        public Node goal;

        Graph(String inputFilename) throws FileNotFoundException {
            start = null;
            goal = null;
            readInMaze(inputFilename);
        }

        /**
         * readInMaze takes a String .txt filename and reads in into the Graph class to set the 2D maze array
         * @param inputFilename - The path name of the input file
         * @throws FileNotFoundException - Will throw an exception if the input file can't be found in the path directory
         */
        public void readInMaze(String inputFilename) throws FileNotFoundException {
            try {
                Scanner fileRead = new Scanner(new File(inputFilename));
                String dimensions = fileRead.nextLine();
                String[] dimensionValues = dimensions.split(" ");
                try {
                    this.rows = Integer.parseInt(dimensionValues[0]);
                    this.cols = Integer.parseInt(dimensionValues[1]);
                }catch (NumberFormatException e){
                    e.printStackTrace();
                    throw new NumberFormatException("Invalid input for number of rows");
                }
                maze = new Node[rows][cols];
                for(int r = 0; r < rows; r++){
                    String[] newRow = fileRead.nextLine().split("");
                    for(int c = 0; c < cols; c++){
                        maze[r][c] = new Node(newRow[c],r,c);
                        if(newRow[c].equals("S")){
                            if(start != null) {
                                throw new InvalidObjectException("Cannot have more than one start");
                            }
                            start = maze[r][c];
//                            System.out.println("Start set");
                        }
                        if(newRow[c].equals("G")){
                            if(goal != null){
                                throw new InvalidObjectException("Cannot have more than one goal");
                            }
                            goal = maze[r][c];
//                            System.out.println("Goal set");
                        }
                    }
                }

                if(goal == null || start == null){
                    throw new InvalidObjectException("No start or goal found - Cannot solve maze");
                }

            }catch (FileNotFoundException | InvalidObjectException e){
                e.printStackTrace();
                throw new FileNotFoundException("File does not exist");
            }
//            System.out.println("Maze " + inputFilename + " read in");
        }

        /**
         * findShortestPath performs breadth-first traversal accross the provided Graph in order to find the shortest
         * path from the starting point (startNode) to the end point (goalNode).
         * @param startNode - Node at which to start the traversal
         * @param goalNode - Node at which to end the traversal
         */
        public void findShortestPath(Node startNode, Node goalNode){
            startNode.visited = true;
            LinkedList<Node> nodeQueue = new LinkedList<>();
            nodeQueue.add(startNode);
            while(!nodeQueue.isEmpty()){
                Node current = nodeQueue.remove();
                if(current.equals(goalNode)){
                    // Go back and change all the shortest path data to be periods
                    while(!current.cameFrom.equals(startNode)){
                        current.cameFrom.data = ".";
                        current = current.cameFrom;
                    }
//                    System.out.println("Shortest path found and marked");
                    return;
                }
                Node[] currentNeighbors = current.getNeighbors(this.maze, this.rows, this.cols);

                for(int x = 0; x < currentNeighbors.length; x++){
                    if(currentNeighbors[x] != null && !currentNeighbors[x].visited){
                        Node nextNode = currentNeighbors[x];
                        nextNode.visited = true;
                        nextNode.cameFrom = current;
                        nodeQueue.add(nextNode);
                    }
                }
            }
        }

        /**
         * writeToFile writes the solved maze to the filepath designamted by outputFilename
         * @param outputFilename - The filepath to where the output file should be written
         * @throws FileNotFoundException - Throws an exception if the location doesn't exist
         */
        public void writeToFile(String outputFilename) throws FileNotFoundException {
//            System.out.println("Preparing to write to " + outputFilename);
            PrintWriter pw = new PrintWriter(new File(outputFilename));
            String header = rows + " " + cols;
            pw.println(header);
            for(int r = 0; r < rows; r++){
                String rowString = "";
                for(int c = 0; c < cols; c++){
                    rowString += maze[r][c].data;
                }
                pw.println(rowString);
            }
            pw.flush();
            pw.close();
//            System.out.println("Data written to file " + outputFilename);
        }

    }

    private static class Node{
        private String data;
        public int row;
        public int col;
        public boolean visited = false;
        public Node cameFrom = null;

        Node(String data, int row, int col){
            this.data = data;
            this.row = row;
            this.col = col;
        }

        /**
         * getNeighbors returns an Array of the Nodes in the Graph that are immediately adjacent to the designated Node
         * Controls are in place for: If the neighbor is out of bounds of the Graph
         *                            If the neighbor is a wall
         *      If a value falls underneath either of these cases, that value of the Array is set to null
         * @param maze - The 2D array of nodes that create our maze
         * @param mazeRows - The number of rows of the 2D array
         * @param mazeCols - The number of columns in the 2D array
         * @return - An Array of Nodes each one representing the adjacent neighbors of the node
         */
        public Node[] getNeighbors(Node[][] maze, int mazeRows, int mazeCols){

            Node[] newNeighbors = new Node[4];
            int idx = 0;
            for(int r = -1; r <=1; r++){
                for(int c = -1; c <= 1; c++){

                    // If we're at the node itself
                    if(r==0 && c==0){ // If we're at the middle node, don't do anything
                        continue;
                    }else if(r == 0 || c == 0){ // Only if we're adjacent to the middle node do we want to do stuff
                        Node neighbor;
                        if(row+r >= mazeRows || row+r < 0 || col+c >= mazeCols || col+c < 0){ // If we exceed the bounds of the Graph return null
                            neighbor = null;
                        }else{ // Otherwise, set the neighbor
                            neighbor = maze[this.row+r][this.col+c];
                        }


                        if(neighbor == null || neighbor.data.equals("X")){ // If the neighbor data is a wall or outside of the range
                            newNeighbors[idx] = null;
                        }else{
                            newNeighbors[idx] = neighbor;
                        }
                        idx++;
                    }
                }
            }
            return newNeighbors;
        }
    }

}
