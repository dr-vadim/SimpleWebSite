package factories;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class HibernateConnector {
    private final String PROPERTIES_PATH = "hibernate.properties";
    private static final HibernateConnector instance;
    private Configuration configuration;
    private SessionFactory sessionFactory;

    static {
        instance = new HibernateConnector();
    }

    private HibernateConnector(){
        configuration = new Configuration();
        Properties prop = new Properties();
        String path = getClass().getClassLoader().getResource(PROPERTIES_PATH).getPath();
        try(FileInputStream fin = new FileInputStream(path)){
            prop.load(fin);
            configuration.addProperties(prop);
            //Add annotated classes
            configuration.addAnnotatedClass(models.User.class)
                    .addAnnotatedClass(models.Auto.class);
            sessionFactory = configuration.buildSessionFactory();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static HibernateConnector getInstance(){
        return instance;
    }

    public Session getSession() throws HibernateException {
        Session session = sessionFactory.openSession();
        if(!session.isConnected()){
            this.reconnect();
        }
        return session;
    }

    public void reconnect() throws HibernateException {
        this.sessionFactory = configuration.buildSessionFactory();
    }

}
