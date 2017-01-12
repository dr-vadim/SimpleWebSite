package dao;

import interfaces.dao.AutoDao;
import models.Auto;
import models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class AutoDaoImpl implements AutoDao{
    //language=SQL
    private final String SQL_SELECT_AUTO_BY_ID = "SELECT * FROM auto WHERE id=?";
    //language=SQL
    private final String SQL_SELECT_ALL = "SELECT * FROM auto";
    //language=SQL
    private final String SQL_SELECT_AUTOS_BY_USER = "SELECT * FROM auto WHERE user_id=?";
    //language=SQL
    private final String SQL_INSERT_AUTO = "INSERT INTO auto(name,color,user_id) VALUES (?,?,?)";
    //language=SQL
    private final String SQL_UPDATE_AUTO = "UPDATE auto SET name=?,color=?,user_id=? WHERE id=?";
    //language=SQL
    private final String SQL_DELETE_AUTO = "DELETE FROM auto WHERE id=?";
    //language=SQL
    private final String SQL_DELETE_AUTO_BY_USER = "DELETE FROM auto WHERE user_id=?";

    private JdbcTemplate template;

    public AutoDaoImpl(DataSource dataSource){
        template = new JdbcTemplate(dataSource);
    }

    RowMapper<Auto> autoRowMapper = (ResultSet rs, int i) -> {
        User user = new User.Builder().setId(rs.getInt("user_id")).build();
        Auto auto = new Auto.Builder().setId(rs.getInt("id"))
                .setModel(rs.getString("name"))
                .setColor(rs.getString("color"))
                .setUser(user).build();
        return auto;
    };

    @Override
    public Auto find(int id) {
        return template.queryForObject(SQL_SELECT_AUTO_BY_ID,new Object[]{id},autoRowMapper);
    }

    @Override
    public List<Auto> findAll() {
        return template.query(SQL_SELECT_ALL,new Object[]{},autoRowMapper);
    }

    @Override
    public Auto save(Auto item) {
        Object[] args = {item.getModel(),item.getColor(),item.getUser().getId()};
        int[] types = {Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = template.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps =
                        connection.prepareStatement(SQL_INSERT_AUTO, new String[] {"id"});
                ps.setString(1,item.getModel());
                ps.setString(2, item.getColor());
                ps.setInt(3,item.getUser().getId());
                return ps;
            }
        },keyHolder);
        if(result <= 0) return null;
        item.setId(keyHolder.getKey().intValue());
        return item;
    }

    @Override
    public boolean update(int id, Auto item) {
        Object[] args = {item.getModel(),item.getColor(),item.getUser().getId(),id};
        int[] types = {Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.INTEGER};
        return template.update(SQL_UPDATE_AUTO,args,types) > 0;
    }

    @Override
    public boolean remove(int id) {
        return template.update(SQL_DELETE_AUTO,new Integer[]{id},new int[]{Types.INTEGER}) > 0;
    }

    @Override
    public List<Auto> findByUser(int userId) {
        return template.query(SQL_SELECT_AUTOS_BY_USER,new Object[]{userId},autoRowMapper);
    }

    @Override
    public boolean removeByUser(int id) {
        return template.update(SQL_DELETE_AUTO_BY_USER,new Integer[]{id},new int[]{Types.INTEGER}) > 0;
    }
}
