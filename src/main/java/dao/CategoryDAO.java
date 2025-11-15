package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Category;

public class CategoryDAO extends DAO {

    public CategoryDAO() {
        super();
    }

    // 🔍 Lấy tất cả Category từ bảng tblCategory
    public List<Category> getCategory() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT id, title, description FROM tblCategory";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Category c = new Category(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description")
                );
                list.add(c);
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
