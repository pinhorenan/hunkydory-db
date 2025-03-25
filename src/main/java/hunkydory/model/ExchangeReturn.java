package hunkydory.model;

import java.time.LocalDate;

@SuppressWarnings("unused")
public class ExchangeReturn {
    private int solicitationID; // ID da solicitação (PK)
    private int orderID;        // FK para compra
    private LocalDate date;     // Data da solicitação
    private String reason;
    private String status;


    public ExchangeReturn() {

    }

    public ExchangeReturn(int solicitationID, int orderID, LocalDate date, String reason, String status) {
        this.solicitationID = solicitationID;
        this.orderID = orderID;
        this.date = date;
        this.reason = reason;
        this.status = status;
    }

    public int getSolicitationID() {
        return solicitationID;
    }

    public void setSolicitationID(int solicitationID) {
        this.solicitationID = solicitationID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ExchangeReturn [solicitationID=" + solicitationID
                + ", orderID=" + orderID
                + ", date=" + date
                + ", reason=" + reason
                + ", status=" + status + "]";
    }
}
