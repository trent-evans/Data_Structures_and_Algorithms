package assignment06;

public class ReallyBadHashFunctor implements HashFunctor {
    @Override
    public int hash(String item) {
        return 0;
    }
}

