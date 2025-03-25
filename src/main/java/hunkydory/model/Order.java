package hunkydory.model;

import java.math.BigDecimal;
import java.time.LocalDate;

@SuppressWarnings("unused")
public class Order {
    private int orderID;       // id_compra (PK)
    private int customerID;    // id_cliente (FK)
    private String status;     // status
    private BigDecimal totalValue;  // valor_total
    private LocalDate orderDate;    // data_pedido
    private LocalDate deliveryDate; // data_entrega

    public Order() {
    }

    public Order(int orderID, int customerID, String status,
                 BigDecimal totalValue, LocalDate orderDate, LocalDate deliveryDate) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.status = status;
        this.totalValue = totalValue;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
    }

    public int getOrderID() {
        return orderID;
    }
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
    public int getCustomerID() {
        return customerID;
    }
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public BigDecimal getTotalValue() {
        return totalValue;
    }
    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }
    public LocalDate getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }
    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Override
    public String toString() {
        return "Order [orderID=" + orderID
                + ", customerID=" + customerID
                + ", status=" + status
                + ", totalValue=" + totalValue
                + ", orderDate=" + orderDate
                + ", deliveryDate=" + deliveryDate + "]";
    }
}
