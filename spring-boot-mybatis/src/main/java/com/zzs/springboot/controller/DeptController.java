package com.zzs.springboot.controller;

import com.zzs.springboot.bean.Department;
import com.zzs.springboot.bean.Employee;
import com.zzs.springboot.service.DepartmentService;
import com.zzs.springboot.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeptController {

    private static Logger logger = LoggerFactory.getLogger(DeptController.class);

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/dept/{id}")
    public Department getDepartment(@PathVariable("id") Integer id) {
        return departmentService.getDeptById(id);
    }

    @GetMapping("/dept")
    public Department insertDept(Department department) {
        departmentService.insertDept(department);
        return department;
    }

    @GetMapping("/emp/{id}")
    public Employee getEmp(@PathVariable("id") Integer id) {
        logger.info("33333333333333333333");
        return employeeService.getEmpById(id);
    }

    @GetMapping("/interceptor/dept")
    public Department interceptorinsertDept(Department department) {
        logger.info("/interceptor/dept--------------22222");
        departmentService.insertDept(department);
        return department;
    }

}
