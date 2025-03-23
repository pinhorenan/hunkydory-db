package hunkydory.ui.order;

import hunkydory.dao.OrderItemDAO;
import hunkydory.model.OrderItem;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class OrderItemScreen extends Stage {
    private final TableView<OrderItem> tableView;
    private final ObservableList<OrderItem> data;
    private final OrderItemDAO orderItemDAO = new OrderItemDAO();
    private int orderId;

    public OrderItemScreen(int orderId) {
        this.orderId = orderId;
        setTitle("Gerenciar Itens do Pedido (Pedido " + orderId + ")");
        initModality(Modality.APPLICATION_MODAL);

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        tableView = new TableView<>();
        data = FXCollections.observableArrayList();
        tableView.setItems(data);

        TableColumn<OrderItem, Integer> colOrderID = new TableColumn<>("ID do Pedido");
        colOrderID.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getOrderId()));

        TableColumn<OrderItem, Integer> colProductID = new TableColumn<>("ID do Produto");
        colProductID.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getProductId()));

        TableColumn<OrderItem, Integer> colQuantity = new TableColumn<>("Qtd.");
        colQuantity.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getQuantity()));

        TableColumn<OrderItem, String> colUnitPrice = new TableColumn<>("Preço Unitário");
        colUnitPrice.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getUnitPrice().toString()));

        tableView.getColumns().addAll(colOrderID, colProductID, colQuantity, colUnitPrice);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // TitledPane
        TitledPane titledPane = new TitledPane("Itens do Pedido", tableView);
        titledPane.setCollapsible(false);
        VBox.setVgrow(titledPane, Priority.ALWAYS);

        Button btnNew = new Button("Novo Item");
        btnNew.setOnAction(e -> openForm(null));

        Button btnEdit = new Button("Editar Item");
        btnEdit.setOnAction(e -> {
            OrderItem selected = tableView.getSelectionModel().getSelectedItem();
            if(selected != null) {
                openForm(selected);
            } else {
                showAlert("Selecione um item para editar.");
            }
        });

        Button btnDelete = new Button("Deletar Item");
        btnDelete.setOnAction(e -> {
            OrderItem selected = tableView.getSelectionModel().getSelectedItem();
            if(selected != null) {
                boolean ok = orderItemDAO.delete(selected.getOrderId(), selected.getProductId());
                if(ok) {
                    showAlert("Item deletado.");
                    loadData();
                } else {
                    showAlert("Erro ao deletar item.");
                }
            } else {
                showAlert("Selecione um item para deletar.");
            }
        });

        Button btnClose = new Button("Fechar");
        btnClose.setOnAction(e -> close());

        HBox hboxButtons = new HBox(10, btnNew, btnEdit, btnDelete, btnClose);

        root.getChildren().addAll(titledPane, hboxButtons);
        Scene scene = new Scene(root, 550, 400);
        setScene(scene);
        loadData();
    }

    private void loadData() {
        data.clear();
        List<OrderItem> list = orderItemDAO.listAll();
        for (OrderItem item : list) {
            if (item.getOrderId() == orderId) {
                data.add(item);
            }
        }
    }

    private void openForm(OrderItem item) {
        OrderItemForm form = new OrderItemForm(orderId, item, orderItemDAO);
        form.setOnSave(this::loadData);
        form.showAndWait();
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.showAndWait();
    }
}
