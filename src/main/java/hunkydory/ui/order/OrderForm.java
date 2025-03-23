package hunkydory.ui.order;

import hunkydory.dao.OrderDAO;
import hunkydory.model.Order;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.time.LocalDate;

public class OrderForm extends Stage {
    private TextField txtId;
    private DatePicker dpOrderDate;
    private TextField txtStatus;
    private TextField txtCustomerId;

    private Runnable onSave;
    private OrderDAO orderDAO;
    private Order order; // null = new order

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
        Label lblDate = new Label("Data do Pedido:");
        dpOrderDate = new DatePicker();
        Label lblStatus = new Label("Status:");
        txtStatus = new TextField();
        Label lblCustomer = new Label("ID do Cliente:");
        txtCustomerId = new TextField();

        if(order != null) {
            txtId.setText(String.valueOf(order.getIdOrder()));
            txtId.setDisable(true);
            dpOrderDate.setValue(order.getOrderDate());
            txtStatus.setText(order.getStatus());
            txtCustomerId.setText(String.valueOf(order.getCustomerId()));
        }

        Button btnSave = new Button("Salvar");
        btnSave.setOnAction(e -> saveOrder());
        Button btnCancel = new Button("Cancelar");
        btnCancel.setOnAction(e -> close());

        gp.addRow(0, lblId, txtId);
        gp.addRow(1, lblDate, dpOrderDate);
        gp.addRow(2, lblStatus, txtStatus);
        gp.addRow(3, lblCustomer, txtCustomerId);
        gp.addRow(4, btnSave, btnCancel);

        Scene scene = new Scene(gp, 400, 300);
        setScene(scene);
    }

    private void saveOrder() {
        try {
            int id = Integer.parseInt(txtId.getText());
            LocalDate orderDate = dpOrderDate.getValue();
            String status = txtStatus.getText();
            int customerId = Integer.parseInt(txtCustomerId.getText());

            boolean ok;
            if(order == null) {
                Order newOrder = new Order(id, orderDate, status, customerId);
                ok = orderDAO.insert(newOrder);
            } else {
                order.setOrderDate(orderDate);
                order.setStatus(status);
                order.setCustomerId(customerId);
                ok = orderDAO.update(order);
            }
            if(ok) {
                showAlert("Pedido salvo com sucesso!");
                if(onSave != null) onSave.run();
                close();
            } else {
                showAlert("Erro salvando pedido.");
            }
        } catch (NumberFormatException ex) {
            showAlert("Input numérico inválido.");
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
