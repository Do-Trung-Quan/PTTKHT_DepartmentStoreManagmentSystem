package dao;

import model.User;
import model.ManagementStaff;
import model.Customer;
import java.sql.*;
import java.time.LocalDate;

public class UserDAO extends DAO {

    public User login(String email, String password) {
        User user = null;
        String sql = "SELECT * FROM tblUser WHERE email = ? AND password = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("id");
                String fullName = rs.getString("fullName");
                LocalDate dob = rs.getDate("dateOfBirth") != null ? rs.getDate("dateOfBirth").toLocalDate() : null;
                String gender = rs.getString("gender");
                String telephone = rs.getString("telephone");
                String emailDb = rs.getString("email");
                String passDb = rs.getString("password");

                // 🔹 Kiểm tra xem người này có phải là Staff không
                String staffSql = "SELECT role FROM tblStaff WHERE tblUserid = ?";
                try (PreparedStatement psStaff = con.prepareStatement(staffSql)) {
                    psStaff.setInt(1, userId);
                    ResultSet rsStaff = psStaff.executeQuery();

                    if (rsStaff.next()) {
                        String role = rsStaff.getString("role");

                        // Kiểm tra xem là Manager hay Sales
                        if (role.equals("Manager")) {
                            user = new ManagementStaff(userId, fullName, dob, gender, telephone, emailDb, passDb, role);
                        } else {
//                            user = new SaleStaff(userId, fullName, dob, gender, telephone, emailDb, passDb, role);
                        }
                        return user;
                    }
                }

                // 🔹 Nếu không phải Staff thì kiểm tra Customer
                String customerSql = "SELECT address FROM tblCustomer WHERE tblUserid = ?";
                try (PreparedStatement psCus = con.prepareStatement(customerSql)) {
                    psCus.setInt(1, userId);
                    ResultSet rsCus = psCus.executeQuery();
                    if (rsCus.next()) {
                        String address = rsCus.getString("address");
                        user = new Customer(userId, fullName, dob, gender, telephone, emailDb, passDb, address);
                        return user;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user; // null nếu không tồn tại user hoặc sai mật khẩu
    }
}
