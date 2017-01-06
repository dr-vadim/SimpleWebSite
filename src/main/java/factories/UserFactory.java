package factories;

import interfaces.dao.UserDao;
import org.springframework.beans.NotReadablePropertyException;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * Created by User on 04.01.2017.
 */
public class UserFactory {
    private static final UserFactory instance;
    private final String PROPERTIES_PATH = "E:\\Java\\Projects\\SimpleWebApp\\src\\main\\resources\\context.properties";
    UserDao userDao;

    static{
        instance = new UserFactory();
    }

    private UserFactory(){
        Properties properties = new Properties();
        try(FileInputStream fin = new FileInputStream(PROPERTIES_PATH)){

            properties.load(fin);
            String dataType = properties.getProperty("data.type");
            String userClassName = properties.getProperty("user.class."+dataType);
            Constructor<?> constructor = Class.forName(userClassName).getConstructor(DataSource.class);
            userDao = (UserDao) constructor.newInstance(DataSourceFactory.getInstance().getDataSource());

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
