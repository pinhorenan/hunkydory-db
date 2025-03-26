package hunkydory.ui.sql;

import hunkydory.infrastructure.ConnectionFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

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
        setPadding(new Insets(10));

        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        TitledPane titledPane = new TitledPane("Resultados da Consulta", tableView);
        titledPane.setCollapsible(false);
        VBox.setVgrow(titledPane, Priority.ALWAYS);

        sqlTextArea = new TextArea();
        sqlTextArea.setPromptText("Digite sua consulta SQL aqui...");
        sqlTextArea.setPrefRowCount(4);
        sqlTextArea.setWrapText(true);
        sqlTextArea.setMinHeight(80);

        Button btnExecute = new Button("Executar Consulta");
        btnExecute.setOnAction(e -> executeQuery());

        lblStatus = new Label();
        lblStatus.setWrapText(true);

        HBox actions = new HBox(10, btnExecute, lblStatus);
        actions.setPadding(new Insets(10, 0, 0, 0));

        VBox contentBox = new VBox(10, titledPane, new Label("Consulta SQL:"), sqlTextArea, actions);
        contentBox.setPadding(new Insets(10));
        VBox.setVgrow(titledPane, Priority.ALWAYS);

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
