package dao;

import interfaces.dao.UserDao;
import models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import javax.sql.RowSet;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.List;
import java.util.Map;

/**
 * Created by User on 04.01.2017.
 */
public class UserDaoImpl implements UserDao {
    //language=SQL
    private final String SQL_SELECT_USER_BY_ID = "SELECT * FROM group_users WHERE id=?";
    //language=SQL
    private final String SQL_SELECT_ALL = "SELECT * FROM group_users";
    //language=SQL
    private final String SQL_ADD_USER = "INSERT INTO group_users SET name=?,age=?";

    private JdbcTemplate template;
    public UserDaoImpl(DataSource dataSource){
        template = new JdbcTemplate(dataSource);
    }

    RowMapper<User> userRowMapper = (ResultSet rs, int i) -> {
      User user = new User.Builder().setName(rs.getString("name"))
              .setAge(rs.getInt("age"))
              .setId(rs.getInt("id")).build();
      return user;
    };

    @Override
    public User find(int id) {
        User user = template.queryForObject(SQL_SELECT_USER_BY_ID,new Object[]{id},userRowMapper);
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> usersList = template.query(SQL_SELECT_ALL,new Object[]{},userRowMapper);
        return usersList;
    }

    @Override
    public boolean save(User item) {
        Object[] args = {item.getName(),item.getAge()};
        int[] types = {Types.VARCHAR,Types.INTEGER};
        return template.update(SQL_ADD_USER,args,types) > 0 ? true : false;
    }

    @Override
    public boolean remove(int id) {
        return false;
    }
}
