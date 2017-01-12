package services;

import interfaces.dao.AutoDao;
import interfaces.dao.UserDao;
import models.Auto;
import models.User;

import java.util.*;
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

    public SortedMap<Integer,User> userListToSortedMap(List<User> list){
        Map<Integer,User> mapUser = list.stream().collect(Collectors.toMap(i -> i.getId(),i -> i));
        return new TreeMap(mapUser);
    }

    public List<User> getUsersWithAuto(){
        Map<Integer,User> users = userListToSortedMap(userDao.findAll());
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

    public User getUser(int id){
        return userDao.find(id);
    }

    public User addUser(User user){
        return userDao.save(user);
    }

    public boolean updateUser(int id,User user){
        return userDao.update(id,user);
    }

    public boolean deleteUser(int id){
        autoDao.removeByUser(id);
        boolean result = userDao.remove(id);
        return result;
    }

    public List<Auto> getAutoByUser(int userId){
        return autoDao.findByUser(userId);
    }

    public Auto addAuto(Auto auto){
        return autoDao.save(auto);
    }

    public boolean updateAuto(int id, Auto auto){
        return autoDao.update(id,auto);
    }

    public boolean deleteAuto(int id){
        return autoDao.remove(id);
    }

    public Auto getAuto(int id){
        return autoDao.find(id);
    }
}
