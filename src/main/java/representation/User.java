package representation;

/**
 * Created by samuel on 5/30/17.
 */
public class User {

    private String wNumber;
    private int id;
    private String firstName;
    private String lastName;

    public User(String wNumber, String firstName, String lastName) {
        this.wNumber = wNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User() {}

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
