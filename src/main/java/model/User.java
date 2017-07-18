package model;

import application.ApplicationConstants;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by samuel on 5/30/17.
 * Class representing a given user.
 */
public class User {

    private Long id;
    private String userName;
    private String wNumber;
    private String phone;
    private String email;
    private String firstName;
    private String lastName;
    private Address address;
    private String hashedPassword;
    private String salt;
    private ApplicationConstants.AccessLevel accessLevel;

    public User (String userName, String wNumber, String email, String firstName, String lastName, Address address,
            String hashedPassword, String salt, ApplicationConstants.AccessLevel accessLevel) {
        this.userName = userName;
        this.wNumber = wNumber;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
        this.accessLevel = accessLevel;
    }

    public User () {
    }

    public String getPhone () {
        return phone;
    }

    public void setPhone (String phone) {
        this.phone = phone;
    }

    public Long getId () {
        return this.id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getUserName () {
        return userName;
    }

    public void setUserName (String userName) {
        this.userName = userName;
    }

    public String getSalt () {
        return salt;
    }

    public void setSalt (String salt) {
        this.salt = salt;
    }

    public ApplicationConstants.AccessLevel getAccessLevel () {
        return accessLevel;
    }

    public void setAccessLevel (ApplicationConstants.AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public Address getAddress () {
        return address;
    }

    public void setAddress (Address address) {
        this.address = address;
    }

    public String getHashedPassword () {
        return hashedPassword;
    }

    public void setHashedPassword (String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getwNumber () {
        return wNumber;
    }

    public void setwNumber (String wNumber) {
        this.wNumber = wNumber;
    }

    public String getFirstName () {
        return firstName;
    }

    public void setFirstName (String firstName) {
        this.firstName = firstName;
    }

    public String getLastName () {
        return lastName;
    }

    public void setLastName (String lastName) {
        this.lastName = lastName;
    }

    @Override
    public int hashCode () {
        return new HashCodeBuilder(7, 31).append(this.getId()).toHashCode();
    }

    @Override
    public boolean equals (Object obj) {
        return obj instanceof User && ((User) obj).getId().equals(this.getId());
    }
}
