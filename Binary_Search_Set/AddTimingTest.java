package lab05;

public class AddTimingTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		long startTime, soloLoopTime, endTime;
		BinarySearchSet<Integer> testSet = new BinarySearchSet<Integer>();
		
		System.out.println("Size\tContainsTime");
		
		int numberOfTests = 100000; // Ten thousand tests
		
		for(int x = 10; x <= 100; x++) {
			
			for(int  y = 0; y < Math.pow(2, x); y++) {
				// tests from between 1000 and 10,000 elements
				testSet.add((Integer)y); // Won't add duplicate array elements, which is awesome
			}
			
			testSet.remove((Integer)1); // Testing 
			
			startTime = System.nanoTime(); // Get things started because the initial call to System.nanoTime() is always slow
			while (System.nanoTime() - startTime < 100000000) { 
		    }
				
			startTime = System.nanoTime();
			
			for(int z = 0; z < numberOfTests; z++) {
				testSet.contains((Integer)1); // Search for an array element that is not there to force
			}
				
			soloLoopTime = System.nanoTime();
			
			for(int a = 0; a < numberOfTests; a++) {
			} // Blank loop
			
			endTime = System.nanoTime();
			
			double averageTimePerAreAnagrams = ((soloLoopTime - startTime) - (endTime - soloLoopTime)) / (double) numberOfTests;
			System.out.println(testSet.size() + "\t" + averageTimePerAreAnagrams);

			
		}
		System.out.println("");
		System.out.println("Done");
	}

}
