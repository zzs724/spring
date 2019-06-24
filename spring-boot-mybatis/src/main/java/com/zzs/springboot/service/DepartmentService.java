package com.zzs.springboot.service;

import com.zzs.springboot.bean.Department;

public interface DepartmentService {

    public Department getDeptById(Integer id);

    public int deleteById(Integer id);

    public int insertDept(Department department);

    public int updateDept(Department department);
}
