package representation;

import application.ApplicationConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import model.Address;

/**
 * Created by samuel on 7/6/17.
 * Class representing what the service will return for a given user.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRepresentation {

    private Long id;
    private String userName;
    private String wNumber;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String password;
    private Address address;
    private ApplicationConstants.AccessLevel accessLevel;

    public ApplicationConstants.AccessLevel getAccessLevel () {
        return accessLevel;
    }

    public void setAccessLevel (ApplicationConstants.AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getPhone () {
        return phone;
    }

    public void setPhone (String phone) {
        this.phone = phone;
    }

    @JsonIgnore
    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public String getUserName () {
        return userName;
    }

    public void setUserName (String userName) {
        this.userName = userName;
    }

    public String getwNumber () {
        return wNumber;
    }

    public void setwNumber (String wNumber) {
        this.wNumber = wNumber != null ? wNumber : "";
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email != null ? email : "";
    }

    public String getFirstName () {
        return firstName;
    }

    public void setFirstName (String firstName) {
        this.firstName = firstName != null ? firstName : "";
    }

    public String getLastName () {
        return lastName;
    }

    public void setLastName (String lastName) {
        this.lastName = lastName != null ? lastName : "";
    }

    public Address getAddress () {
        return address;
    }

    public void setAddress (Address address) {
        this.address = address;
    }
}
