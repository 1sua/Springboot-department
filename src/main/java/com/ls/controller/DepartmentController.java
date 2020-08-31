package com.ls.controller;

import com.ls.mapper.DepartmentMapper;
import com.ls.pojo.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class DepartmentController {

    @Autowired
    private DepartmentMapper departmentMapper;

    @GetMapping("queryAllDepartment")
    public List<Department> queryAllDepartment() {
        return departmentMapper.queryAllDepartment();
    }

}
