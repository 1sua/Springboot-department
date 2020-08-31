package com.ls.service;

import com.ls.pojo.User;

public interface UserService {
    //登录功能
    User UserLogin(String username, String password);
}
