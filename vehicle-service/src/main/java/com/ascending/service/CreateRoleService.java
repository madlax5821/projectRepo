package com.ascending.service;

import com.ascending.dao.CreateRole;
import com.ascending.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateRoleService {

    @Autowired
    private CreateRole createRole;

    public boolean saveRole(Role role){return createRole.saveRole(role);}
}
