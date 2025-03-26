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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;

public class InventoryScreen extends VBox {
    @SuppressWarnings("FieldCanBeLocal")
    private final TableView<ObservableList<String>> tableView;
    private final ObservableList<ObservableList<String>> data;
    private final ProductDAO productDAO = new ProductDAO();

    @SuppressWarnings("unchecked")
    public InventoryScreen(Stage mainStage) {
        setSpacing(10);
        setPadding(new Insets(10));

        tableView = new TableView<>();
        data = FXCollections.observableArrayList();
        tableView.setItems(data);

        TableColumn<ObservableList<String>, String> colID = new TableColumn<>("ID");
        colID.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFirst()));
        colID.setPrefWidth(50);

        TableColumn<ObservableList<String>, String> colProductName = new TableColumn<>("Product Name");
        colProductName.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().get(1)));

        TableColumn<ObservableList<String>, String> colQuantity = new TableColumn<>("Quantity Available");
        colQuantity.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().get(2)));

        tableView.getColumns().addAll(colID, colProductName, colQuantity);
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        TitledPane titledPane = new TitledPane("Inventory", tableView);
        titledPane.setCollapsible(false);
        VBox.setVgrow(titledPane, Priority.ALWAYS);

        Button btnUpdate = new Button("Update Inventory");
        btnUpdate.setOnAction(e -> loadData());

        Button btnBack = new Button("Back");
        btnBack.setOnAction(e -> mainStage.getScene().setRoot(new MainScreen(mainStage)));

        HBox hboxButtons = new HBox(10, btnUpdate, btnBack);
        hboxButtons.setPadding(new Insets(10));
        hboxButtons.setAlignment(Pos.CENTER);

        getChildren().addAll(titledPane, hboxButtons);
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
