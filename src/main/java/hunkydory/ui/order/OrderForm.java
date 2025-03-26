package hunkydory.ui.order;

import hunkydory.dao.OrderDAO;
import hunkydory.model.Order;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.math.BigDecimal;
import java.time.LocalDate;

public class OrderForm extends Stage {
    private final TextField txtId;
    private final DatePicker dpOrderDate;
    private final DatePicker dpDeliveryDate;
    private final TextField txtStatus;
    private final TextField txtCustomerId;

    private Runnable onSave;
    private final OrderDAO orderDAO;
    private final Order order;

    public OrderForm(Order order, OrderDAO dao) {
        this.order = order;
        this.orderDAO = dao;
        setTitle(order == null ? "Novo Pedido" : "Editar Pedido");
        initModality(Modality.APPLICATION_MODAL);

        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setPadding(new Insets(10));

        Label lblId = new Label("ID do Pedido:");
        txtId = new TextField();
        Label lblOrderDate = new Label("Data do Pedido:");
        dpOrderDate = new DatePicker();
        Label lblDeliveryDate = new Label("Data de Entrega:");
        dpDeliveryDate = new DatePicker();
        Label lblStatus = new Label("Status:");
        txtStatus = new TextField();
        Label lblCustomer = new Label("ID do Cliente:");
        txtCustomerId = new TextField();

        if (order != null) {
            txtId.setText(String.valueOf(order.getOrderID()));
            txtId.setDisable(true);
            dpOrderDate.setValue(order.getOrderDate());
            dpDeliveryDate.setValue(order.getDeliveryDate());
            txtStatus.setText(order.getStatus());
            txtCustomerId.setText(String.valueOf(order.getCustomerID()));
        }

        Button btnSave = new Button("Salvar");
        btnSave.setOnAction(e -> saveOrder());
        Button btnCancel = new Button("Cancelar");
        btnCancel.setOnAction(e -> close());

        gp.addRow(0, lblId, txtId);
        gp.addRow(1, lblOrderDate, dpOrderDate);
        gp.addRow(2, lblDeliveryDate, dpDeliveryDate);
        gp.addRow(3, lblStatus, txtStatus);
        gp.addRow(4, lblCustomer, txtCustomerId);
        gp.addRow(5, btnSave, btnCancel);

        Scene scene = new Scene(gp, 400, 350);
        setScene(scene);
    }

    private void saveOrder() {
        try {
            int id = Integer.parseInt(txtId.getText());
            LocalDate orderDate = dpOrderDate.getValue();
            LocalDate deliveryDate = dpDeliveryDate.getValue();
            String status = txtStatus.getText();
            int customerId = Integer.parseInt(txtCustomerId.getText());

            boolean ok;
            if (order == null) {
                Order newOrder = new Order(id, customerId, status, BigDecimal.ZERO, orderDate, deliveryDate);
                ok = orderDAO.insert(newOrder);
            } else {
                order.setOrderDate(orderDate);
                order.setDeliveryDate(deliveryDate);
                order.setStatus(status);
                order.setCustomerID(customerId);
                ok = orderDAO.update(order);
            }
            if (ok) {
                showAlert("Pedido salvo com sucesso!");
                if (onSave != null) onSave.run();
                close();
            } else {
                showAlert("Erro ao salvar pedido.");
            }
        } catch (NumberFormatException ex) {
            showAlert("Entrada numérica inválida.");
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.showAndWait();
    }

    public void setOnSave(Runnable onSave) {
        this.onSave = onSave;
    }
}
