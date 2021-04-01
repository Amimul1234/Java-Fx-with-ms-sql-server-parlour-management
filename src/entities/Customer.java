package entities;

import java.io.Serializable;

public class Customer implements Serializable {

    private String creationDate;
    private String name;
    private String email;
    private String mobileNumber;

    public Customer(String creationDate, String name, String email, String mobileNumber) {
        this.creationDate = creationDate;
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
    }

    public Customer() {

    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}