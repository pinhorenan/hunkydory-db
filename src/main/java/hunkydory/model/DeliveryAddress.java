package hunkydory.model;

@SuppressWarnings("unused")
public class DeliveryAddress {
    private int addressID; // id_endereco (PK)
    private int orderID;   // id_compra (FK)
    private String street;
    private String number;
    private String city;
    private String state;
    private String zipCode;
    private String complement;

    public DeliveryAddress() {
    }

    public DeliveryAddress(int addressID, int orderID, String street, String number,
                           String city, String state, String zipCode, String complement) {
        this.addressID = addressID;
        this.orderID = orderID;
        this.street = street;
        this.number = number;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.complement = complement;
    }

    public int getAddressID() {
        return addressID;
    }
    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }
    public int getOrderID() {
        return orderID;
    }
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getZipCode() {
        return zipCode;
    }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    public String getComplement() {
        return complement;
    }
    public void setComplement(String complement) {
        this.complement = complement;
    }

    @Override
    public String toString() {
        return "DeliveryAddress [addressID=" + addressID
                + ", orderID=" + orderID
                + ", street=" + street
                + ", number=" + number
                + ", city=" + city
                + ", state=" + state
                + ", zipCode=" + zipCode
                + ", complement=" + complement + "]";
    }
}
