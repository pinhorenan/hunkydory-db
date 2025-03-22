package hunkydory.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por fornecer conexões JDBC com o banco PostgreSQL.
 */
public class ConnectionFactory {
    // Ajuste com as credenciais do seu ambiente PostgreSQL
    private static final String URL = "jdbc:postgresql://localhost:5432/hunkydory";
    private static final String USER = "postgres";
    private static final String PASS = "846284";

    /**
     * Retorna uma nova conexão com o banco de dados.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}