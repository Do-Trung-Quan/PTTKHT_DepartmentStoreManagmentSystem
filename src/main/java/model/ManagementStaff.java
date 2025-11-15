package model;

import java.time.LocalDate;

public class ManagementStaff extends Staff {

    public ManagementStaff() {}

    public ManagementStaff(int id, String fullName, LocalDate dateOfBirth, String gender, String telephone, String email, String password, String role) {
        super(id, fullName, dateOfBirth, gender, telephone, email, password, role);
    }
}
