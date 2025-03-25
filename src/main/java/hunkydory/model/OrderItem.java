package hunkydory.model;

import java.math.BigDecimal;

@SuppressWarnings("unused")
public class OrderItem {
    private int orderID;   // id_compra (PK part 1)
    private int productID; // id_produto (PK part 2)
    private int quantity;  // quantidade
    private BigDecimal unitPrice; // preco_unitario

    public OrderItem() {
    }

    public OrderItem(int orderID, int productID, int quantity, BigDecimal unitPrice) {
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
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
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "OrderItem [orderID=" + orderID
                + ", productID=" + productID
                + ", quantity=" + quantity
                + ", unitPrice=" + unitPrice + "]";
    }
}
