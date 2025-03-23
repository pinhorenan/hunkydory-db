package hunkydory.ui.sql;

import hunkydory.infrastructure.ConnectionFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.*;

@SuppressWarnings("CallToPrintStackTrace")
public class AdvancedSQLScreen extends BorderPane {

    private final TextArea sqlTextArea;
    private final TableView<ObservableList<String>> tableView;
    private final Label lblStatus;

    public AdvancedSQLScreen(Stage mainStage) {
        setPadding(new Insets(10));

        // TABELA DE RESULTADO
        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Envolvendo a tabela em um TitledPane
        TitledPane titledPane = new TitledPane("Resultados da Consulta", tableView);
        titledPane.setCollapsible(false);
        VBox.setVgrow(titledPane, Priority.ALWAYS);

        // ÁREA DE QUERY
        sqlTextArea = new TextArea();
        sqlTextArea.setPromptText("Digite sua query SQL aqui...");
        sqlTextArea.setPrefRowCount(4);
        sqlTextArea.setWrapText(true);
        sqlTextArea.setMinHeight(80);

        Button btnExecute = new Button("Executar Query");
        btnExecute.setOnAction(e -> executeQuery());

        lblStatus = new Label();
        lblStatus.setWrapText(true);

        HBox actions = new HBox(10, btnExecute, lblStatus);
        actions.setPadding(new Insets(10, 0, 0, 0));

        VBox contentBox = new VBox(10, titledPane, new Label("Query SQL:"), sqlTextArea, actions);
        contentBox.setPadding(new Insets(10));
        VBox.setVgrow(titledPane, Priority.ALWAYS);

        setCenter(contentBox);
    }

    private void executeQuery() {
        String query = sqlTextArea.getText().trim();
        if (query.isEmpty()) {
            lblStatus.setText("Digite uma query válida.");
            return;
        }

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement()) {

            if (query.toLowerCase().startsWith("select")) {
                ResultSet rs = stmt.executeQuery(query);
                buildData(rs);
                lblStatus.setText("Query executada com sucesso.");
            } else {
                int affected = stmt.executeUpdate(query);
                tableView.getColumns().clear();
                tableView.getItems().clear();
                lblStatus.setText("Query executada com sucesso. Linhas afetadas: " + affected);
            }

        } catch (SQLException ex) {
            lblStatus.setText("Erro: " + ex.getMessage());
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
                    new javafx.beans.property.SimpleStringProperty(cellData.getValue().get(j - 1))
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
