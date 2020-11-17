package com.ascending.repository;

import com.ascending.dao.RoleDao;
import com.ascending.dao.UserDao;
import com.ascending.init.AppInitializer;
import com.ascending.model.Role;
import com.ascending.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class RegisterTest {
    @Autowired
    private RegisterImpl register;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    private User user;
    private Role role;

    @Before
    public void setup(){
        user = new User();
        user.setName("testUser");
        user.setEmail("test@test.com");
        role = new Role();
        role.setName("employee");
        roleDao.save(role);
    }

    @After
    public void tearDown(){
        user.removeRole(role);
        userDao.save(user);
        userDao.deleteByName(user.getName());
        roleDao.delete(role);
    }

    @Test
    public void registerTest(){
        register.userRegister(user,role.getName());
    }



}
