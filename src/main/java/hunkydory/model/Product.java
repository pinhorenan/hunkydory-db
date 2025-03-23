package hunkydory.model;

import java.math.BigDecimal;

public class Product {
    private int idProduct;
    private String name;
    private BigDecimal price;
    private String description;
    private String category;
    private String supplierCnpj;  // alterado de int supplierId para String supplierCnpj

    public Product(int idProduct, String name, BigDecimal price, String description, String category, String supplierCnpj) {
        this.idProduct = idProduct;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.supplierCnpj = supplierCnpj;
    }

    public int getIdProduct() {
        return idProduct;
    }
    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getSupplierCNPJ() {
        return supplierCnpj;
    }
    public void setSupplierCNPJ(String supplierCnpj) {
        this.supplierCnpj = supplierCnpj;
    }

    @Override
    public String toString() {
        return "Product [idProduct=" + idProduct +
                ", name=" + name +
                ", price=" + price +
                ", description=" + description +
                ", category=" + category +
                ", supplierCnpj=" + supplierCnpj + "]";
    }
}
