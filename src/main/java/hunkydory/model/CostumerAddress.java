package hunkydory.model;

@SuppressWarnings("unused")
public class CostumerAddress {
    private int addressID; // ID do endereço (PK)
    private int costumerID; // ID do cliente (FK)
    private String street; // Rua
    private String number; // Número
    private String city;   // Cidade
    private String state;  // Estado
    private String zipCode; // CEP
    private String complement; // Complemento

    public CostumerAddress() {

    }

    public CostumerAddress(int addressID, String street, String number, String city, String state, String zipCode, String complement) {
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

    public int getCostumerID() {
        return costumerID;
    }

    public void setCostumerID(int costumerID) {
        this.costumerID = costumerID;
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
        return "CostumerAddress [addressID=" + addressID + ", costumerID=" + costumerID + ", street=" + street + ", number=" + number + ", city=" + city + ", state=" + state + ", zipCode=" + zipCode + ", complement=" + complement + "]";
    }

}
