package factories;

import interfaces.dao.UserDao;
import org.hibernate.Session;
import org.springframework.beans.NotReadablePropertyException;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class UserFactory {
    private static final UserFactory instance;
    private final String PROPERTIES_PATH = "context.properties";
    UserDao userDao;

    static{
        instance = new UserFactory();
    }

    private UserFactory(){
        Properties properties = new Properties();
        String path = getClass().getClassLoader().getResource(PROPERTIES_PATH).getPath();
        try(FileInputStream fin = new FileInputStream(path)){

            properties.load(fin);
            String dataType = properties.getProperty("data.type");
            String userClassName = properties.getProperty("user.class."+dataType);
            Constructor<?> constructor = Class.forName(userClassName).getConstructor(DataSource.class, Session.class);
            userDao = (UserDao) constructor.newInstance(DataSourceFactory.getInstance().getDataSource(),
                    HibernateConnector.getInstance().getSession());

        } catch (IOException | InvocationTargetException | InstantiationException |
                IllegalAccessException | NoSuchMethodException | ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static UserFactory getInstance(){
        return instance;
    }

    public UserDao getUser(){
        return userDao;
    }

}
