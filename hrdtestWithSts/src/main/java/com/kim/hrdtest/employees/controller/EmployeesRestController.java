package com.kim.hrdtest.employees.controller;

import com.kim.hrdtest.employees.domain.Employees;
import com.kim.hrdtest.employees.service.EmployeesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
//RestApi 설계
//1. 전체 조회 -> /api/employees
//2.과제 1 부서조회(Get)->/api/employees/dept/{dept}
//3.과제 2 급여 조회(Get) ->/api/employees/salary/{salary}
//4.단건조회(Get) ->/api/employees/{empNo}
//5.등록 (Post) ->/api/employees
//6.수정 (Put) ->/api/employees/{empNo}
//7.삭제 (Delete) ->/api/employees/{empNo}


@Slf4j
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeesRestController {
    private final EmployeesService employeesService;

    //    1.전체 조회
//    /api/employees
    @GetMapping
    private ResponseEntity<List<Employees>> getAllEmployees() {
        log.info("전체 학생조회 요청");
        List<Employees> employeesList = employeesService.getAllEmployees();
//        200ok와 함께 목록 반환
        return ResponseEntity.ok(employeesList);
    }

    //  2.  과제1 : 부서가 '개발부'인 사원의 사번, 이름, 급여를 조회하시오.
//    /api/employees/dept/{dept}
    @GetMapping("/dept/{dept}")
    private List<Employees> getByDept(@PathVariable String dept) {
        log.info("부서 요청");
        return employeesService.getEmployeesByDept(dept);
    }

    //    3.과제 2 : 급여가 3,000,000원 이상인 사원의 이름과 부서를 조회하시오.
//    /api/employees/salary/{salary}
    @GetMapping("/salary/{salary}")
    public List<Employees> getBySalary(@PathVariable Integer minsalary) {
        log.info("급여 조회");
        return employeesService.getEmployeesBySalary(minsalary);
    }

    //    4.단건조회(Get) ->/api/employees/{empNo}
    @GetMapping("/{empNo}")
    public ResponseEntity<Employees> getEmployeesByEmpNo(@PathVariable Long empNo) {
        log.info("API: 사원 조회 요청 - 사번 : {}", empNo);
        try {
            Employees employees = employeesService.getEmployeeById(empNo);
            return ResponseEntity.ok(employees);

        } catch (Exception ex) {
            ex.fillInStackTrace();
            log.warn("API: 사원을 찾을 수 없음", ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    //5.등록 (Post) ->/api/employees
    @PostMapping
    public ResponseEntity<Employees> createEmployees(@Valid @RequestBody Employees employees) {
        log.info("API: 사원  등록 요청 - 이름 : {}", employees.getEmpName());
        try {
//            service 계층에서 사원정보를 저장하고 ,ID가 부여된 객체를 반환한다고 가정
            employees.setEmpNo(employees.getEmpNo());
//            201 Created응답을 생성하고,Location헤더에 새로 생성된 리소스의 URl을 포함(RESTFul권장사항)
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{empNo}")
                                    .buildAndExpand(employees.getEmpNo()).toUri();

//                    201 created와 Location헤더를 함께 반환
            employeesService.updateEmployees(employees);
            return ResponseEntity.ok(employees);
        } catch (Exception ex) {
            ex.fillInStackTrace();
            log.warn("API: 사원을 찾을 수 없음", ex.getMessage());
//            요청 데이터 문제 등으로 실패한경우 400Bad Request
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    //6.수정 (Put) ->/api/employees/{empNo}
    @PutMapping("/{empNo}")
    public  ResponseEntity<Employees> updateEmployees(@PathVariable Long empNo, @Valid @RequestBody Employees employees) {
        log.info("API: 사원 수정 요청 - 사번 : {}", empNo);
        try {
            employees.setEmpNo(empNo);
//            service 에서 수정된 객체를 반환한다고 가정
            employeesService.updateEmployees(employees);
        } catch (IllegalArgumentException e) {
// 수정대상 사원을 찾을수 없는경우 404 not found
            log.warn("사원을 찾을수 없음", e.getMessage());
            return ResponseEntity.notFound().build();

        } catch (Exception ex) {
            ex.fillInStackTrace();
            log.error("사원 수정실패", ex);
//            기타 서버/로직 오류 의 경우 400 bad Request
            return ResponseEntity.badRequest().build();
        }
        return  ResponseEntity.ok(employees);
    }
//    7.삭제 (Delete) ->/api/employees/{empNo}
    @DeleteMapping("/{empNo}")
    public ResponseEntity<Employees>deleteEmployees(@PathVariable Long empNo){
        log.info("API: 사원 삭제 요청 - 사번 : {}", empNo);
        try {
            employeesService.deleteEmployees(empNo);
//            성공적인 삭제는 보통 204 no content 반환
            return ResponseEntity.noContent().build();
        }catch (IllegalArgumentException e){
//            삭제할 사원을 찾을수 없는 경우 404 NotFound
            log.warn("사원을 찾을수 없음", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

}

