package interfaces.dao;

import models.Auto;

import java.util.List;

/**
 * Created by User on 04.01.2017.
 */
public interface AutoDao extends MainDao<Auto> {
    List<Auto> findByUser(int userId);
    boolean removeByUser(int id);
}
