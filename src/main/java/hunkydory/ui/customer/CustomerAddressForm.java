package hunkydory.ui.customer;

import hunkydory.dao.CustomerAddressDAO;
import hunkydory.model.CustomerAddress;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CustomerAddressForm extends Stage {
    private final TextField txtId;
    private final TextField txtStreet;
    private final TextField txtNumber;
    private final TextField txtCity;
    private final TextField txtState;
    private final TextField txtZip;
    private final TextField txtComplement;
    private final TextField txtCustomerId;

    private Runnable onSave;
    private final CustomerAddressDAO customerAddressDAO;
    private final CustomerAddress customerAddress;

    public CustomerAddressForm(CustomerAddress address, CustomerAddressDAO dao) {
        this.customerAddress = address;
        this.customerAddressDAO = dao;

        setTitle(address == null ? "Novo Endereço" : "Editar Endereço");
        initModality(Modality.APPLICATION_MODAL);

        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setPadding(new Insets(10));

        Label lblId = new Label("ID:");
        txtId = new TextField();
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
        Label lblCustomerId = new Label("ID do Cliente:");
        txtCustomerId = new TextField();

        if (customerAddress != null) {
            txtId.setText(String.valueOf(customerAddress.getAddressID()));
            txtId.setDisable(true);
            txtStreet.setText(customerAddress.getStreet());
            txtNumber.setText(customerAddress.getNumber());
            txtCity.setText(customerAddress.getCity());
            txtState.setText(customerAddress.getState());
            txtZip.setText(customerAddress.getZipCode());
            txtComplement.setText(customerAddress.getComplement());
            txtCustomerId.setText(String.valueOf(customerAddress.getCustomerID()));
        }

        Button btnSave = new Button("Salvar");
        btnSave.setOnAction(e -> saveAddress());
        Button btnCancel = new Button("Cancelar");
        btnCancel.setOnAction(e -> close());

        gp.addRow(0, lblId, txtId);
        gp.addRow(1, lblStreet, txtStreet);
        gp.addRow(2, lblNumber, txtNumber);
        gp.addRow(3, lblCity, txtCity);
        gp.addRow(4, lblState, txtState);
        gp.addRow(5, lblZip, txtZip);
        gp.addRow(6, lblComplement, txtComplement);
        gp.addRow(7, lblCustomerId, txtCustomerId);
        gp.addRow(8, btnSave, btnCancel);

        Scene scene = new Scene(gp, 400, 400);
        setScene(scene);
    }

    private void saveAddress() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String street = txtStreet.getText();
            String number = txtNumber.getText();
            String city = txtCity.getText();
            String state = txtState.getText();
            String zip = txtZip.getText();
            String complement = txtComplement.getText();
            int customerId = Integer.parseInt(txtCustomerId.getText());

            boolean ok;
            if (customerAddress == null) {
                CustomerAddress newAddress = new CustomerAddress(id, street, number, city, state, zip, complement);
                newAddress.setCustomerID(customerId);
                ok = customerAddressDAO.insert(newAddress);
            } else {
                customerAddress.setStreet(street);
                customerAddress.setNumber(number);
                customerAddress.setCity(city);
                customerAddress.setState(state);
                customerAddress.setZipCode(zip);
                customerAddress.setComplement(complement);
                customerAddress.setCustomerID(customerId);
                ok = customerAddressDAO.update(customerAddress);
            }
            if (ok) {
                showAlert("Endereço salvo com sucesso!");
                if (onSave != null) onSave.run();
                close();
            } else {
                showAlert("Erro ao salvar o endereço.");
            }
        } catch (NumberFormatException e) {
            showAlert("Entrada numérica inválida.");
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
