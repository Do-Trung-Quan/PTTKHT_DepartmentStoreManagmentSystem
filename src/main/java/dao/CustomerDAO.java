package dao;

import model.Customer;
import java.sql.*;

public class CustomerDAO extends DAO {

    // Hàm đăng ký tài khoản khách hàng mới
    public boolean addCustomer(Customer c) {
        String sqlUser = "INSERT INTO tblUser (fullName, dateOfBirth, gender, telephone, email, password) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlCustomer = "INSERT INTO tblCustomer (tblUserid, address) VALUES (?, ?)";

        try {
            // Tắt auto commit để đảm bảo 2 insert thực hiện cùng lúc (transaction)
            con.setAutoCommit(false);

            // 1. Thêm vào bảng tblUser
            PreparedStatement psUser = con.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS);
            psUser.setString(1, c.getFullName());
            psUser.setDate(2, c.getDateOfBirth() != null ? java.sql.Date.valueOf(c.getDateOfBirth()) : null);
            psUser.setString(3, c.getGender());
            psUser.setString(4, c.getTelephone());
            psUser.setString(5, c.getEmail());
            psUser.setString(6, c.getPassword());
            psUser.executeUpdate();

            // Lấy id được tạo tự động
            ResultSet rs = psUser.getGeneratedKeys();
            int userId = 0;
            if (rs.next()) {
                userId = rs.getInt(1);
            }

            // 2. Thêm vào bảng tblCustomer
            PreparedStatement psCustomer = con.prepareStatement(sqlCustomer);
            psCustomer.setInt(1, userId);
            psCustomer.setString(2, c.getAddress());
            psCustomer.executeUpdate();

            // Commit transaction
            con.commit();
            con.setAutoCommit(true);
            return true;

        } catch (SQLException e) {
            try {
                con.rollback(); // Hủy nếu lỗi
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
    }
}
