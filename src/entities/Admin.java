package entities;

import java.io.Serializable;

public class Admin implements Serializable {

    private Integer adminId;
    private String name;
    private String password;
    private String email;
    private String contactNumber;

    public Admin() {
    }

    public Admin(Integer adminId, String name, String password, String email, String contactNumber) {
        this.adminId = adminId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.contactNumber = contactNumber;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

}
