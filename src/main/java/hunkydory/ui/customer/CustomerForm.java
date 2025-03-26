package hunkydory.ui.customer;

import hunkydory.dao.CustomerDAO;
import hunkydory.model.Customer;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CustomerForm extends Stage {

    private final TextField txtId;
    private final TextField txtName;
    private final TextField txtEmail;
    private final PasswordField txtPassword;
    private final TextField txtPhone;

    private Runnable onSave; // callback executado ao salvar
    private final CustomerDAO customerDAO;
    private final Customer customer; // null = novo

    public CustomerForm(Customer customer, CustomerDAO dao) {
        this.customer = customer;
        this.customerDAO = dao;

        setTitle(customer == null ? "Novo Cliente" : "Editar Cliente");
        initModality(Modality.APPLICATION_MODAL);

        // Layout
        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setPadding(new Insets(10));

        // Campos
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

        // Se está editando, preenche campos
        if(customer != null) {
            txtId.setText(String.valueOf(customer.getCustomerID()));
            txtId.setDisable(true); // ID não pode ser alterado
            txtName.setText(customer.getName());
            txtEmail.setText(customer.getEmail());
            txtPassword.setText(customer.getPassword());
            txtPhone.setText(customer.getPhone());
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
        gp.addRow(5, btnSave, btnCancel);

        Scene scene = new Scene(gp, 400, 300);
        setScene(scene);
    }

    private void saveCustomer() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String name = txtName.getText();
            String email = txtEmail.getText();
            String password = txtPassword.getText();
            String phone = txtPhone.getText();

            boolean ok;
            if(customer == null) {
                Customer newCustomer = new Customer(id, name, email, password, phone);
                ok = customerDAO.insert(newCustomer);
            } else {
                customer.setName(name);
                customer.setEmail(email);
                customer.setPassword(password);
                customer.setPhone(phone);
                ok = customerDAO.update(customer);
            }

            if(ok) {
                showAlert("Salvo com sucesso!");
                if(onSave != null) onSave.run();
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
