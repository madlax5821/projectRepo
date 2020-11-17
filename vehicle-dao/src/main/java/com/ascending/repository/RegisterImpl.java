package com.ascending.repository;

import com.ascending.dao.Register;
import com.ascending.dao.RoleDao;
import com.ascending.dao.UserDao;
import com.ascending.model.Role;
import com.ascending.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RegisterImpl implements Register {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Override
    public String userRegister(User user, String roleName) {
        Role role = roleDao.getRoleByName(roleName);
        user.addRole(role);
        if (userDao.getUserByName(user.getName())!=null){
            return "user name has been used...";
        }else if(userDao.getUserByEmail(user.getEmail())!=null){
            return "user`s email has been used...";
        }else{
            userDao.save(user);
            return "user has been created successfully...";
        }
    }
}
