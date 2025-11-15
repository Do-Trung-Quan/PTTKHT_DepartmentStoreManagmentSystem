package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {
    protected static Connection con = null;

    // Thông tin kết nối CSDL
    private static final String DB_URL = "jdbc:mysql://localhost:3306/onlinedepartmentstore?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String DB_USER = "root"; // đổi nếu bạn dùng user khác
    private static final String DB_PASS = "12345"; // nhập mật khẩu thật

    // Khối static chỉ chạy 1 lần khi class được load
    static {
        try {
            // Nạp driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Tạo kết nối dùng chung cho toàn bộ DAO
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println("✅ DAO: Kết nối MySQL thành công!");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Không tìm thấy driver MySQL!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Lỗi kết nối cơ sở dữ liệu!");
            e.printStackTrace();
        }
    }

    // Hàm getter để lớp con có thể lấy connection
    public static Connection getConnection() {
        return con;
    }
}
