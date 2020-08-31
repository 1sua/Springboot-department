package com.ls.mapper;


import com.ls.pojo.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DepartmentMapper {

    //查询所有部门id
    List<Department> queryAllDepartment();

    //根据部门id查询部门
    String queryDepartment(@Param("did") int did);

}
