package com.ascending.controller;


import com.ascending.model.Role;
import com.ascending.service.CreateRoleService;
import com.ascending.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "role")
public class RoleController {

    @Autowired
    private CreateRoleService createRoleService;

    @Autowired
    private RoleService roleService;

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean saveRole(@RequestBody Role role){
        return createRoleService.saveRole(role);
    }

    @DeleteMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean deleteRoleByName(@RequestParam String roleName){return roleService.deleteRoleByName(roleName);}
}
