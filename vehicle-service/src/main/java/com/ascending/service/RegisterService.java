package com.ascending.service;

import com.ascending.model.User;
import com.ascending.repository.RegisterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    @Autowired
    private RegisterImpl registerImpl;

    public String userRegister(User user, String roleName){return registerImpl.userRegister(user,roleName);}
}
