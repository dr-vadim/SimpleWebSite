package interfaces.dao;

import java.util.List;

public interface MainDao<T> {
    T find(int id);
    List<T> findAll();
    boolean save(T item);
    boolean remove(int id);
}
