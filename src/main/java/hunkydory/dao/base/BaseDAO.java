package hunkydory.dao.base;

import hunkydory.infrastructure.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseDAO<T> implements GenericDAO<T> {

    protected Connection getConnection() throws SQLException {
        return ConnectionFactory.getConnection();
    }

}
