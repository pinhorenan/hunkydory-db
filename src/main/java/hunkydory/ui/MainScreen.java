package hunkydory.ui;

import hunkydory.ui.customer.CustomerScreen;
import hunkydory.ui.order.OrderScreen;
import hunkydory.ui.product.ProductScreen;
import hunkydory.ui.supplier.SupplierScreen;
import hunkydory.ui.inventory.InventoryScreen;
import hunkydory.ui.sql.AdvancedSQLScreen;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainScreen extends BorderPane {

    public MainScreen(Stage mainStage) {
        Button btnCustomers = new Button("Clientes");
        btnCustomers.setOnAction(e -> setCenter(new CustomerScreen(mainStage)));

        Button btnProducts = new Button("Produtos");
        btnProducts.setOnAction(e -> setCenter(new ProductScreen(mainStage)));

        Button btnSuppliers = new Button("Fornecedores");
        btnSuppliers.setOnAction(e -> setCenter(new SupplierScreen(mainStage)));

        Button btnOrders = new Button("Pedidos");
        btnOrders.setOnAction(e -> setCenter(new OrderScreen(mainStage)));

        Button btnInventory = new Button("Estoque");
        btnInventory.setOnAction(e -> setCenter(new InventoryScreen(mainStage)));

        Button btnSQL = new Button("Consultas SQL");
        btnSQL.setOnAction(e -> setCenter(new AdvancedSQLScreen()));

        ToolBar toolBar = new ToolBar(btnCustomers, btnProducts, btnSuppliers, btnOrders, btnInventory, btnSQL);
        toolBar.setPadding(new Insets(10));
        setTop(toolBar);

        Label lblWelcome = new Label("Bem vindo ao Hunkydory!");
        lblWelcome.setStyle("-fx-font-size: 18px;");
        lblWelcome.setAlignment(Pos.CENTER);
        lblWelcome.setMaxWidth(Double.MAX_VALUE);
        lblWelcome.setWrapText(true);
        StackPane welcomePane = new StackPane(lblWelcome);
        welcomePane.setPadding(new Insets(20));
        setCenter(welcomePane);
    }
}
