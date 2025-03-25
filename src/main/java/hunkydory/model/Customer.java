package hunkydory.model;

@SuppressWarnings("unused")
public class Customer {
    private int customerID;  // id_cliente
    private String name;     // nome
    private String email;    // email
    private String password; // senha
    private String phone;    // telefone

    public Customer() {
    }

    public Customer(int customerID, String name, String email, String password, String phone) {
        this.customerID = customerID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public int getCustomerID() {
        return customerID;
    }
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Customer [customerID=" + customerID
                + ", name=" + name
                + ", email=" + email
                + ", password=" + password
                + ", phone=" + phone + "]";
    }
}
