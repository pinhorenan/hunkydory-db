package hunkydory.ui.supplier;

import hunkydory.dao.SupplierDAO;
import hunkydory.model.Supplier;
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

public class SupplierForm extends Stage {
    private final TextField txtSupplierID;
    private final TextField txtName;
    private final TextField txtContact;

    private Runnable onSave;
    private final SupplierDAO supplierDAO;
    private final Supplier supplier;

    public SupplierForm(Supplier supplier, SupplierDAO dao) {
        this.supplier = supplier;
        this.supplierDAO = dao;
        setTitle(supplier == null ? "Novo Fornecedor" : "Editar Fornecedor");
        initModality(Modality.APPLICATION_MODAL);

        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setPadding(new Insets(10));

        Label lblSupplierID = new Label("ID do Fornecedor:");
        txtSupplierID = new TextField();
        Label lblName = new Label("Nome:");
        txtName = new TextField();
        Label lblContact = new Label("Contato:");
        txtContact = new TextField();

        if (supplier != null) {
            txtSupplierID.setText(String.valueOf(supplier.getSupplierID()));
            txtSupplierID.setDisable(true);
            txtName.setText(supplier.getName());
            txtContact.setText(supplier.getContact());
        }

        Button btnSave = new Button("Salvar");
        btnSave.setOnAction(e -> saveSupplier());
        Button btnCancel = new Button("Cancelar");
        btnCancel.setOnAction(e -> close());

        gp.addRow(0, lblSupplierID, txtSupplierID);
        gp.addRow(1, lblName, txtName);
        gp.addRow(2, lblContact, txtContact);
        gp.addRow(3, btnSave, btnCancel);

        Scene scene = new Scene(gp, 400, 250);
        setScene(scene);
    }

    private void saveSupplier() {
        try {
            int supplierId = Integer.parseInt(txtSupplierID.getText());
            String name = txtName.getText();
            String contact = txtContact.getText();

            boolean ok;
            if (supplier == null) {
                Supplier newSupplier = new Supplier(supplierId, name, contact);
                ok = supplierDAO.insert(newSupplier);
            } else {
                supplier.setName(name);
                supplier.setContact(contact);
                ok = supplierDAO.update(supplier);
            }
            if (ok) {
                showAlert("Fornecedor salvo.");
                if (onSave != null) onSave.run();
                close();
            } else {
                showAlert("Erro ao salvar fornecedor.");
            }
        } catch (NumberFormatException ex) {
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
