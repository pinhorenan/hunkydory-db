package hunkydory.ui.customer;

import hunkydory.dao.CostumerDAO;
import hunkydory.model.Customer;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Formulário para criar/editar um cliente.
 */
public class CustomerForm extends Stage {

    private final TextField txtId;
    private final TextField txtName;
    private final TextField txtEmail;
    private final TextField txtPhone;
    private final TextField txtAddress;

    private Runnable onSave; // callback executado ao salvar
    private CostumerDAO costumerDAO;
    private Customer costumer; // null = novo

    public CustomerForm(Customer costumer, CostumerDAO dao) {
        this.costumer = costumer;
        this.costumerDAO = dao;

        setTitle(costumer == null ? "Novo Cliente" : "Editar Cliente");
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
        Label lblPhone = new Label("Telefone:");
        txtPhone = new TextField();
        Label lblAddress = new Label("Endereço:");
        txtAddress = new TextField();

        // Se está editando, preenche campos
        if(costumer != null) {
            txtId.setText(String.valueOf(costumer.getIdCustomer()));
            txtId.setDisable(true); // ID não pode ser alterado
            txtName.setText(costumer.getName());
            txtEmail.setText(costumer.getEmail());
            txtPhone.setText(costumer.getPhone());
            txtAddress.setText(costumer.getAddress());
        }

        Button btnSave = new Button("Salvar");
        btnSave.setOnAction(e -> saveCostumer());

        Button btnCancel = new Button("Cancelar");
        btnCancel.setOnAction(e -> close());

        gp.addRow(0, lblId, txtId);
        gp.addRow(1, lblName, txtName);
        gp.addRow(2, lblEmail, txtEmail);
        gp.addRow(3, lblPhone, txtPhone);
        gp.addRow(4, lblAddress, txtAddress);
        gp.addRow(5, btnSave, btnCancel);

        Scene scene = new Scene(gp, 400, 250);
        setScene(scene);
    }

    private void saveCostumer() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String name = txtName.getText();
            String email = txtEmail.getText();
            String phone = txtPhone.getText();
            String address = txtAddress.getText();

            boolean ok;
            if(costumer == null) {
                // Novo
                Customer newC = new Customer(id, name, email, phone, address);
                ok = costumerDAO.insert(newC);
            } else {
                // Edição
                costumer.setName(name);
                costumer.setEmail(email);
                costumer.setPhone(phone);
                costumer.setAddress(address);
                ok = costumerDAO.update(costumer);
            }

            if(ok) {
                showInfo("Salvo com sucesso!");
                if(onSave != null) onSave.run();
                close();
            } else {
                showInfo("Erro ao salvar no banco de dados!");
            }
        } catch (NumberFormatException e) {
            showInfo("ID inválido. Digite apenas números.");
        }
    }

    private void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.showAndWait();
    }

    // Callback executado após salvar
    public void setOnSave(Runnable onSave) {
        this.onSave = onSave;
    }
}
