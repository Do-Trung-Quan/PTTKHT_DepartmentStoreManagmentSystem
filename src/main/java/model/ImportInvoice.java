package model;

import java.sql.Date;
import java.util.List;

public class ImportInvoice {
    private int id;
    private Date importDate;
    private float total;
    private Supplier supplier;
    private ManagementStaff managementStaff;
    private List<ImportProduct> importProductList; // danh sách sản phẩm trong hóa đơn nhập

    public ImportInvoice() {}

    public ImportInvoice(int id, Date importDate, float total, Supplier supplier, ManagementStaff managementStaff, List<ImportProduct> importProductList) {
        this.id = id;
        this.importDate = importDate;
        this.total = total;
        this.supplier = supplier;
        this.managementStaff = managementStaff;
        this.importProductList = importProductList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public ManagementStaff getManagementStaff() {
        return managementStaff;
    }

    public void setManagementStaff(ManagementStaff managementStaff) {
        this.managementStaff = managementStaff;
    }

    public List<ImportProduct> getImportProductList() {
        return importProductList;
    }

    public void setImportProductList(List<ImportProduct> importProductList) {
        this.importProductList = importProductList;
    }
}
