package hunkydory.ui.inventory;

import hunkydory.dao.ProductDAO;
import hunkydory.model.Product;
import hunkydory.ui.MainScreen;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;

public class InventoryScreen extends VBox {
    private final ObservableList<ObservableList<String>> data;
    private final ProductDAO productDAO = new ProductDAO();

    public InventoryScreen(Stage mainStage) {
        setSpacing(10);
        setPadding(new Insets(10));

        Label title = new Label("Estoque");
        title.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        VBox.setMargin(title, new Insets(0, 0, 5, 0));

        TableView<ObservableList<String>> tableView = new TableView<>();
        data = FXCollections.observableArrayList();
        tableView.setItems(data);

        TableColumn<ObservableList<String>, String> colID = new TableColumn<>("ID");
        colID.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFirst()));
        colID.setPrefWidth(50);

        TableColumn<ObservableList<String>, String> colProductName = new TableColumn<>("Nome do Produto");
        colProductName.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().get(1)));

        TableColumn<ObservableList<String>, String> colQuantity = new TableColumn<>("Quantidade Disponível");
        colQuantity.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().get(2)));

        //noinspection unchecked
        tableView.getColumns().addAll(colID, colProductName, colQuantity);
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(tableView, Priority.ALWAYS);

        Button btnUpdate = new Button("Atualizar estoque");
        btnUpdate.setOnAction(e -> loadData());

        Button btnBack = new Button("Voltar");
        btnBack.setOnAction(e -> mainStage.getScene().setRoot(new MainScreen(mainStage)));

        HBox hboxButtons = new HBox(10, btnUpdate, btnBack);
        hboxButtons.setAlignment(Pos.CENTER);

        getChildren().addAll(title, tableView, hboxButtons);
        loadData();
    }

    private void loadData() {
        data.clear();
        List<Product> products = productDAO.listAll();
        for (Product p : products) {
            ObservableList<String> row = FXCollections.observableArrayList();
            row.add(String.valueOf(p.getProductID()));
            row.add(p.getName());
            row.add(String.valueOf(p.getStock()));
            data.add(row);
        }
    }
}
