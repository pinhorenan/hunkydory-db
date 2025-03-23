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
        // Cria os botões para a ToolBar
        Button btnClientes = new Button("Clientes Cadastrados");
        btnClientes.setOnAction(e -> setCenter(new CustomerScreen(mainStage)));

        Button btnProdutos = new Button("Catálogo de Produtos");
        btnProdutos.setOnAction(e -> setCenter(new ProductScreen(mainStage)));

        Button btnFornecedores = new Button("Lista de Fornecedores");
        btnFornecedores.setOnAction(e -> setCenter(new SupplierScreen(mainStage)));

        Button btnPedidos = new Button("Histórico de Pedidos");
        btnPedidos.setOnAction(e -> setCenter(new OrderScreen(mainStage)));

        Button btnEstoque = new Button("Gerenciar Estoque");
        btnEstoque.setOnAction(e -> setCenter(new InventoryScreen(mainStage)));

        Button btnSQL = new Button("SQL Avançado");
        btnSQL.setOnAction(e -> setCenter(new AdvancedSQLScreen(mainStage)));

        // Cria a barra de ferramentas (ToolBar) e adiciona os botões
        ToolBar toolBar = new ToolBar(btnClientes, btnProdutos, btnFornecedores, btnPedidos, btnEstoque, btnSQL);
        toolBar.setPadding(new Insets(10));
        setTop(toolBar);

        // Define a área central com uma mensagem de boas-vindas
        Label lblWelcome = new Label("Bem-vindo ao Painel Administrativo - Hunky Dory!\nSelecione uma opção na barra superior.");
        lblWelcome.setStyle("-fx-font-size: 18px;");
        lblWelcome.setAlignment(Pos.CENTER);
        lblWelcome.setMaxWidth(Double.MAX_VALUE);
        lblWelcome.setWrapText(true);
        StackPane welcomePane = new StackPane(lblWelcome);
        welcomePane.setPadding(new Insets(20));
        setCenter(welcomePane);
    }
}
