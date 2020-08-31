package com.ls.mapper;

import com.github.pagehelper.Page;
import com.ls.dto.EmployeeDTO;
import com.ls.pojo.Employee;
import com.ls.pojo.PageBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EmployeeMapper {

    //分页查询所有员工
    Page<EmployeeDTO> queryAllEmployee(String name);

    //根据id查询员工
    Employee queryEmployee(@Param("eid") int eid);

    //添加员工
    int addEmployee(Employee employee);

    //删除员工
    int deleteEmployee(@Param("eid") int eid);

    //修改员工
    int updateEmployee(Employee employee);

    //根据员工姓名分页模糊查询
    Page<EmployeeDTO> queryEmpLikeName(String name, PageBean pageBean);

}
