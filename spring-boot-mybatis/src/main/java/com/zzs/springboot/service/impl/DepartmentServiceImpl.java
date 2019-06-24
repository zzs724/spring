package com.zzs.springboot.service.impl;

import com.zzs.springboot.bean.Department;
import com.zzs.springboot.mapper.DepartmentMapper;
import com.zzs.springboot.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public Department getDeptById(Integer id) {
        return departmentMapper.getDeptById(id);
    }

    @Override
    public int deleteById(Integer id) {
        return departmentMapper.deleteById(id);
    }

    @Override
    public int insertDept(Department department) {
        int i = departmentMapper.insertDept(department);
        int a = 1/0;
        return i;
    }

    @Override
    public int updateDept(Department department) {
        return 0;
    }
}
