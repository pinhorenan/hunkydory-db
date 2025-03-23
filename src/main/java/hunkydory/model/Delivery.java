package hunkydory.model;

import java.time.LocalDate;

public class Delivery {
    private int idDelivery;
    private int orderId;
    private LocalDate deliveryDate;
    private String status;

    public Delivery(int idDelivery, int orderId, LocalDate deliveryDate, String status) {
        this.idDelivery = idDelivery;
        this.orderId = orderId;
        this.deliveryDate = deliveryDate;
        this.status = status;
    }

    public int getIdDelivery() {
        return idDelivery;
    }

    public void setIdDelivery(int idDelivery) {
        this.idDelivery = idDelivery;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Delivery [idDelivery=" + idDelivery + ", orderId=" + orderId +
                ", deliveryDate=" + deliveryDate + ", status=" + status + "]";
    }
}
