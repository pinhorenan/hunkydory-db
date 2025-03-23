package hunkydory.ui.inventory;

import hunkydory.dao.InventoryDAO;
import hunkydory.ui.MainScreen;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class InventoryScreen extends VBox {
    private TableView<ObservableList<String>> tableView;
    private ObservableList<ObservableList<String>> data;
    private final InventoryDAO inventoryDAO = new InventoryDAO();

    public InventoryScreen(Stage mainStage) {
        setSpacing(10);
        setPadding(new Insets(10));

        // TableView
        tableView = new TableView<>();
        data = FXCollections.observableArrayList();
        tableView.setItems(data);

        // Colunas: ID, Nome do Produto, Quantidade
        TableColumn<ObservableList<String>, String> colID = new TableColumn<>("ID");
        colID.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().get(0))
        );
        colID.setPrefWidth(50);

        TableColumn<ObservableList<String>, String> colProductName = new TableColumn<>("Nome do Produto");
        colProductName.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().get(1))
        );

        TableColumn<ObservableList<String>, String> colQuantity = new TableColumn<>("Qtd. Disponível");
        colQuantity.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().get(2))
        );

        tableView.getColumns().addAll(colID, colProductName, colQuantity);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // TitledPane
        TitledPane titledPane = new TitledPane("Estoque", tableView);
        titledPane.setCollapsible(false);
        VBox.setVgrow(titledPane, Priority.ALWAYS);

        // Botões
        Button btnAtualizar = new Button("Atualizar Estoque");
        btnAtualizar.setOnAction(e -> loadData());

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(e -> mainStage.getScene().setRoot(new MainScreen(mainStage)));

        HBox hboxButtons = new HBox(10, btnAtualizar, btnVoltar);
        hboxButtons.setPadding(new Insets(10));
        hboxButtons.setAlignment(Pos.CENTER);

        getChildren().addAll(titledPane, hboxButtons);
        loadData();
    }

    private void loadData() {
        data.clear();
        data.addAll(inventoryDAO.listAllJoin());
    }
}
