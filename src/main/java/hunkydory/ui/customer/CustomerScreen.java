package hunkydory.ui.customer;

import hunkydory.dao.CustomerDAO;
import hunkydory.model.Customer;
import hunkydory.ui.MainScreen;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class CustomerScreen extends VBox {

    private final TableView<Customer> tableView;
    private final ObservableList<Customer> data;
    private final CustomerDAO costumerDAO = new CustomerDAO();

    public CustomerScreen(Stage mainStage) {
        setSpacing(10);
        setPadding(new Insets(10));

        // Criação da TableView
        tableView = new TableView<>();
        data = FXCollections.observableArrayList();
        tableView.setItems(data);

        // Colunas da tabela
        TableColumn<Customer, Integer> colID = new TableColumn<>("ID");
        colID.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getIdCustomer()));
        colID.setPrefWidth(50);
        colID.setMinWidth(50);
        colID.setMaxWidth(70);

        TableColumn<Customer, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getName()));

        TableColumn<Customer, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getEmail()));

        TableColumn<Customer, String> colPhone = new TableColumn<>("Telefone");
        colPhone.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getPhone()));

        TableColumn<Customer, String> colAddress = new TableColumn<>("Endereço");
        colAddress.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getAddress()));

        tableView.getColumns().addAll(colID, colNome, colEmail, colPhone, colAddress);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(tableView, Priority.ALWAYS);

        // Envolvendo a TableView num TitledPane para melhor apresentação
        TitledPane titledPane = new TitledPane("Clientes Cadastrados", tableView);
        titledPane.setCollapsible(false);
        VBox.setVgrow(titledPane, Priority.ALWAYS);

        // Botões de ação
        Button btnNew = new Button("Novo Cliente");
        btnNew.setOnAction(e -> openForm(null));

        Button btnEdit = new Button("Editar");
        btnEdit.setOnAction(e -> {
            Customer selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                openForm(selected);
            } else {
                showAlert("Selecione um cliente para editar.");
            }
        });

        Button btnDelete = new Button("Excluir");
        btnDelete.setOnAction(e -> {
            Customer selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                boolean ok = costumerDAO.delete(selected.getIdCustomer());
                if (ok) {
                    showAlert("Cliente excluído com sucesso!");
                    loadData();
                } else {
                    showAlert("Erro ao excluir cliente.");
                }
            } else {
                showAlert("Selecione um cliente para excluir.");
            }
        });

        Button btnBack = new Button("Voltar");
        btnBack.setOnAction(e -> mainStage.getScene().setRoot(new MainScreen(mainStage)));

        HBox hboxButtons = new HBox(10, btnNew, btnEdit, btnDelete, btnBack);
        hboxButtons.setAlignment(Pos.CENTER);

        getChildren().addAll(titledPane, hboxButtons);
        loadData();
    }

    private void loadData() {
        data.clear();
        List<Customer> list = costumerDAO.listAll();
        data.addAll(list);
    }

    private void openForm(Customer c) {
        CustomerForm form = new CustomerForm(c, costumerDAO);
        form.setOnSave(this::loadData);
        form.showAndWait();
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.showAndWait();
    }
}
