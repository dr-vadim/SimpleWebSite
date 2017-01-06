package factories;

import interfaces.dao.AutoDao;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class AutoFactory {
    private static final AutoFactory instance;
    private final String PROPERTIES_PATH = "E:\\Java\\Projects\\SimpleWebApp\\src\\main\\resources\\context.properties";

    AutoDao auto;

    static {
        instance = new AutoFactory();
    }

    private AutoFactory(){
        Properties prop = new Properties();
        try(FileInputStream fin = new FileInputStream(PROPERTIES_PATH)){
            prop.load(fin);
            String dataType = prop.getProperty("data.type");
            String className = prop.getProperty("auto.class."+dataType);
            Constructor<?> constructor = Class.forName(className).getConstructor(DataSource.class);
            auto = (AutoDao) constructor.newInstance(DataSourceFactory.getInstance().getDataSource());
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
