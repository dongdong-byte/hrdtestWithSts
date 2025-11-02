package com.kim.hrdtest.employees.mapper;


import com.kim.hrdtest.employees.domain.Employees;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeesMapper {

//    create(사원등록)
    void  createEmployee(Employees employees);
//    Read(전체 조회)
    List<Employees> selectAllEmployees();
//    Read(ID로 조회)
    Employees selectEmployeeById(Long empNo);
//    update (사원수정)
    void updateEmployee(Employees employees);
//    delete(사원삭제)
    void deleteEmployee(Long empNo);
//    과제 2-(1) : 급여가 3,000,000이상인 사원의 이름과 부서 조회
    List<Employees> selectEmployeesBySalary(Integer salary);
//    과제 2-(2) :

    List<Employees> selectEmployeesByDept(String dept);

}
