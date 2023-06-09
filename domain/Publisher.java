package domain;

public class Publisher {
    private Long id;
    private String name;
    private String location;

    public Publisher(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public Publisher() {
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
