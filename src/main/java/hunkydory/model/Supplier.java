package hunkydory.model;

public class Supplier {
    private String cnpj;  // chave primária
    private String name;
    private String contact;

    public Supplier(String cnpj, String name, String contact) {
        this.cnpj = cnpj;
        this.name = name;
        this.contact = contact;
    }

    public String getCNPJ() {
        return cnpj;
    }
    public void setCNPJ(String cnpj) {
        this.cnpj = cnpj;
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
        return "Supplier [cnpj=" + cnpj +
                ", name=" + name +
                ", contact=" + contact + "]";
    }
}
