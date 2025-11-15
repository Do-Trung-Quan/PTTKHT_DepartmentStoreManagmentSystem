package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Supplier;

public class SupplierDAO extends DAO {

    public SupplierDAO() {
        super();
    }

    public List<Supplier> searchSupplierByName(String name) {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT * FROM tblSupplier WHERE name LIKE ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Supplier s = new Supplier(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("telephone"),
                        rs.getString("email"),
                        rs.getString("address")
                );
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addSupplier(Supplier supplier) {
        String sql = "INSERT INTO tblSupplier(name, telephone, email, address) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, supplier.getName());
            ps.setString(2, supplier.getTelephone());
            ps.setString(3, supplier.getEmail());
            ps.setString(4, supplier.getAddress());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
