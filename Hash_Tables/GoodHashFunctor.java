package assignment06;

public class GoodHashFunctor implements HashFunctor {
    @Override
    public int hash(String item) {
        int count = 0;
        int hashValue = 7;
        while(count < item.length()){
            if(count == 3)
                break;
            hashValue = hashValue*item.charAt(count) + item.length()*31;
            count++;
        }
        return Math.abs(hashValue);
    }
}
