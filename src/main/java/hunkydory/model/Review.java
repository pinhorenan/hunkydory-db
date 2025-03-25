package hunkydory.model;

import java.time.LocalDate;

@SuppressWarnings("unused")
public class Review {
    private int reviewID;    // id_avaliacao (PK)
    private int customerID;  // id_cliente (FK)
    private int orderID;     // id_compra (FK)
    private int rating;      // nota (1..5)
    private String comment;  // comentario
    private LocalDate date;  // data

    public Review() {
    }

    public Review(int reviewID, int customerID, int orderID,
                  int rating, String comment, LocalDate date) {
        this.reviewID = reviewID;
        this.customerID = customerID;
        this.orderID = orderID;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public int getReviewID() {
        return reviewID;
    }
    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }
    public int getCustomerID() {
        return customerID;
    }
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    public int getOrderID() {
        return orderID;
    }
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewID=" + reviewID +
                ", customerID=" + customerID +
                ", orderID=" + orderID +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", date=" + date +
                '}';
    }
}
