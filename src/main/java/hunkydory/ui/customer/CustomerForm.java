package hunkydory.ui.customer;

import hunkydory.dao.CustomerAddressDAO;
import hunkydory.dao.CustomerDAO;
import hunkydory.model.Customer;
import hunkydory.model.CustomerAddress;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CustomerForm extends Stage {

    private final TextField txtId;
    private final TextField txtName;
    private final TextField txtEmail;
    private final PasswordField txtPassword;
    private final TextField txtPhone;

    private final TextField txtStreet;
    private final TextField txtNumber;
    private final TextField txtCity;
    private final TextField txtState;
    private final TextField txtZip;
    private final TextField txtComplement;

    private Runnable onSave;
    private final CustomerDAO customerDAO;
    private final CustomerAddressDAO addressDAO = new CustomerAddressDAO();
    private final Customer customer;

    public CustomerForm(Customer customer, CustomerDAO dao) {
        this.customer = customer;
        this.customerDAO = dao;

        setTitle(customer == null ? "Novo Cliente" : "Editar Cliente");
        initModality(Modality.APPLICATION_MODAL);

        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setPadding(new Insets(10));

        Label lblId = new Label("ID:");
        txtId = new TextField();
        Label lblName = new Label("Nome:");
        txtName = new TextField();
        Label lblEmail = new Label("Email:");
        txtEmail = new TextField();
        Label lblPassword = new Label("Senha:");
        txtPassword = new PasswordField();
        Label lblPhone = new Label("Telefone:");
        txtPhone = new TextField();

        Label lblStreet = new Label("Rua:");
        txtStreet = new TextField();
        Label lblNumber = new Label("Número:");
        txtNumber = new TextField();
        Label lblCity = new Label("Cidade:");
        txtCity = new TextField();
        Label lblState = new Label("Estado:");
        txtState = new TextField();
        Label lblZip = new Label("CEP:");
        txtZip = new TextField();
        Label lblComplement = new Label("Complemento:");
        txtComplement = new TextField();

        if (customer != null) {
            txtId.setText(String.valueOf(customer.getCustomerID()));
            txtId.setDisable(true);
            txtName.setText(customer.getName());
            txtEmail.setText(customer.getEmail());
            txtPassword.setText(customer.getPassword());
            txtPhone.setText(customer.getPhone());

            CustomerAddress address = addressDAO.searchByCustomerID(customer.getCustomerID());
            if (address != null) {
                txtStreet.setText(address.getStreet());
                txtNumber.setText(address.getNumber());
                txtCity.setText(address.getCity());
                txtState.setText(address.getState());
                txtZip.setText(address.getZipCode());
                txtComplement.setText(address.getComplement());
            }
        }

        Button btnSave = new Button("Salvar");
        btnSave.setOnAction(e -> saveCustomer());

        Button btnCancel = new Button("Cancelar");
        btnCancel.setOnAction(e -> close());

        gp.addRow(0, lblId, txtId);
        gp.addRow(1, lblName, txtName);
        gp.addRow(2, lblEmail, txtEmail);
        gp.addRow(3, lblPassword, txtPassword);
        gp.addRow(4, lblPhone, txtPhone);
        gp.addRow(5, lblStreet, txtStreet);
        gp.addRow(6, lblNumber, txtNumber);
        gp.addRow(7, lblCity, txtCity);
        gp.addRow(8, lblState, txtState);
        gp.addRow(9, lblZip, txtZip);
        gp.addRow(10, lblComplement, txtComplement);
        gp.addRow(11, btnSave, btnCancel);

        Scene scene = new Scene(gp, 500, 550);
        setScene(scene);
    }

    private void saveCustomer() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String name = txtName.getText();
            String email = txtEmail.getText();
            String password = txtPassword.getText();
            String phone = txtPhone.getText();

            String street = txtStreet.getText();
            String number = txtNumber.getText();
            String city = txtCity.getText();
            String state = txtState.getText();
            String zip = txtZip.getText();
            String complement = txtComplement.getText();

            boolean ok;
            if (customer == null) {
                Customer newCustomer = new Customer(id, name, email, password, phone);
                ok = customerDAO.insert(newCustomer);
                if (ok) {
                    CustomerAddress address = new CustomerAddress(0, street, number, city, state, zip, complement);
                    address.setCustomerID(id);
                    addressDAO.insert(address);
                }
            } else {
                customer.setName(name);
                customer.setEmail(email);
                customer.setPassword(password);
                customer.setPhone(phone);
                ok = customerDAO.update(customer);

                CustomerAddress address = addressDAO.searchByCustomerID(customer.getCustomerID());
                if (address == null) {
                    address = new CustomerAddress(0, street, number, city, state, zip, complement);
                    address.setCustomerID(customer.getCustomerID());
                    addressDAO.insert(address);
                } else {
                    address.setStreet(street);
                    address.setNumber(number);
                    address.setCity(city);
                    address.setState(state);
                    address.setZipCode(zip);
                    address.setComplement(complement);
                    addressDAO.update(address);
                }
            }

            if (ok) {
                showAlert("Salvo com sucesso!");
                if (onSave != null) onSave.run();
                close();
            } else {
                showAlert("Erro ao salvar no banco de dados!");
            }

        } catch (NumberFormatException e) {
            showAlert("ID inválido. Digite apenas números.");
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.showAndWait();
    }

    public void setOnSave(Runnable onSave) {
        this.onSave = onSave;
    }
}
