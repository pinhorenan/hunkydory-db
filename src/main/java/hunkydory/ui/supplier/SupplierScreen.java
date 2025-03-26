package hunkydory.ui.supplier;

import hunkydory.dao.SupplierDAO;
import hunkydory.model.Supplier;
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
import java.util.List;

public class SupplierScreen extends VBox {
    private final TableView<Supplier> tableView;
    private final ObservableList<Supplier> data;
    private final SupplierDAO supplierDAO = new SupplierDAO();

    public SupplierScreen(Stage mainStage) {
        setSpacing(10);
        setPadding(new Insets(10));

        tableView = new TableView<>();
        data = FXCollections.observableArrayList();
        tableView.setItems(data);

        TableColumn<Supplier, Integer> colID = new TableColumn<>("Supplier ID");
        colID.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getSupplierID()));

        TableColumn<Supplier, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getName()));

        TableColumn<Supplier, String> colContact = new TableColumn<>("Contact");
        colContact.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getContact()));

        //noinspection unchecked
        tableView.getColumns().addAll(colID, colName, colContact);
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        TitledPane titledPane = new TitledPane("Suppliers", tableView);
        titledPane.setCollapsible(false);
        VBox.setVgrow(titledPane, Priority.ALWAYS);

        Button btnNew = new Button("New Supplier");
        btnNew.setOnAction(e -> openForm(null));

        Button btnEdit = new Button("Edit");
        btnEdit.setOnAction(e -> {
            Supplier selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                openForm(selected);
            } else {
                showAlert("Please select a supplier to edit.");
            }
        });

        Button btnDelete = new Button("Delete");
        btnDelete.setOnAction(e -> {
            Supplier selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                boolean ok = supplierDAO.delete(selected.getSupplierID());
                if(ok) {
                    showAlert("Supplier deleted.");
                    loadData();
                } else {
                    showAlert("Error deleting supplier.");
                }
            } else {
                showAlert("Please select a supplier to delete.");
            }
        });

        Button btnBack = new Button("Back");
        btnBack.setOnAction(e -> mainStage.getScene().setRoot(new MainScreen(mainStage)));

        HBox hboxButtons = new HBox(10, btnNew, btnEdit, btnDelete, btnBack);

        getChildren().addAll(titledPane, hboxButtons);
        loadData();
    }

    private void loadData() {
        data.clear();
        List<Supplier> list = supplierDAO.listAll();
        data.addAll(list);
    }

    private void openForm(Supplier supplier) {
        SupplierForm form = new SupplierForm(supplier, supplierDAO);
        form.setOnSave(this::loadData);
        form.showAndWait();
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.showAndWait();
    }
}
