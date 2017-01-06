package dao;

import interfaces.dao.AutoDao;
import models.Auto;
import models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.List;

public class AutoDaoImpl implements AutoDao{
    //language=SQL
    private final String SQL_SELECT_AUTO_BY_ID = "SELECT * FROM auto WHERE id=?";
    //language=SQL
    private final String SQL_SELECT_ALL = "SELECT * FROM auto";

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
    public boolean save(Auto item) {
        return false;
    }

    @Override
    public boolean remove(int id) {
        return false;
    }
}
