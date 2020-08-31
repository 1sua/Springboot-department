package com.ls;

import com.ls.mapper.UserMapper;
import com.ls.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class Springboot03WebApplicationTests {

    @Autowired
    DataSource dataSource;

    @Autowired
    UserMapper mapper;

    @Test
    void contextLoads() throws SQLException {

        System.out.println(dataSource);
        System.out.println(dataSource.getConnection());

        Map<String, String> map = new HashMap<>();
        map.put("username", "admin");
        map.put("password", "123456");

        User user = mapper.queryUserByUsernameAndPassword(map);
        System.out.println(user);

    }

}
