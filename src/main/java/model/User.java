package model;

/**
 * Created by samuel on 5/30/17.
 */
public class User {

    private int id;
    private String wNumber;
    private String email;
    private String firstName;
    private String lastName;
    private Address address;
    private String password;

    public User(String wNumber, String email, String firstName, String lastName, Address address, String password) {
        this.wNumber = wNumber;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.password = password;
    }

    public User() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
    	return this.id;
    }

    public void setId(int id) {
    	this.id = id;
    }

    public String getwNumber() {
        return wNumber;
    }

    public void setwNumber(String wNumber) {
        this.wNumber = wNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if (((User) obj).getwNumber().equals(this.getwNumber())) {
            return true;
        }
        return false;
    }
}
