package com.zzs.springboot;

import com.zzs.springboot.bean.Employee;
import com.zzs.springboot.service.DepartmentService;
import com.zzs.springboot.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootMybatisApplicationTests {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartmentService departmentService;

    @Test
    public void contextLoads() {
//        System.out.println(employeeService.getEmpById(1));
        Employee employee = new Employee();
        employee.setdId(2);
        employee.setLastName("张三");
        employee.setEmail("zhangs@qq.com");
        employeeService.insertEmp(employee);
    }

}
