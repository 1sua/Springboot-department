package com.ls.service.ServiceImpl;

import com.github.pagehelper.Page;
import com.ls.dto.EmployeeDTO;
import com.ls.mapper.EmployeeMapper;
import com.ls.pojo.Employee;
import com.ls.pojo.PageBean;
import com.ls.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper mapper;

    @Override
    public Page<EmployeeDTO> queryAllEmployee(String name) {
        return mapper.queryAllEmployee(name);
    }

    @Override
    public void addEmployee(Employee employee) {
        mapper.addEmployee(employee);
    }

    @Override
    public Employee queryEmployeeById(int id) {
        return mapper.queryEmployee(id);
    }

    @Override
    public void updateEmployee(Employee employee) {
        mapper.updateEmployee(employee);
    }

    @Override
    public int deleteEmployeeById(int id) {
        return mapper.deleteEmployee(id);
    }

    @Override
    public Page<EmployeeDTO> queryEmpLikeName(String name, PageBean pageBean) {
        return mapper.queryEmpLikeName(name, pageBean);
    }
}
