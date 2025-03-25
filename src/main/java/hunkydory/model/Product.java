package hunkydory.model;

import java.math.BigDecimal;

@SuppressWarnings("unused")
public class Product {
    private int productID; // ID do produto (PK)
    private int categoryID; // ID da categoria (FK)
    private int supplierID; // ID do fornecedor (FK)
    private String name; // Nome do produto
    private BigDecimal price; //
    private int stock; // Estoque
    private String description; // Descrição

    public Product() {

    }

    public Product(int productID, int categoryID, int supplierID, String name, BigDecimal price, int stock, String description) {
        this.productID = productID;
        this.categoryID = categoryID;
        this.supplierID = supplierID;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Product [productID=" + productID
                + ", categoryID=" + categoryID
                + ", supplierID=" + supplierID
                + ", name=" + name
                + ", price=" + price
                + ", stock=" + stock
                + ", description=" + description + "]";
    }
}