package hunkydory.ui.customer;

import hunkydory.dao.CustomerAddressDAO;
import hunkydory.model.CustomerAddress;
import hunkydory.ui.MainScreen;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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

public class CustomerAddressScreen extends VBox {
    private final TableView<CustomerAddress> tableView;
    private final ObservableList<CustomerAddress> data;
    private final CustomerAddressDAO customerAddressDAO = new CustomerAddressDAO();

    public CustomerAddressScreen(Stage mainStage) {
        setSpacing(10);
        setPadding(new Insets(10));

        Label title = new Label("Endereços dos Clientes");
        title.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        VBox.setMargin(title, new Insets(0, 0, 5, 0));

        tableView = new TableView<>();
        data = FXCollections.observableArrayList();
        tableView.setItems(data);

        TableColumn<CustomerAddress, Integer> colID = new TableColumn<>("ID");
        colID.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getAddressID()));
        colID.setPrefWidth(50);

        TableColumn<CustomerAddress, String> colStreet = new TableColumn<>("Rua");
        colStreet.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStreet()));

        TableColumn<CustomerAddress, String> colNumber = new TableColumn<>("Número");
        colNumber.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNumber()));

        TableColumn<CustomerAddress, String> colCity = new TableColumn<>("Cidade");
        colCity.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCity()));

        TableColumn<CustomerAddress, String> colState = new TableColumn<>("Estado");
        colState.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getState()));

        TableColumn<CustomerAddress, String> colZip = new TableColumn<>("CEP");
        colZip.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getZipCode()));

        TableColumn<CustomerAddress, String> colComplement = new TableColumn<>("Complemento");
        colComplement.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getComplement()));

        TableColumn<CustomerAddress, Integer> colCustomerID = new TableColumn<>("ID do Cliente");
        colCustomerID.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCustomerID()));

        //noinspection unchecked
        tableView.getColumns().addAll(colID, colStreet, colNumber, colCity, colState, colZip, colComplement, colCustomerID);
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(tableView, Priority.ALWAYS);

        Button btnNovo = new Button("Novo Endereço");
        btnNovo.setOnAction(e -> openForm(null));

        Button btnEditar = new Button("Editar Endereço");
        btnEditar.setOnAction(e -> {
            CustomerAddress selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                openForm(selected);
            } else {
                showAlert("Selecione um endereço para editar.");
            }
        });

        Button btnExcluir = new Button("Excluir Endereço");
        btnExcluir.setOnAction(e -> {
            CustomerAddress selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                boolean ok = customerAddressDAO.delete(selected.getAddressID());
                if (ok) {
                    showAlert("Endereço excluído.");
                    loadData();
                } else {
                    showAlert("Erro ao excluir endereço.");
                }
            } else {
                showAlert("Selecione um endereço para excluir.");
            }
        });

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(e -> mainStage.getScene().setRoot(new MainScreen(mainStage)));

        HBox hboxButtons = new HBox(10, btnNovo, btnEditar, btnExcluir, btnVoltar);
        hboxButtons.setAlignment(Pos.CENTER);

        getChildren().addAll(title, tableView, hboxButtons);
        loadData();
    }

    private void loadData() {
        data.clear();
        List<CustomerAddress> list = customerAddressDAO.listAll();
        data.addAll(list);
    }

    private void openForm(CustomerAddress address) {
        CustomerAddressForm form = new CustomerAddressForm(address, customerAddressDAO);
        form.setOnSave(this::loadData);
        form.showAndWait();
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.showAndWait();
    }
}