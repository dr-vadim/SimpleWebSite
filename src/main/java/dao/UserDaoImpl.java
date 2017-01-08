package dao;

import interfaces.dao.UserDao;
import models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import javax.sql.RowSet;
import java.sql.*;
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
    private final String SQL_ADD_USER = "INSERT INTO group_users (name,age) VALUES (?,?)";
    //language=SQL
    private final String SQL_UPDATE_USER = "UPDATE group_users SET name=?,age=? WHERE id=?";
    //language=SQL
    private final String SQL_DELETE_USER = "DELETE FROM group_users WHERE id=?";

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
    public User save(User item) {
        Object[] args = {item.getName(),item.getAge()};
        int[] types = {Types.VARCHAR,Types.INTEGER};
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int res = template.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps =
                        connection.prepareStatement(SQL_ADD_USER, new String[] {"id"});
                ps.setString(1,item.getName());
                ps.setInt(2, item.getAge());
                return ps;
            }
        },keyHolder);
        if(res <= 0) return null;
        item.setId(keyHolder.getKey().intValue());
        return item;
    }

    @Override
    public boolean update(int id, User item) {
        Object[] args = {item.getName(),item.getAge(),id};
        int[] types = {Types.VARCHAR,Types.INTEGER,Types.INTEGER};
        return template.update(SQL_UPDATE_USER,args,types) > 0;
    }

    @Override
    public boolean remove(int id) {
        return template.update(SQL_DELETE_USER,new Integer[]{id},new int[]{Types.INTEGER}) > 0;
    }
}
