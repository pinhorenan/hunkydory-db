package hunkydory.model;

public class Inventory {
    private int productId;
    private int quantityAvailable;


    public Inventory(int productId, int quantityAvailable) {
        this.productId = productId;
        this.quantityAvailable = quantityAvailable;
    }

    public int getProductId() {
        return productId;
    }

    @SuppressWarnings("unused")
    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    @SuppressWarnings("unused")
    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    @Override
    public String toString() {
        return "Inventory [productId=" + productId +
                ", quantityAvailable=" + quantityAvailable + "]";
    }
}
