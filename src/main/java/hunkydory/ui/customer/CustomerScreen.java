package hunkydory.ui.customer;

import hunkydory.dao.CustomerDAO;
import hunkydory.model.Customer;
import hunkydory.ui.MainScreen;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import java.util.List;

public class CustomerScreen extends VBox {
    private final TableView<Customer> tableView;
    private final ObservableList<Customer> data;
    private final CustomerDAO customerDAO = new CustomerDAO();

    @SuppressWarnings("unchecked")
    public CustomerScreen(Stage mainStage) {
        setSpacing(10);
        setPadding(new Insets(10));

        tableView = new TableView<>();
        data = FXCollections.observableArrayList();
        tableView.setItems(data);

        TableColumn<Customer, Integer> colID = new TableColumn<>("ID");
        colID.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCustomerID()));
        colID.setPrefWidth(50);
        colID.setMinWidth(50);
        colID.setMaxWidth(70);

        TableColumn<Customer, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getName()));

        TableColumn<Customer, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getEmail()));

        TableColumn<Customer, String> colPhone = new TableColumn<>("Phone");
        colPhone.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getPhone()));

        tableView.getColumns().addAll(colID, colName, colEmail, colPhone);
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(tableView, Priority.ALWAYS);

        TitledPane titledPane = new TitledPane("Registered Customers", tableView);
        titledPane.setCollapsible(false);
        VBox.setVgrow(titledPane, Priority.ALWAYS);

        Button btnNew = new Button("New Customer");
        btnNew.setOnAction(e -> openForm(null));

        Button btnEdit = new Button("Edit");
        btnEdit.setOnAction(e -> {
            Customer selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                openForm(selected);
            } else {
                showAlert("Select a customer to edit.");
            }
        });

        Button btnDelete = new Button("Delete");
        btnDelete.setOnAction(e -> {
            Customer selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                boolean ok = customerDAO.delete(selected.getCustomerID());
                if (ok) {
                    showAlert("Customer deleted successfully!");
                    loadData();
                } else {
                    showAlert("Error deleting customer.");
                }
            } else {
                showAlert("Select a customer to delete.");
            }
        });

        Button btnBack = new Button("Back");
        btnBack.setOnAction(e -> mainStage.getScene().setRoot(new MainScreen(mainStage)));

        HBox hboxButtons = new HBox(10, btnNew, btnEdit, btnDelete, btnBack);
        hboxButtons.setAlignment(Pos.CENTER);

        getChildren().addAll(titledPane, hboxButtons);
        loadData();
    }

    private void loadData() {
        data.clear();
        List<Customer> list = customerDAO.listAll();
        data.addAll(list);
    }

    private void openForm(Customer customer) {
        CustomerForm form = new CustomerForm(customer, customerDAO);
        form.setOnSave(this::loadData);
        form.showAndWait();
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.showAndWait();
    }
}
