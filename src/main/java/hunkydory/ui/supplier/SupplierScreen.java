package hunkydory.ui.supplier;

import hunkydory.dao.SupplierDAO;
import hunkydory.model.Supplier;
import hunkydory.ui.MainScreen;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;

public class SupplierScreen extends VBox {
    private TableView<Supplier> tableView;
    private ObservableList<Supplier> data;
    private final SupplierDAO supplierDAO = new SupplierDAO();

    public SupplierScreen(Stage mainStage) {
        setSpacing(10);
        setPadding(new Insets(10));

        // Criação da TableView
        tableView = new TableView<>();
        data = FXCollections.observableArrayList();
        tableView.setItems(data);

        // Definição das colunas
        TableColumn<Supplier, String> colCNPJ = new TableColumn<>("CNPJ");
        colCNPJ.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCNPJ()));

        TableColumn<Supplier, String> colName = new TableColumn<>("Nome");
        colName.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getName()));

        TableColumn<Supplier, String> colContact = new TableColumn<>("Contato");
        colContact.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getContact()));

        tableView.getColumns().addAll(colCNPJ, colName, colContact);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // TitledPane
        TitledPane titledPane = new TitledPane("Fornecedores", tableView);
        titledPane.setCollapsible(false);
        VBox.setVgrow(titledPane, Priority.ALWAYS);

        // Botões
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

        Button btnDelete = new Button("Deletar");
        btnDelete.setOnAction(e -> {
            Supplier selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                boolean ok = supplierDAO.delete(selected.getCNPJ());
                if(ok) {
                    showAlert("Fornecedor deletado.");
                    loadData();
                } else {
                    showAlert("Erro ao deletar fornecedor.");
                }
            } else {
                showAlert("Por favor, selecione um fornecedor para deletar.");
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
