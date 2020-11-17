package com.ascending.controller;

import com.ascending.dao.Register;
import com.ascending.dao.UserDao;
import com.ascending.model.Role;
import com.ascending.model.User;
import com.ascending.repository.UserDaoImpl;
import com.ascending.service.RegisterService;
import com.ascending.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RegisterService registerService;

    @GetMapping(value = "getUserByNameAndEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserByNameAndEmail(@RequestParam("name") String name, @RequestParam("email") String email) {
        return userService.getUserByNameAndEmail(name, email);
    }

    @PostMapping(value = "saveUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public User saveUser(@RequestBody User user) {
        user = userService.save(user);
        return user;
    }

    @DeleteMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean deleteUserByRole(@RequestParam String userName, @RequestParam String roleName){
        return userService.deleteUserWithRole(userName,roleName);
    }

    @PostMapping(value = "register", produces = MediaType.APPLICATION_JSON_VALUE)
    public String register(@RequestBody User user, @RequestParam String roleName){
        return registerService.userRegister(user,roleName);
    }
}
