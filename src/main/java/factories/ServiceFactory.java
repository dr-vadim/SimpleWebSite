package factories;

import interfaces.dao.AutoDao;
import interfaces.dao.UserDao;
import models.User;
import services.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Properties;

public class ServiceFactory {
    private static final ServiceFactory instance;
    public final String PROPERTIES_PATH = "E:\\Java\\Projects\\SimpleWebApp\\src\\main\\resources\\context.properties";

    Service service;

    static {
        instance = new ServiceFactory();
    }

    private ServiceFactory(){
        Properties prop = new Properties();
        try(FileInputStream fin = new FileInputStream(PROPERTIES_PATH)){
            prop.load(fin);
            String classname = prop.getProperty("service.class");
            Constructor<?> constructor = Class.forName(classname).getConstructor(UserDao.class, AutoDao.class);
            service = (Service)constructor.newInstance(UserFactory.getInstance().getUser(),
                    AutoFactory.getInstance().getAuto());

        }catch (IOException e){
            throw new IllegalArgumentException(e);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        } catch (InstantiationException e) {
            throw new IllegalArgumentException(e);
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static ServiceFactory getInstance(){
        return instance;
    }

    public Service getService(){
        return service;
    }
}
