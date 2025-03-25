package hunkydory.model;

import java.time.LocalDate;

@SuppressWarnings("unused")
public class Review {
    private int reviewID;   // ID da avaliação (PK)
    private int costumerID; // ID do cliente (FK)
    private int orderID;    // ID do produto (FK
    private int rating;     // Nota (1..5)
    private String comment; // Comentário
    private LocalDate date; // Data da avaliação

    public Review() {

    }

    public Review(int reviewID, int costumerID, int orderID, int rating, String comment, LocalDate date) {
        this.reviewID = reviewID;
        this.costumerID = costumerID;
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

    public int getCostumerID() {
        return costumerID;
    }

    public void setCostumerID(int costumerID) {
        this.costumerID = costumerID;
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
                ", costumerID=" + costumerID +
                ", orderID=" + orderID +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", date=" + date +
                '}';
    }
}

