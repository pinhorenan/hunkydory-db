package hunkydory.ui.sql;

import hunkydory.infrastructure.ConnectionFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class AdvancedSQLScreen extends BorderPane {

    private final TextArea sqlTextArea;
    private final TableView<ObservableList<String>> tableView;
    private final Label lblStatus;

    public AdvancedSQLScreen() {
        sqlTextArea = new TextArea();
        sqlTextArea.setPromptText("Digite sua consulta SQL aqui...");
        sqlTextArea.setWrapText(true);
        VBox.setVgrow(sqlTextArea, Priority.ALWAYS);

        Button btnExecute = new Button("Executar Consulta");
        btnExecute.setMaxWidth(Double.MAX_VALUE);
        btnExecute.setOnAction(e -> executeQuery());

        Button btnExamples = new Button("Consultas Exemplo");
        btnExamples.setMaxWidth(Double.MAX_VALUE);
        btnExamples.setOnAction(e -> new ExampleQueriesDialog().showAndWait());

        VBox buttonBox = new VBox(5, btnExecute, btnExamples);
        buttonBox.setFillWidth(true);

        lblStatus = new Label();
        lblStatus.setWrapText(true);

        VBox leftPanel = new VBox(10, sqlTextArea, buttonBox, lblStatus);
        leftPanel.setPadding(new Insets(10));
        leftPanel.setPrefWidth(300);
        leftPanel.setAlignment(Pos.TOP_CENTER);
        VBox.setVgrow(sqlTextArea, Priority.ALWAYS);
        VBox.setVgrow(btnExecute, Priority.NEVER);
        VBox.setVgrow(lblStatus, Priority.NEVER);

        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(tableView, Priority.ALWAYS);

        Label title = new Label("Resultados da Consulta");
        title.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        VBox.setMargin(title, new Insets(0, 0, 5, 0));

        VBox rightPanel = new VBox(10, title, tableView);
        rightPanel.setPadding(new Insets(10));
        VBox.setVgrow(tableView, Priority.ALWAYS);

        HBox contentBox = new HBox(leftPanel, rightPanel);
        contentBox.setPadding(new Insets(10));
        HBox.setHgrow(rightPanel, Priority.ALWAYS);

        setCenter(contentBox);
    }

    private void executeQuery() {
        String query = sqlTextArea.getText().trim();
        if (query.isEmpty()) {
            lblStatus.setText("Por favor, insira uma consulta SQL válida.");
            return;
        }

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement()) {

            if (query.toLowerCase().startsWith("select")) {
                ResultSet rs = stmt.executeQuery(query);
                buildData(rs);
                lblStatus.setText("Consulta executada com sucesso.");
            } else {
                int affected = stmt.executeUpdate(query);
                tableView.getColumns().clear();
                tableView.getItems().clear();
                lblStatus.setText("Consulta executada com sucesso. Linhas afetadas: " + affected);
            }

        } catch (SQLException ex) {
            lblStatus.setText("Erro: " + ex.getMessage());
            //noinspection CallToPrintStackTrace
            ex.printStackTrace();
        }
    }

    private void buildData(ResultSet rs) throws SQLException {
        tableView.getColumns().clear();
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

        ResultSetMetaData rsmd = rs.getMetaData();
        int columns = rsmd.getColumnCount();

        for (int i = 1; i <= columns; i++) {
            final int j = i;
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(rsmd.getColumnName(i));
            col.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().get(j - 1))
            );
            col.setMinWidth(100);
            tableView.getColumns().add(col);
        }

        while (rs.next()) {
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= columns; i++) {
                row.add(rs.getString(i));
            }
            data.add(row);
        }
        tableView.setItems(data);
    }
}
