package hunkydory.ui.supplier;

import hunkydory.dao.SupplierDAO;
import hunkydory.model.Supplier;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SupplierForm extends Stage {
    private TextField txtCnpj;
    private TextField txtName;
    private TextField txtContact;

    private Runnable onSave;
    private SupplierDAO supplierDAO;
    private Supplier supplier;

    public SupplierForm(Supplier supplier, SupplierDAO dao) {
        this.supplier = supplier;
        this.supplierDAO = dao;
        setTitle(supplier == null ? "Novo Fornecedor" : "Editar Fornecedor");
        initModality(Modality.APPLICATION_MODAL);

        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setPadding(new Insets(10));

        Label lblCnpj = new Label("CNPJ:");
        txtCnpj = new TextField();
        Label lblName = new Label("Nome:");
        txtName = new TextField();
        Label lblContact = new Label("Contato:");
        txtContact = new TextField();

        if(supplier != null) {
            txtCnpj.setText(supplier.getCNPJ());
            txtCnpj.setEditable(false);
            txtName.setText(supplier.getName());
            txtContact.setText(supplier.getContact());
        }

        Button btnSave = new Button("Salvar");
        btnSave.setOnAction(e -> saveSupplier());
        Button btnCancel = new Button("Cancelar");
        btnCancel.setOnAction(e -> close());

        gp.addRow(0, lblCnpj, txtCnpj);
        gp.addRow(1, lblName, txtName);
        gp.addRow(2, lblContact, txtContact);
        gp.addRow(3, btnSave, btnCancel);

        Scene scene = new Scene(gp, 400, 250);
        setScene(scene);
    }

    private void saveSupplier() {
        try {
            String cnpj = txtCnpj.getText();
            String name = txtName.getText();
            String contact = txtContact.getText();

            boolean ok;
            if(supplier == null) {
                Supplier newSupplier = new Supplier(cnpj, name, contact);
                ok = supplierDAO.insert(newSupplier);
            } else {
                supplier.setName(name);
                supplier.setCNPJ(cnpj);
                supplier.setContact(contact);
                ok = supplierDAO.update(supplier);
            }
            if(ok) {
                showAlert("Fornecedor salvo.");
                if(onSave != null) onSave.run();
                close();
            } else {
                showAlert("Erro ao salvar fornecedor.");
            }
        } catch (NumberFormatException ex) {
            // TODO: IDs devem ser autoincrementados
            showAlert("ID inválido. Por favor, digite apenas números inteiros positivos.");
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
