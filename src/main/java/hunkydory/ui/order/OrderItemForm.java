package hunkydory.ui.order;

import hunkydory.dao.OrderItemDAO;
import hunkydory.model.OrderItem;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.math.BigDecimal;

public class OrderItemForm extends Stage {
    private TextField txtProductId;
    private TextField txtQuantity;
    private TextField txtUnitPrice;

    private Runnable onSave;
    private OrderItemDAO orderItemDAO;
    private OrderItem orderItem; // can be null for new
    private int orderId;

    public OrderItemForm(int orderId, OrderItem item, OrderItemDAO dao) {
        this.orderId = orderId;
        this.orderItem = item;
        this.orderItemDAO = dao;
        setTitle(item == null ? "Novo item" : "Editar item");
        initModality(Modality.APPLICATION_MODAL);

        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setPadding(new Insets(10));

        Label lblProductId = new Label("ID do Produto:");
        txtProductId = new TextField();
        Label lblQuantity = new Label("Quantidade:");
        txtQuantity = new TextField();
        Label lblUnitPrice = new Label("Preço Unitário:");
        txtUnitPrice = new TextField();

        if(orderItem != null) {
            txtProductId.setText(String.valueOf(orderItem.getProductId()));
            txtProductId.setDisable(true); // Não pode alterar ID
            txtQuantity.setText(String.valueOf(orderItem.getQuantity()));
            txtUnitPrice.setText(orderItem.getUnitPrice().toString());
        }

        Button btnSave = new Button("Salvar");
        btnSave.setOnAction(e -> saveItem());
        Button btnCancel = new Button("Cancelar");
        btnCancel.setOnAction(e -> close());

        gp.addRow(0, lblProductId, txtProductId);
        gp.addRow(1, lblQuantity, txtQuantity);
        gp.addRow(2, lblUnitPrice, txtUnitPrice);
        gp.addRow(3, btnSave, btnCancel);

        Scene scene = new Scene(gp, 400, 250);
        setScene(scene);
    }

    private void saveItem() {
        try {
            int productId = Integer.parseInt(txtProductId.getText());
            int quantity = Integer.parseInt(txtQuantity.getText());
            BigDecimal unitPrice = new BigDecimal(txtUnitPrice.getText());

            boolean ok;
            if(orderItem == null) {
                OrderItem newItem = new OrderItem(orderId, productId, quantity, unitPrice);
                ok = orderItemDAO.insert(newItem);
            } else {
                orderItem.setQuantity(quantity);
                orderItem.setUnitPrice(unitPrice);
                ok = orderItemDAO.update(orderItem);
            }
            if(ok) {
                showAlert("Item salvo.");
                if(onSave != null) onSave.run();
                close();
            } else {
                showAlert("Erro ao salvar item.");
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
