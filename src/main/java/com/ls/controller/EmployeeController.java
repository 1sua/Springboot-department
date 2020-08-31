package com.ls.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ls.dto.EmployeeDTO;
import com.ls.pojo.Department;
import com.ls.pojo.Employee;
import com.ls.pojo.PageBean;
import com.ls.service.DepartmentService;
import com.ls.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PageBean pageBean;

    /*
        1. 静态方法，传递两个参数（当前页码，每页查询条数）
        2. 使用pageHelper 分页的时候，不再关注分页语句，查询全部的语句
        3. 自动的对PageHelper.startPage 方法下的第一个sql 查询进行分页
        PageHelper.startPage(1,5);
        //紧跟着的第一个select 方法会被分页
        List<Country> list = countryMapper.findAll();
    */
    @RequestMapping("/emps")
    public String queryEmp(Model model, Integer pageNum, String likeName) {
        if (pageNum == null) {
            pageNum = 1;
        }
        PageHelper.startPage(pageNum, 5);
        Page<EmployeeDTO> employeeDTOS = employeeService.queryAllEmployee(likeName);
        System.out.println(employeeDTOS);
        model.addAttribute("emps", employeeDTOS);
        model.addAttribute("totalPage", employeeDTOS.getPages());
        model.addAttribute("totalCount", employeeDTOS.getTotal());
        model.addAttribute("pageNum", employeeDTOS.getPageNum());
        model.addAttribute("likeName", likeName);
        return "emp/list";
    }

    //跳转到添加页面
    @GetMapping("/emp")
    public String toAdd(Model model) {
        //查出部门信息
        List<Department> departments = departmentService.queryAllDepartment();
        model.addAttribute("departments", departments);
        return "emp/add";
    }

    //添加员工
    @PostMapping("/emp")
    public String addEmp(Employee employee) {
        employeeService.addEmployee(employee);
        return "redirect:/emps";
    }

    @GetMapping("/emp/{id}")
    public String toUpdate(@PathVariable("id") int id, Model model) {
        //查出原来的数据
        Employee employee = employeeService.queryEmployeeById(id);
        model.addAttribute("emp", employee);
        //查出部门信息
        List<Department> departments = departmentService.queryAllDepartment();
        model.addAttribute("departments", departments);
        return "emp/update";
    }

    @RequestMapping("/updateEmp")
    public String updateEmp(Employee employee) {
        System.out.println("debug===>>>" + employee);
        employeeService.updateEmployee(employee);
        return "redirect:/emps";
    }

    @GetMapping("/emp/delete/{id}")
    public String deleteEmp(@PathVariable("id") int id) {
        employeeService.deleteEmployeeById(id);
        return "redirect:/emps";
    }

    @PostMapping("/emp/query")
    public String queryEmp(String likeName, Model model, Integer pageNum) {
        if (pageNum == null) {
            pageNum = 1;
        }
        PageHelper.startPage(pageNum, 5);
        Page<EmployeeDTO> EmployeeDTOs = employeeService.queryAllEmployee(likeName);
        model.addAttribute("emps", EmployeeDTOs);
        model.addAttribute("totalPage", EmployeeDTOs.getPages());
        model.addAttribute("totalCount", EmployeeDTOs.getTotal());
        return "emp/list";
    }

}
