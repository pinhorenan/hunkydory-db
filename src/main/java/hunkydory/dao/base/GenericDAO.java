package hunkydory.dao.base;

import java.util.List;

public interface GenericDAO<T> {
    boolean insert(T entity);
    boolean update(T entity);
    boolean delete(int id);
    List<T> listAll();
    @SuppressWarnings("unused")
    T searchByID(int id);
}
