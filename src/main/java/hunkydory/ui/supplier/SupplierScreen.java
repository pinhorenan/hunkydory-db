package hunkydory.ui.supplier;

import hunkydory.dao.SupplierDAO;
import hunkydory.model.Supplier;
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

public class SupplierScreen extends VBox {
    private final TableView<Supplier> tableView;
    private final ObservableList<Supplier> data;
    private final SupplierDAO supplierDAO = new SupplierDAO();

    public SupplierScreen(Stage mainStage) {
        setSpacing(10);
        setPadding(new Insets(10));

        Label title = new Label("Fornecedores");
        title.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        VBox.setMargin(title, new Insets(0, 0, 5, 0));

        tableView = new TableView<>();
        data = FXCollections.observableArrayList();
        tableView.setItems(data);

        TableColumn<Supplier, Integer> colID = new TableColumn<>("ID");
        colID.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getSupplierID()));

        TableColumn<Supplier, String> colName = new TableColumn<>("Nome");
        colName.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getName()));

        TableColumn<Supplier, String> colContact = new TableColumn<>("Contato");
        colContact.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getContact()));

        //noinspection unchecked
        tableView.getColumns().addAll(colID, colName, colContact);
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(tableView, Priority.ALWAYS);

        Button btnNew = new Button("Novo Fornecedor");
        btnNew.setOnAction(e -> openForm(null));

        Button btnEdit = new Button("Editar");
        btnEdit.setOnAction(e -> {
            Supplier selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                openForm(selected);
            } else {
                showAlert("Por favor, selecione um fornecedor para editar.");
            }
        });

        Button btnDelete = new Button("Excluir");
        btnDelete.setOnAction(e -> {
            Supplier selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                boolean ok = supplierDAO.delete(selected.getSupplierID());
                if(ok) {
                    showAlert("Fornecedor excluído.");
                    loadData();
                } else {
                    showAlert("Erro ao excluir fornecedor.");
                }
            } else {
                showAlert("Por favor, selecione um fornecedor para excluir.");
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
