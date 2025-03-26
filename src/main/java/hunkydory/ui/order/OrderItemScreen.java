package hunkydory.ui.order;

import hunkydory.dao.OrderItemDAO;
import hunkydory.model.OrderItem;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.List;

public class OrderItemScreen extends Stage {
    private final TableView<OrderItem> tableView;
    private final ObservableList<OrderItem> data;
    private final OrderItemDAO orderItemDAO = new OrderItemDAO();
    private final int orderId;

    @SuppressWarnings("unchecked")
    public OrderItemScreen(int orderId) {
        this.orderId = orderId;
        setTitle("Gerenciar Itens da Compra (Pedido: " + orderId + ")");
        initModality(Modality.APPLICATION_MODAL);

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        tableView = new TableView<>();
        data = FXCollections.observableArrayList();
        tableView.setItems(data);

        TableColumn<OrderItem, Integer> colOrderID = new TableColumn<>("ID Pedido");
        colOrderID.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getOrderID()));

        TableColumn<OrderItem, Integer> colProductID = new TableColumn<>("ID Produto");
        colProductID.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getProductID()));

        TableColumn<OrderItem, Integer> colQuantity = new TableColumn<>("Quantidade");
        colQuantity.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getQuantity()));

        TableColumn<OrderItem, String> colUnitPrice = new TableColumn<>("Preço unitário");
        colUnitPrice.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getUnitPrice().toString()));

        tableView.getColumns().addAll(colOrderID, colProductID, colQuantity, colUnitPrice);
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        TitledPane titledPane = new TitledPane("Items do pedido", tableView);
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

        Button btnDelete = new Button("Excluir Item");
        btnDelete.setOnAction(e -> {
            OrderItem selected = tableView.getSelectionModel().getSelectedItem();
            if(selected != null) {
                boolean ok = orderItemDAO.delete(selected.getOrderID(), selected.getProductID());
                if(ok) {
                    showAlert("Item excluído.");
                    loadData();
                } else {
                    showAlert("Erro ao excluir item.");
                }
            } else {
                showAlert("Selecione um item para excluir.");
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
            if (item.getOrderID() == orderId) {
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
