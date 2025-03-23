package hunkydory.ui.product;

import hunkydory.dao.ProductDAO;
import hunkydory.model.Product;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.math.BigDecimal;

public class ProductForm extends Stage {
    private TextField txtId;
    private TextField txtName;
    private TextField txtPrice;
    private TextField txtDescription;
    private TextField txtCategory;
    private TextField txtSupplierCNPJ;

    private Runnable onSave;
    private ProductDAO productDAO;
    private Product product; // null indicates new product

    public ProductForm(Product product, ProductDAO dao) {
        this.product = product;
        this.productDAO = dao;

        setTitle(product == null ? "Novo Produto" : "Editar Produto");
        initModality(Modality.APPLICATION_MODAL);

        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setPadding(new Insets(10));

        Label lblId = new Label("ID:"); // TODO: Deve ser autoincrementado
        txtId = new TextField();

        Label lblName = new Label("Nome:");
        txtName = new TextField();

        Label lblPrice = new Label("Preço:");
        txtPrice = new TextField();

        Label lblDescription = new Label("Descrição:");
        txtDescription = new TextField();

        Label lblCategory = new Label("Categoria:");
        txtCategory = new TextField();

        Label lblSupplierId = new Label("ID do Fornecedor:");
        txtSupplierCNPJ = new TextField(); // TODO: Checar se existe fornecedor com esse ID

        if(product != null) {
            txtId.setText(String.valueOf(product.getIdProduct()));
            txtId.setDisable(true);
            txtName.setText(product.getName());
            txtPrice.setText(product.getPrice().toString());
            txtDescription.setText(product.getDescription());
            txtCategory.setText(product.getCategory());
            txtSupplierCNPJ.setText(product.getSupplierCNPJ());
        }

        Button btnSave = new Button("Salvar");
        btnSave.setOnAction(e -> saveProduct());
        Button btnCancel = new Button("Cancelar");
        btnCancel.setOnAction(e -> close());

        gp.addRow(0, lblId, txtId);
        gp.addRow(1, lblName, txtName);
        gp.addRow(2, lblPrice, txtPrice);
        gp.addRow(3, lblDescription, txtDescription);
        gp.addRow(4, lblCategory, txtCategory);
        gp.addRow(5, lblSupplierId, txtSupplierCNPJ);
        gp.addRow(6, btnSave, btnCancel);

        Scene scene = new Scene(gp, 400, 300);
        setScene(scene);
    }

    private void saveProduct() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String name = txtName.getText();
            BigDecimal price = new BigDecimal(txtPrice.getText());
            String description = txtDescription.getText();
            String category = txtCategory.getText();
            String supplierCNPJ = txtSupplierCNPJ.getText();

            boolean ok;
            if(product == null) {
                Product newProduct = new Product(id, name, price, description, category, supplierCNPJ);
                ok = productDAO.insert(newProduct);
            } else {
                product.setName(name);
                product.setPrice(price);
                product.setDescription(description);
                product.setCategory(category);
                product.setSupplierCNPJ(supplierCNPJ);
                ok = productDAO.update(product);
            }

            if(ok) {
                showAlert("Produto salvo.");
                if(onSave != null) onSave.run();
                close();
            } else {
                showAlert("Erro ao salvar produto.");
            }
        } catch (NumberFormatException ex) {
            showAlert("Input numérico inválido.");
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
