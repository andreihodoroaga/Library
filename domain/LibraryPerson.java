package domain;

public class LibraryPerson {
    protected Long id;

    private String name;
    private String email;
    private Long addressId;

    public LibraryPerson(String name, String email, Long addressId) {
        this.name = name;
        this.email = email;
        this.addressId = addressId;
    }

    public LibraryPerson() {
        // default constructor
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddress(Long addressId) {
        this.addressId = addressId;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", name, email);
    }
}
