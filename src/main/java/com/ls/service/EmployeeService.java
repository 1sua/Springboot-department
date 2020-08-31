package com.ls.service;

import com.github.pagehelper.Page;
import com.ls.dto.EmployeeDTO;
import com.ls.pojo.Employee;
import com.ls.pojo.PageBean;

import java.util.List;

public interface EmployeeService {
    //查询所有员工
    Page<EmployeeDTO> queryAllEmployee(String name);

    //添加员工
    void addEmployee(Employee employee);

    Employee queryEmployeeById(int id);

    void updateEmployee(Employee employee);

    int deleteEmployeeById(int id);

    //根据员工姓名分页模糊查询
    Page<EmployeeDTO> queryEmpLikeName(String name, PageBean pageBean);
}
