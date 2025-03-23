package hunkydory.ui.product;

import hunkydory.dao.ProductDAO;
import hunkydory.model.Product;
import hunkydory.ui.MainScreen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;

public class ProductScreen extends VBox {
    private final TableView<Product> tableView;
    private final ObservableList<Product> data;
    private final ProductDAO productDAO = new ProductDAO();

    public ProductScreen(Stage mainStage) {
        setSpacing(10);
        setPadding(new Insets(10));

        // Criação da TableView
        tableView = new TableView<>();
        data = FXCollections.observableArrayList();
        tableView.setItems(data);

        // Colunas
        TableColumn<Product, Integer> colID = new TableColumn<>("ID");
        colID.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getIdProduct()));
        colID.setPrefWidth(50);
        colID.setMinWidth(10);
        colID.setMaxWidth(70);

        TableColumn<Product, String> colName = new TableColumn<>("Nome");
        colName.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getName()));

        TableColumn<Product, String> colCategory = new TableColumn<>("Categoria");
        colCategory.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getCategory()));

        TableColumn<Product, String> colPrice = new TableColumn<>("Preço");
        colPrice.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getPrice().toString()));

        tableView.getColumns().addAll(colID, colName, colCategory, colPrice);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // TitledPane
        TitledPane titledPane = new TitledPane("Catálogo de Produtos", tableView);
        titledPane.setCollapsible(false);
        VBox.setVgrow(titledPane, Priority.ALWAYS);

        // Botões
        Button btnNew = new Button("Novo Produto");
        btnNew.setOnAction(e -> openForm(null));

        Button btnEdit = new Button("Editar");
        btnEdit.setOnAction(e -> {
            Product selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                openForm(selected);
            } else {
                showAlert("Por favor, selecione um produto para editar.");
            }
        });

        Button btnDelete = new Button("Deletar");
        btnDelete.setOnAction(e -> {
            Product selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                boolean ok = productDAO.delete(selected.getIdProduct());
                if (ok) {
                    showAlert("Produto deletado.");
                    loadData();
                } else {
                    showAlert("Erro ao deletar produto.");
                }
            } else {
                showAlert("Por favor, selecione um produto para deletar.");
            }
        });

        Button btnBack = new Button("Voltar");
        btnBack.setOnAction(e -> mainStage.getScene().setRoot(new MainScreen(mainStage)));

        HBox hboxButtons = new HBox(10, btnNew, btnEdit, btnDelete, btnBack);

        getChildren().addAll(titledPane, hboxButtons);
        loadData();
    }

    private void loadData() {
        data.clear();
        List<Product> list = productDAO.listAll();
        data.addAll(list);
    }

    private void openForm(Product product) {
        ProductForm form = new ProductForm(product, productDAO);
        form.setOnSave(this::loadData);
        form.showAndWait();
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.showAndWait();
    }
}
