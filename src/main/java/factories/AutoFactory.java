package factories;

import interfaces.dao.AutoDao;
import org.hibernate.Session;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class AutoFactory {
    private static final AutoFactory instance;
    private final String PROPERTIES_PATH = "context.properties";

    AutoDao auto;

    static {
        instance = new AutoFactory();
    }

    private AutoFactory(){
        Properties prop = new Properties();
        String path = getClass().getClassLoader().getResource(PROPERTIES_PATH).getPath();
        try(FileInputStream fin = new FileInputStream(path)){
            prop.load(fin);
            String dataType = prop.getProperty("data.type");
            String className = prop.getProperty("auto.class."+dataType);
            Constructor<?> constructor = Class.forName(className).getConstructor(DataSource.class, Session.class);
            auto = (AutoDao) constructor.newInstance(DataSourceFactory.getInstance().getDataSource(),
                    HibernateConnector.getInstance().getSession());
        }catch (IOException e){
            throw new IllegalArgumentException(e);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException |
                InvocationTargetException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static AutoFactory getInstance(){
        return instance;
    }

    public AutoDao getAuto(){
        return auto;
    }
}
