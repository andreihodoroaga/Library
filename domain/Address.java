package domain;

public class Address {
    private Long id;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private int number;

    public Address(String street, String city, String state, String postalCode, int number) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.number = number;
    }

    public Address() {
        // default constructor
    }

    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s %s", street, city, state, postalCode);
    }
}
