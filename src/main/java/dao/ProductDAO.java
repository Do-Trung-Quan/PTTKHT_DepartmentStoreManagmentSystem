package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Product;
import model.Category;

public class ProductDAO extends DAO {

    public ProductDAO() {
        super();
    }

    // 🔍 Tìm sản phẩm theo tên
    public List<Product> searchItemByName(String name) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.id, p.name, p.quantity, p.unitPrice, p.description, "
                   + "c.id AS cid, c.title, c.description AS cdesc "
                   + "FROM tblProduct p "
                   + "JOIN tblCategory c ON p.tblCategoryid = c.id "
                   + "WHERE p.name LIKE ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Category c = new Category(
                        rs.getInt("cid"),
                        rs.getString("title"),
                        rs.getString("cdesc")
                );

                Product p = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getFloat("unitPrice"),
                        rs.getString("description"),
                        c
                );

                list.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // ➕ Thêm sản phẩm mới
    public boolean addItem(Product product) {
        String sql = "INSERT INTO tblProduct(name, quantity, unitPrice, description, tblCategoryid) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, product.getName());
            ps.setInt(2, product.getQuantity());
            ps.setFloat(3, product.getUnitPrice());
            ps.setString(4, product.getDescription());
            ps.setInt(5, product.getCategory().getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
