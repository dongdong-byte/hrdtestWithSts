package com.kim.hrdtest.employees.service;


import com.kim.hrdtest.employees.domain.Employees;
import com.kim.hrdtest.employees.mapper.EmployeesMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
//@Builder
//bulider은 객체 생성용 (DTO,Entity에 적용)

@Transactional(readOnly = true)
public class EmployeesService {

    private final EmployeesMapper employeesMapper;
//    Read 전체 조회
    public List<Employees> getAllEmployees(){
        log.debug("전체 학생조회");
        return employeesMapper.selectAllEmployees();

    }

//Read id 로 조회
    public Employees getEmployeeById(Long empNo){
        log.info("사원조회 - 사번 : {}",empNo);
Employees employees = employeesMapper.selectEmployeeById(empNo);
if(employees == null){
    throw  new IllegalArgumentException("존재 하지않는 사원입니다. :"+ empNo);
}
return employees;
    }
//    1.create(사원등록->3명 등록해야함)
    @Transactional
public void createEmployeesThreeMembers(){
//문제가 있는코드임
//    1.salary가 없음-> 제약조건(2,000,000이상 필수)
//    DB에 저장안됨-> 단순히 list만 반환
//    Empno직접설정 X -> auto_increment로 자동으로 만들어지는데 수동으로 만들이유가 없다.
        //        return List.of(
//                Employees.builder().EmpNo(1L).EmpName("홍길동").Dept("영업부").HireDate(LocalDate.parse("2020-03-11")).build(),
//                Employees.builder().EmpNo(2L).EmpName("이순신").Dept("인사부").HireDate(LocalDate.parse("2019-07-15")).build(),
//                Employees.builder().EmpNo(3L).EmpName("강감찬").Dept("개발부").HireDate(LocalDate.of(2021,01,10)).build()
//
//        );

    log.info("Employee 테이블에 다음 데이터를 삽입하시오.");
    List<Employees>inintialEmployees = List.of(
            Employees.builder()
                    .EmpName("홍길동")
                    .Dept("영업부")
                    .HireDate(LocalDate.parse("2020-03-11"))
                    .Salary(2500000)
                    .build(),
            Employees.builder().
                    EmpName("이순신")
                    .Dept("인사부")
                    .HireDate(LocalDate.parse("2019-07-15"))
                    .Salary(3200000)
                    .build(),
                Employees.builder().
                        EmpName("강감찬")
                        .Dept("개발부")
                        .HireDate(LocalDate.of(2021,01,10))
                        .Salary(2800000).build()
    );
//    단일 등록 메서드 재활용
    for(Employees employees : inintialEmployees){
        insertEmployees(employees);
    }
    log.info("초기 사원 데이터 생성 완료 - 총 {}명", inintialEmployees.size());
}
//    2.create(사원등록)
@Transactional
    public  void insertEmployees(Employees employees){
        log.info("사원 등록 시작 - 이름 : {}",employees.getEmpName());
//        필수 필드 검증
    validationRequriedFields(employees);
//    데이터 형식 검증
    validationRequriedFormat(employees);
//    데이터 가공
    employees.setEmpName(employees.getEmpName().trim());
    employees.setDept(employees.getDept().trim());
//    저장
    employeesMapper.createEmployee(employees);
    log.info("사원 등록 완료 - 이름 : {}",employees.getEmpName());
}
    //    update(사원수정)
    @Transactional
    public void  updateEmployees(Employees employees){
        log.info("사원 등록 시작 - 이름 : {}",employees.getEmpName());
//        존재 여부 확인
        Employees existingEmployee = employeesMapper.selectEmployeeById(employees.getEmpNo());
        if(existingEmployee == null){
            throw new IllegalArgumentException("존재 하지않는 사원입니다. :"+ employees.getEmpName());
        }
//        필수 필드 검증
        validationRequriedFields(employees);
//        데이터 형식 검증
        validationRequriedFormat(employees);
    }
//    delete(사원 삭제)
    @Transactional
    public void  deleteEmployees(Long empNo){
        log.info("사원삭제 시작 - 사번 : {}",empNo);
        Employees employees = employeesMapper.selectEmployeeById(empNo);
        if(employees == null){
            throw new IllegalArgumentException("존재하지 않는 학생입니다. 사번 : " + empNo);
        }
    }
//    과제1 :  부서가 '개발부'인 사원의 사번, 이름, 급여 조회
    public List<Employees> getEmployeesByDept(String dept){
        log.info("부서별 사원 조회 - 부서 : {}", dept);
        List<Employees> employees = employeesMapper.selectEmployeesByDept(dept);
        if(employees.isEmpty()){
            log.warn("해당부서에 사람이 없습니다. - 부서 : {}", dept);
        }
        return employees;
    }
//    과제 2 :급여가 3,000,000이상인 사원의 이름과 부서 조회
    public List<Employees> getEmployeesBySalary(Integer minsalary){
        log.info(" 급여가 3,000,000이상인 사원 조회 - 급여 : {} ",minsalary );
        List<Employees>employees = employeesMapper.selectEmployeesBySalary(minsalary);
        if(employees.isEmpty()){
            log.info(" 급여가 3,000,000이상인 사원 이 없습니다. - 급여 : {} ",minsalary );
        }
        return employees;
    }




//    검증 메서드
    private void validationRequriedFormat(Employees employees) {
//        이름은 필수입니다.EmpName ( NOT NULL)
        if(employees.getEmpName() == null || employees.getEmpName().trim().isEmpty()){
            throw new IllegalArgumentException("이름은 필수입니다." );
        }
//        부서는 필수 입니다. Dept ( NOT NULL)
        if(employees.getDept() == null || employees.getDept().trim().isEmpty()){
            throw new IllegalArgumentException("부서는 필수입니다." );
        }
//        사번은 -1 이하 일수가 없습니다.
        if(employees.getEmpNo() == null ||  employees.getEmpNo().longValue() < 0){
            throw new IllegalArgumentException("사번은 -1 이하 일수가 없습니다." );
        }
//  입사일은 미래일수가 없습니다.
        if(employees.getHireDate().isAfter(LocalDate.now())){
            throw new IllegalArgumentException("입사일은 미래일수가 없습니다." );
        }
    }

    private void validationRequriedFields(Employees employees) {
//        이름길이 검증
        if(employees.getEmpName().length() <2){
            throw  new IllegalArgumentException("이름은 2자 이상이어야합니다");
        }
        if(employees.getEmpName().length() >50){
            throw  new IllegalArgumentException("이름은 50자 이하이어야합니다");
        }
    }

}
