package lab07;

/**
 * Implementation of a generator that produces a very non-random sequence of
 * numbers.
 */
public class PoorRandomNumberGenerator implements RandomNumberGenerator {
  long seed;
  long const1;
  long const2;

  /**
   *
   * @param max
   *          the upper bound for the range of the random number, non-inclusive.
   * @return 1 <- it's not exactly good RNG
   */
  public int nextInt(int max) {
    return 1;
  }

  public void setSeed(long _seed) {
    this.seed = _seed;
  }

  public void setConstants(long _const1, long _const2) {
    this.const1 = _const1;
    this.const2 = _const2;
  }
}
