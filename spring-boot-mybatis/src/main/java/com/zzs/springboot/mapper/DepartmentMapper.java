package com.zzs.springboot.mapper;

import com.zzs.springboot.bean.Department;
import org.apache.ibatis.annotations.*;

@Mapper
public interface DepartmentMapper {


    @Select("select * from department where id=#{id}")
    public Department getDeptById(Integer id);

    @Delete("delete from department where id=#{id}")
    public int deleteById(Integer id);

    @Options(useGeneratedKeys = true,keyColumn = "id")
    @Insert("insert into department (departmentName) values(#{departmentName})")
    public int insertDept(Department department);

    @Update("update department set departmentName = #{departmentName} where id =#{id}")
    public int updateDept(Department department);

}
