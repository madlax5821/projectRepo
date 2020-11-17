package com.ascending.service;

import com.ascending.dao.RoleDao;
import com.ascending.dao.UserDao;
import com.ascending.model.Role;
import com.ascending.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    public User getUserByNameAndEmail(String name, String email){return userDao.getUserByNameAndEmail(name,email);}

    public User save(User user){return userDao.save(user);}

    public User getUserByName(String name){return userDao.getUserByName(name);}

    public boolean delete(User user){return userDao.delete(user);}

    public boolean update(User user){return userDao.update(user);}

    public User getUserByCredential(String email, String password) throws Exception {return userDao.getUserByCredentials(email,password);}

    public User getUserById(long id){return userDao.getUserById(id);}

    public boolean deleteUserWithRole(String userName, String roleName){
        User user = userDao.getUserByName(userName);
        user.removeRole(roleDao.getRoleByName(roleName));
        userDao.save(user);
        return userDao.delete(user);
    }




}
