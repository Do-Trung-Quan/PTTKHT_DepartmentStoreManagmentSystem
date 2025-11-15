package dao;

import java.sql.*;
import model.ImportInvoice;
import model.ImportProduct;

public class ImportInvoiceDAO extends DAO {

    public ImportInvoiceDAO() {
        super();
    }

    // ➕ Thêm hóa đơn nhập + chi tiết sản phẩm + cập nhật tồn kho
    public boolean addImportInvoice(ImportInvoice importInvoice) {
        String sqlImportInvoice = "INSERT INTO tblImportInvoice(importDate, tblSupplierid, tblManagementStaffid) "
                                + "VALUES (?, ?, ?)";

        String sqlImportProduct = "INSERT INTO tblImportProduct(quantity, importUnitPrice, note, tblProductid, tblImportInvoiceid) "
                                + "VALUES (?, ?, ?, ?, ?)";

        String sqlUpdateProduct = "UPDATE tblProduct SET quantity = quantity + ? WHERE id = ?";

        try {
            con.setAutoCommit(false); // 🔁 bắt đầu transaction

            // 1️⃣ Thêm hóa đơn nhập
            PreparedStatement psImportInvoice = con.prepareStatement(sqlImportInvoice, Statement.RETURN_GENERATED_KEYS);
            psImportInvoice.setDate(1, importInvoice.getImportDate());
            psImportInvoice.setInt(2, importInvoice.getSupplier().getId());
            psImportInvoice.setInt(3, importInvoice.getManagementStaff().getId());
            psImportInvoice.executeUpdate();

            // Lấy ID của hóa đơn vừa thêm
            ResultSet rs = psImportInvoice.getGeneratedKeys();
            int importInvoiceId = 0;
            if (rs.next()) {
                importInvoiceId = rs.getInt(1);
            }

            // 2️⃣ Thêm các sản phẩm nhập + cập nhật tồn kho
            for (ImportProduct ip : importInvoice.getImportProductList()) {
                // Thêm vào bảng tblImportProduct
                PreparedStatement psImportProduct = con.prepareStatement(sqlImportProduct);
                psImportProduct.setInt(1, ip.getQuantity());
                psImportProduct.setFloat(2, ip.getImportUnitPrice());
                psImportProduct.setString(3, ip.getNote());
                psImportProduct.setInt(4, ip.getProduct().getId());
                psImportProduct.setInt(5, importInvoiceId);
                psImportProduct.executeUpdate();

                // Cập nhật số lượng sản phẩm trong tblProduct
                PreparedStatement psUpdateProduct = con.prepareStatement(sqlUpdateProduct);
                psUpdateProduct.setInt(1, ip.getQuantity());
                psUpdateProduct.setInt(2, ip.getProduct().getId());
                psUpdateProduct.executeUpdate();
            }

            con.commit(); // ✅ xác nhận transaction
            return true;

        } catch (SQLException e) {
            try {
                con.rollback(); // ❌ lỗi → rollback
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}
