package model;

import java.time.LocalDate;

public class Customer extends User {
    private String address;

    public Customer() {}

    public Customer(int id, String fullName, LocalDate dateOfBirth, String gender, String telephone, String email, String password, String address) {
        super(id, fullName, dateOfBirth, gender, telephone, email, password);
        this.address = address;
    }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
