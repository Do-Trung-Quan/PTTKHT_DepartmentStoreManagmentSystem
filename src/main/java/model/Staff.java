package model;

import java.time.LocalDate;

public class Staff extends User {
    private String role;

    public Staff() {}

    public Staff(int id, String fullName, LocalDate dateOfBirth, String gender, String telephone, String email, String password, String role) {
        super(id, fullName, dateOfBirth, gender, telephone, email, password);
        this.role = role;
    }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
