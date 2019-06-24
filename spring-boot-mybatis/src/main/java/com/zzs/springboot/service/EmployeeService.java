package com.zzs.springboot.service;

import com.zzs.springboot.bean.Employee;

public interface EmployeeService {
    public Employee getEmpById(Integer id);

    public void insertEmp(Employee employee);

    public void addEmp(Employee employee);
}
