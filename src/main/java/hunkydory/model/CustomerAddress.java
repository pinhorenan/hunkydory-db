package hunkydory.model;

@SuppressWarnings("unused")
public class CustomerAddress {
    private int addressID;   // id_endereco (PK)
    private int customerID;  // id_cliente (FK)
    private String street;   // rua
    private String number;   // numero
    private String city;     // cidade
    private String state;    // estado
    private String zipCode;  // cep
    private String complement; // complemento

    public CustomerAddress() {
    }

    public CustomerAddress(int addressID, String street, String number, String city,
                           String state, String zipCode, String complement) {
        this.addressID = addressID;
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
    public int getCustomerID() {
        return customerID;
    }
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
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
        return "CustomerAddress [addressID=" + addressID
                + ", customerID=" + customerID
                + ", street=" + street
                + ", number=" + number
                + ", city=" + city
                + ", state=" + state
                + ", zipCode=" + zipCode
                + ", complement=" + complement + "]";
    }
}
