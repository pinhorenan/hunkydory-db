package hunkydory.ui.product;

import hunkydory.dao.ProductDAO;
import hunkydory.model.Product;
import hunkydory.ui.MainScreen;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;

public class ProductScreen extends VBox {
    private final TableView<Product> tableView;
    private final ObservableList<Product> data;
    private final ProductDAO productDAO = new ProductDAO();

    public ProductScreen(Stage mainStage) {
        setSpacing(10);
        setPadding(new Insets(10));

        Label title = new Label("Catálogo de Produtos");
        title.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        VBox.setMargin(title, new Insets(0, 0, 5, 0));

        tableView = new TableView<>();
        data = FXCollections.observableArrayList();
        tableView.setItems(data);

        TableColumn<Product, Integer> colID = new TableColumn<>("ID");
        colID.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getProductID()));
        colID.setPrefWidth(50);
        colID.setMinWidth(10);
        colID.setMaxWidth(70);

        TableColumn<Product, String> colName = new TableColumn<>("Nome");
        colName.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getName()));

        TableColumn<Product, String> colCategory = new TableColumn<>("ID categoria");
        colCategory.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(String.valueOf(cellData.getValue().getCategoryID())));

        TableColumn<Product, String> colPrice = new TableColumn<>("Preço");
        colPrice.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getPrice().toString()));

        //noinspection unchecked
        tableView.getColumns().addAll(colID, colName, colCategory, colPrice);
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(tableView, Priority.ALWAYS);

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

        Button btnDelete = new Button("Excluir");
        btnDelete.setOnAction(e -> {
            Product selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                boolean ok = productDAO.delete(selected.getProductID());
                if (ok) {
                    showAlert("Produto excluído.");
                    loadData();
                } else {
                    showAlert("Erro ao excluír produto.");
                }
            } else {
                showAlert("Por favor, selecione o produto para excluir.");
            }
        });

        Button btnBack = new Button("Voltar");
        btnBack.setOnAction(e -> mainStage.getScene().setRoot(new MainScreen(mainStage)));

        HBox hboxButtons = new HBox(10, btnNew, btnEdit, btnDelete, btnBack);
        hboxButtons.setAlignment(Pos.CENTER);

        getChildren().addAll(title, tableView, hboxButtons);
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
