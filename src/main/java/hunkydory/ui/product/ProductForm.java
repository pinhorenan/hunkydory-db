package hunkydory.ui.product;

import hunkydory.dao.ProductDAO;
import hunkydory.model.Product;
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
import java.math.BigDecimal;

public class ProductForm extends Stage {
    private final TextField txtId;
    private final TextField txtName;
    private final TextField txtPrice;
    private final TextField txtStock;
    private final TextField txtDescription;
    private final TextField txtCategory;
    private final TextField txtSupplierID;

    private Runnable onSave;
    private final ProductDAO productDAO;
    private final Product product;

    public ProductForm(Product product, ProductDAO dao) {
        this.product = product;
        this.productDAO = dao;

        setTitle(product == null ? "Novo Produto" : "Editar Produto");
        initModality(Modality.APPLICATION_MODAL);

        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setPadding(new Insets(10));

        Label lblId = new Label("ID:");
        txtId = new TextField();
        Label lblName = new Label("Nome:");
        txtName = new TextField();
        Label lblPrice = new Label("Preço:");
        txtPrice = new TextField();
        Label lblStock = new Label("Estoque:");
        txtStock = new TextField();
        Label lblDescription = new Label("Descrição:");
        txtDescription = new TextField();
        Label lblCategory = new Label("ID da Categoria:");
        txtCategory = new TextField();
        Label lblSupplierID = new Label("ID do Fornecedor:");
        txtSupplierID = new TextField();

        if (product != null) {
            txtId.setText(String.valueOf(product.getProductID()));
            txtId.setDisable(true);
            txtName.setText(product.getName());
            txtPrice.setText(product.getPrice().toString());
            txtStock.setText(String.valueOf(product.getStock()));
            txtDescription.setText(product.getDescription());
            txtCategory.setText(String.valueOf(product.getCategoryID()));
            txtSupplierID.setText(String.valueOf(product.getSupplierID()));
        }

        Button btnSave = new Button("Salvar");
        btnSave.setOnAction(e -> saveProduct());
        Button btnCancel = new Button("Cancelar");
        btnCancel.setOnAction(e -> close());

        gp.addRow(0, lblId, txtId);
        gp.addRow(1, lblName, txtName);
        gp.addRow(2, lblPrice, txtPrice);
        gp.addRow(3, lblStock, txtStock);
        gp.addRow(4, lblDescription, txtDescription);
        gp.addRow(5, lblCategory, txtCategory);
        gp.addRow(6, lblSupplierID, txtSupplierID);
        gp.addRow(7, btnSave, btnCancel);

        Scene scene = new Scene(gp, 400, 350);
        setScene(scene);
    }

    private void saveProduct() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String name = txtName.getText();
            BigDecimal price = new BigDecimal(txtPrice.getText());
            int stock = Integer.parseInt(txtStock.getText());
            String description = txtDescription.getText();
            int categoryId = Integer.parseInt(txtCategory.getText());
            int supplierId = Integer.parseInt(txtSupplierID.getText());

            boolean ok;
            if (product == null) {
                Product newProduct = new Product(id, categoryId, supplierId, name, price, stock, description);
                ok = productDAO.insert(newProduct);
            } else {
                product.setName(name);
                product.setPrice(price);
                product.setStock(stock);
                product.setDescription(description);
                product.setCategoryID(categoryId);
                product.setSupplierID(supplierId);
                ok = productDAO.update(product);
            }

            if (ok) {
                showAlert("Produto salvo.");
                if (onSave != null) onSave.run();
                close();
            } else {
                showAlert("Erro ao salvar produto.");
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
