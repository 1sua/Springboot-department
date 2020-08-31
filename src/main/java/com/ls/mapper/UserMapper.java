package com.ls.mapper;

import com.ls.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface UserMapper {

    User queryUserByUsernameAndPassword(Map<String, String> map);

}
