package com.ls.service.ServiceImpl;

import com.ls.mapper.UserMapper;
import com.ls.pojo.User;
import com.ls.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;

    //登录功能
    public User UserLogin(String username, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);

        return mapper.queryUserByUsernameAndPassword(map);
    }

}
