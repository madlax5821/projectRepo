package com.ascending.repository;

import com.ascending.dao.CreateRole;
import com.ascending.dao.RoleDao;
import com.ascending.init.AppInitializer;
import com.ascending.model.Role;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class CreateRoleTest {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CreateRole createRole;

    @Autowired
    private RoleDao roleDao;

    private Role role;

    @Before
    public void setup(){
        role = new Role();
        role.setName("testRole");
    }

    @After
    public void cleanUp(){
        roleDao.deleteRoleByName(role.getName());
    }

    @Test
    public void createRoleTest(){
        Role testRole = role;
        createRole.saveRole(role);
        Assert.assertEquals("testRole",testRole.getName());
    }
}
