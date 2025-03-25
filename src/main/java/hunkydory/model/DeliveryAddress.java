package hunkydory.model;

@SuppressWarnings("unused")
public class DeliveryAddress {
    private int addressId; // ID do endereço (PK)
    private int orderID;   // ID da compra (FK)
    private String street;
    private String number;
    private String city;
    private String state;
    private String zipCode;
    private String complement;

    public DeliveryAddress() {

    }

    public DeliveryAddress(int addressId, int orderID, String street, String number, String city, String state, String zipCode, String complement) {
        this.addressId = addressId;
        this.orderID = orderID;
        this.street = street;
        this.number = number;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.complement = complement;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
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
        return "DeliveryAddress [addressId=" + addressId
                + ", orderID=" + orderID
                + ", street=" + street
                + ", number=" + number
                + ", city=" + city
                + ", state=" + state
                + ", zipCode=" + zipCode
                + ", complement=" + complement + "]";
    }
}
