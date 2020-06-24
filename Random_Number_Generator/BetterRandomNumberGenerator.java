package lab07;

public class BetterRandomNumberGenerator implements RandomNumberGenerator {
    private long previousTerm;
    private long A = 48271;

    /**
     * This function returns a random integer between [0,max)
     *
     * @param max the upper bound for the range of the random number, non-inclusive.
     * @return an integer between [0, max).
     * A lot of helped was gleaned from this value
     * https://en.wikipedia.org/wiki/Linear_congruential_generator
     * Prime factors of max 10,000 = 2 & 5
     * C cannot share factors of the max => 13
     * A-1 needs to share the prime factors of 2 & 5 and be divisible by 4 => 21
     */
    @Override
    public int nextInt(int max) {
        previousTerm = (21 * previousTerm + 13) % max;
        return (int) previousTerm;
    }

    /**
     * This function sets a seed for the random number generator. A random number
     * generator should return the same sequence of numbers for the same seed.
     *
     * @param seed the seed parameter used to initialize the random number generator.
     */
    @Override
    public void setSeed(long seed) {
        this.previousTerm = seed;
    }

    /**
     * This function sets the two constants for use with the random generator (see
     * your textbook for more information). If your generator does not use such
     * constants then you are free to ignore the input from this function.
     *
     * @param const1
     * @param const2
     */
    @Override
    public void setConstants(long const1, long const2) {
        // Do nothing
    }
}
