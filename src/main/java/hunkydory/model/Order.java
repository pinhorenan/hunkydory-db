package hunkydory.model;

import java.time.LocalDate;

public class Order {
    private int idOrder;
    private LocalDate orderDate;
    private String status;
    private int customerId;

    public Order(int idOrder, LocalDate orderDate, String status, int customerId) {
        this.idOrder = idOrder;
        this.orderDate = orderDate;
        this.status = status;
        this.customerId = customerId;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Order [idOrder=" + idOrder +
                ", orderDate=" + orderDate +
                ", status=" + status +
                ", customerId=" + customerId + "]";
    }
}
