package services;

import interfaces.dao.AutoDao;
import interfaces.dao.UserDao;
import models.Auto;
import models.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Service {

    UserDao userDao;
    AutoDao autoDao;

    public Service(UserDao user, AutoDao auto){
        this.userDao = user;
        this.autoDao = auto;
    }

    public Map<Integer,User> userListToMap(List<User> list){
        return list.stream().collect(Collectors.toMap(i -> i.getId(),i -> i));
    }

    public List<User> getUsersWithAuto(){
        Map<Integer,User> users = userListToMap(userDao.findAll());
        List<Auto> autos = autoDao.findAll();
        for (Auto auto : autos){
            int id = auto.getUser().getId();
            User user = users.get(id);
            if(user != null){
                user.setAuto(auto);
                users.replace(id,user);
            }
        }
        Collection<User> userCol = users.values();
        List<User> userList = new ArrayList<>();
        userList.addAll(userCol);
        return userList;
    }

    public boolean addUser(User user){
        return userDao.save(user);
    }
}
