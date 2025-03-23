package hunkydory.ui.order;

import hunkydory.dao.OrderDAO;
import hunkydory.model.Order;
import hunkydory.ui.MainScreen;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrderScreen extends VBox {
    private final TableView<Order> tableView;
    private final ObservableList<Order> data;
    private final OrderDAO orderDAO = new OrderDAO();

    public OrderScreen(Stage mainStage) {
        setSpacing(10);
        setPadding(new Insets(10));

        // Criação da TableView
        tableView = new TableView<>();
        data = FXCollections.observableArrayList();
        tableView.setItems(data);

        // Colunas
        TableColumn<Order, Integer> colID = new TableColumn<>("ID");
        colID.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getIdOrder()));
        colID.setPrefWidth(50);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        TableColumn<Order, String> colDate = new TableColumn<>("Data do Pedido");
        colDate.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getOrderDate().format(dtf)));

        TableColumn<Order, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getStatus()));

        TableColumn<Order, Integer> colCustomerID = new TableColumn<>("ID do Cliente");
        colCustomerID.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCustomerId()));

        tableView.getColumns().addAll(colID, colDate, colStatus, colCustomerID);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // TitledPane
        TitledPane titledPane = new TitledPane("Gerenciar Pedidos", tableView);
        titledPane.setCollapsible(false);
        VBox.setVgrow(titledPane, Priority.ALWAYS);

        // Botões
        Button btnNew = new Button("Novo pedido");
        btnNew.setOnAction(e -> openForm(null));

        Button btnEdit = new Button("Editar");
        btnEdit.setOnAction(e -> {
            Order selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                openForm(selected);
            } else {
                showAlert("Por favor, selecione um pedido para editar.");
            }
        });

        Button btnDelete = new Button("Deletar");
        btnDelete.setOnAction(e -> {
            Order selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                boolean ok = orderDAO.delete(selected.getIdOrder());
                if(ok) {
                    showAlert("Pedido deletado.");
                    loadData();
                } else {
                    showAlert("Erro ao deletar pedido.");
                }
            } else {
                showAlert("Por favor, selecione um pedido para deletar.");
            }
        });

        Button btnManageItems = new Button("Gerenciar Itens");
        btnManageItems.setOnAction(e -> {
            Order selected = tableView.getSelectionModel().getSelectedItem();
            if(selected != null) {
                OrderItemScreen itemScreen = new OrderItemScreen(selected.getIdOrder());
                itemScreen.showAndWait();
            } else {
                showAlert("Por favor, selecione um pedido para gerenciar os itens.");
            }
        });

        Button btnBack = new Button("Voltar");
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
        form.setOnSave(() -> loadData());
        form.showAndWait();
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.showAndWait();
    }
}
