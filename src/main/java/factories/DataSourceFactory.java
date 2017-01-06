package factories;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class DataSourceFactory {
    private static final DataSourceFactory instance;
    private final String PROPERTIES_PATH = "E:\\Java\\Projects\\SimpleWebApp\\src\\main\\resources\\context.properties";

    private DataSource dataSource;

    static{
        instance = new DataSourceFactory();
    }

    private DataSourceFactory(){
        Properties prop = new Properties();
        try(FileInputStream fin = new FileInputStream(PROPERTIES_PATH)){
            prop.load(fin);
            String dataType = prop.getProperty("data.type");
            String dataUrl = prop.getProperty("data.url."+dataType);
            String userName = prop.getProperty("data.user");
            String dataPass = prop.getProperty("data.pass");
            String driver = prop.getProperty("data.driver");

            DriverManagerDataSource driverManager = new DriverManagerDataSource();
            driverManager.setDriverClassName(driver);
            driverManager.setUrl(dataUrl);
            driverManager.setUsername(userName);
            driverManager.setPassword(dataPass);
            dataSource = driverManager;

        }catch (FileNotFoundException e){
            throw new IllegalArgumentException(e);
        }catch(IOException e){
            throw new IllegalArgumentException(e);
        }
    }

    public static DataSourceFactory getInstance(){
        return instance;
    }

    public DataSource getDataSource(){
        return dataSource;
    }
}
