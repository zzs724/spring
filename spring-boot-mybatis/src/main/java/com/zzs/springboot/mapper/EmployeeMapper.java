package com.zzs.springboot.mapper;

import com.zzs.springboot.bean.Employee;

public interface EmployeeMapper {


    public Employee getEmpById(Integer id);

    public void insertEmp(Employee employee);
}
