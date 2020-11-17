package com.ascending.repository;

import com.ascending.dao.CreateRole;
import com.ascending.dao.RoleDao;
import com.ascending.model.Role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CreateRoleImpl implements CreateRole {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private RoleDao roleDao;

    @Override
    public boolean saveRole(Role role) {
        if (roleDao.getRoleByName(role.getName())!=null){
            logger.info("this role has already been used in system...");
            return false;
        }else{
            roleDao.save(role);
            return true;
        }
    }
}
