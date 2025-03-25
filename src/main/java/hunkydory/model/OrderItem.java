package hunkydory.model;

import java.math.BigDecimal;

@SuppressWarnings("unused")
public class OrderItem {
    private int orderID; // ID da compra (PK parte 1)
    private int productID; // ID do produto (PK parte 2)
    private int quantity; // Quantidade
    private BigDecimal price; // Preço unitário

    public OrderItem() {

    }

    public OrderItem(int orderID, int productID, int quantity, BigDecimal price) {
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override

    public String toString() {
        return "OrderItem [orderID=" + orderID
                + ", productID=" + productID
                + ", quantity=" + quantity
                + ", price=" + price + "]";
    }
}