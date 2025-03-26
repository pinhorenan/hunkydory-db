package hunkydory.ui.order;

import hunkydory.dao.OrderDAO;
import hunkydory.model.Order;
import hunkydory.ui.MainScreen;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrderScreen extends VBox {
    private final TableView<Order> tableView;
    private final ObservableList<Order> data;
    private final OrderDAO orderDAO = new OrderDAO();

    @SuppressWarnings("unchecked")
    public OrderScreen(Stage mainStage) {
        setSpacing(10);
        setPadding(new Insets(10));

        tableView = new TableView<>();
        data = FXCollections.observableArrayList();
        tableView.setItems(data);

        TableColumn<Order, Integer> colID = new TableColumn<>("ID");
        colID.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getOrderID()));
        colID.setPrefWidth(50);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        TableColumn<Order, String> colDate = new TableColumn<>("Order Date");
        colDate.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getOrderDate().format(dtf)));

        TableColumn<Order, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getStatus()));

        TableColumn<Order, Integer> colCustomerID = new TableColumn<>("Customer ID");
        colCustomerID.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCustomerID()));

        tableView.getColumns().addAll(colID, colDate, colStatus, colCustomerID);
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        TitledPane titledPane = new TitledPane("Manage Orders", tableView);
        titledPane.setCollapsible(false);
        VBox.setVgrow(titledPane, Priority.ALWAYS);

        Button btnNew = new Button("New Order");
        btnNew.setOnAction(e -> openForm(null));

        Button btnEdit = new Button("Edit");
        btnEdit.setOnAction(e -> {
            Order selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                openForm(selected);
            } else {
                showAlert("Please select an order to edit.");
            }
        });

        Button btnDelete = new Button("Delete");
        btnDelete.setOnAction(e -> {
            Order selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                boolean ok = orderDAO.delete(selected.getOrderID());
                if(ok) {
                    showAlert("Order deleted.");
                    loadData();
                } else {
                    showAlert("Error deleting order.");
                }
            } else {
                showAlert("Please select an order to delete.");
            }
        });

        Button btnManageItems = new Button("Manage Items");
        btnManageItems.setOnAction(e -> {
            Order selected = tableView.getSelectionModel().getSelectedItem();
            if(selected != null) {
                OrderItemScreen itemScreen = new OrderItemScreen(selected.getOrderID());
                itemScreen.showAndWait();
            } else {
                showAlert("Please select an order to manage items.");
            }
        });

        Button btnBack = new Button("Back");
        btnBack.setOnAction(e -> mainStage.getScene().setRoot(new MainScreen(mainStage)));

        HBox hboxButtons = new HBox(10, btnNew, btnEdit, btnDelete, btnManageItems, btnBack);

        getChildren().addAll(titledPane, hboxButtons);
        loadData();
    }

    private void loadData() {
        data.clear();
        List<Order> list = orderDAO.listAll();
        data.addAll(list);
    }

    private void openForm(Order order) {
        OrderForm form = new OrderForm(order, orderDAO);
        form.setOnSave(this::loadData);
        form.showAndWait();
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.showAndWait();
    }
}
