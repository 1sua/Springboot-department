package com.ls.service.ServiceImpl;

import com.ls.mapper.DepartmentMapper;
import com.ls.pojo.Department;
import com.ls.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper mapper;

    @Override
    public List<Department> queryAllDepartment() {
        return mapper.queryAllDepartment();
    }
}
