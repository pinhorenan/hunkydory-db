package hunkydory.model;

@SuppressWarnings("unused")
public class Supplier {
    private int supplierID; // ID do fornecedor (PK)
    private String name;    // Nome do fornecedor
    private String contact; // Contato

    public Supplier() {

    }

    public Supplier(int supplierID, String name, String contact) {
        this.supplierID = supplierID;
        this.name = name;
        this.contact = contact;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Supplier [supplierID=" + supplierID + ", name=" + name + ", contact=" + contact + "]";
    }
}