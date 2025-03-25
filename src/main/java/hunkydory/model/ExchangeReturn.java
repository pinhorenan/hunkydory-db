package hunkydory.model;

import java.time.LocalDate;

@SuppressWarnings("unused")
public class ExchangeReturn {
    private int requestID;        // id_solicitacao (PK)
    private int orderID;          // id_compra (FK)
    private LocalDate requestDate; // data_solicitacao
    private String reason;        // motivo
    private String status;        // status

    public ExchangeReturn() {
    }

    public ExchangeReturn(int requestID, int orderID, LocalDate requestDate,
                          String reason, String status) {
        this.requestID = requestID;
        this.orderID = orderID;
        this.requestDate = requestDate;
        this.reason = reason;
        this.status = status;
    }

    public int getRequestID() {
        return requestID;
    }
    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }
    public int getOrderID() {
        return orderID;
    }
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
    public LocalDate getRequestDate() {
        return requestDate;
    }
    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
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
        return "ExchangeReturn [requestID=" + requestID
                + ", orderID=" + orderID
                + ", requestDate=" + requestDate
                + ", reason=" + reason
                + ", status=" + status + "]";
    }
}
