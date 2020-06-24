package lab06;

// Custom class for testing
public class Pokemon {
    private String name;
    private int idNumber;

    Pokemon(String name, int idNumber){
        this.name = name;
        this.idNumber = idNumber;
    }

    public String getName() {
        return name;
    }

    public int getIdNumber() {
        return idNumber;
    }
}
