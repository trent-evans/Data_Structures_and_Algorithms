package assignment06;

public class MediocreHashFunctor implements HashFunctor {
    @Override
    public int hash(String item) {
        return item.charAt(0);
    }
}
