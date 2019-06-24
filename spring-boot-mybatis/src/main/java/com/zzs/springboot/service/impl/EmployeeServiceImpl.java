package com.zzs.springboot.service.impl;

import com.zzs.springboot.bean.Department;
import com.zzs.springboot.bean.Employee;
import com.zzs.springboot.mapper.EmployeeMapper;
import com.zzs.springboot.service.DepartmentService;
import com.zzs.springboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private DepartmentService departmentService;


    @Override
    public Employee getEmpById(Integer id) {
        return employeeMapper.getEmpById(id);
    }

    @Override
    public void insertEmp(Employee employee) {
        Department department = new Department();
        department.setDepartmentName("测试事务");
        employeeMapper.insertEmp(employee);
/*
        //两种
        try {
            departmentService.insertDept(department);
        } catch (Exception e) {
            System.out.println(e);
        }*/

    }

    @Override
    public void addEmp(Employee employee) {

        employeeMapper.insertEmp(employee);


    }
}
