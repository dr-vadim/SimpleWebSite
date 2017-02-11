package dao;

import interfaces.dao.UserDao;
import models.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserDaoImpl implements UserDao {
    //language=SQL
    private final String SQL_SELECT_USER_BY_ID = "SELECT * FROM group_users WHERE id=?";
    //language=HQL
    private final String HQL_SELECT_USER_BY_ID = "from User u where u.id=:id";
    //language=SQL
    private final String SQL_SELECT_ALL = "SELECT * FROM group_users";
    //language=SQL
    private final String SQL_ADD_USER = "INSERT INTO group_users (name,age) VALUES (?,?)";
    //language=SQL
    private final String SQL_UPDATE_USER = "UPDATE group_users SET name=?,age=? WHERE id=?";
    //language=HQL
    private final String HQL_UPDATE_USER = "update User as u set u.name=:name, u.age=:age WHERE u.id=:id";
    //language=SQL
    private final String SQL_DELETE_USER = "DELETE FROM group_users WHERE id=?";
    //language=HQL
    private final String HQL_DELETE_USER = "delete from User u WHERE u.id=:id";

    private JdbcTemplate template;
    private Session session;

    public UserDaoImpl(DataSource dataSource, Session session){
        template = new JdbcTemplate(dataSource);
        this.session = session;
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
    public User save(User user) {
        session.beginTransaction();
        Integer userId = (Integer)session.save(user);
        session.getTransaction().commit();
        user.setId(userId);
        return user;
    }

    @Override
    public boolean update(int id, User item) {
        session.beginTransaction();
        Query query = session.createQuery(HQL_UPDATE_USER);
        query.setParameter("name",item.getName())
                .setParameter("age", item.getAge())
        .setParameter("id", id);
        int result = query.executeUpdate();
        session.getTransaction().commit();

        return result > 0;
    }

    @Override
    public boolean remove(int id) {
        session.beginTransaction();
        Query query = session.createQuery(HQL_DELETE_USER);
        query.setParameter("id",id);
        int result = query.executeUpdate();
        session.getTransaction().commit();
        session.flush();
        session.clear();
        return result > 0;
    }
}
