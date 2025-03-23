package hunkydory.model;

/**
 * Model that represents the 'customer' table.
 */
public class Customer {
    private int idCustomer;
    private String name;
    private String email;
    private String phone;
    private String address;

    public Customer(int idCustomer, String name, String email, String phone, String address) {
        this.idCustomer = idCustomer;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public int getIdCustomer() {
        return idCustomer;
    }
    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return idCustomer + " - " + name;
    }
}
