package interfaces.dao;

import java.util.List;

public interface MainDao<T> {
    T find(int id);
    List<T> findAll();
    T save(T item);
    boolean update(int id,T item);
    boolean remove(int id);
}
