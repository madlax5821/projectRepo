package com.ascending.dao;

import com.ascending.model.User;
import org.springframework.beans.factory.annotation.Autowired;

public interface Register {
    String userRegister(User user, String roleName);

}
