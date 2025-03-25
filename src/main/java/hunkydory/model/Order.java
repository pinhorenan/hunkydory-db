package hunkydory.model;

import java.math.BigDecimal;
import java.time.LocalDate;

@SuppressWarnings("unused")
public class Order {
    private int orderID;    // ID da compra (PK)
    private int costumerID; // ID do cliente (FK)
    private String status;  // Status da compra
    private BigDecimal total; // Valor total
    private LocalDate date; // Data da compra
    private LocalDate deliveryDate; // Data de entrega

    public Order() {

    }

    public Order(int orderID, int costumerID, String status, BigDecimal total, LocalDate date, LocalDate deliveryDate) {
        this.orderID = orderID;
        this.costumerID = costumerID;
        this.status = status;
        this.total = total;
        this.date = date;
        this.deliveryDate = deliveryDate;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getCostumerID() {
        return costumerID;
    }

    public void setCostumerID(int costumerID) {
        this.costumerID = costumerID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
                + ", costumerID=" + costumerID
                + ", status=" + status
                + ", total=" + total
                + ", date=" + date
                + ", deliveryDate=" + deliveryDate + "]";
    }
}