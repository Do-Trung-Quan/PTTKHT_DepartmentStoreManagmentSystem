package model;

public class ImportProduct {
    private int id;
    private int quantity;
    private float importUnitPrice;
    private String note;
    private float importPrice;
    private Product product;  // chỉ tham chiếu tới Product, không chứa ImportInvoice

    public ImportProduct() {}

    public ImportProduct(int id, int quantity, float importUnitPrice, String note, Product product) {
        this.id = id;
        this.quantity = quantity;
        this.importUnitPrice = importUnitPrice;
        this.note = note;
        this.importPrice = (float)quantity*importUnitPrice;
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getImportUnitPrice() {
        return importUnitPrice;
    }

    public void setImportUnitPrice(float importUnitPrice) {
        this.importUnitPrice = importUnitPrice;
    }
    
    public float getImportPrice() {
        return importPrice;
    }

    public void setImportPrice() {
        this.importPrice = this.importUnitPrice * this.quantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
