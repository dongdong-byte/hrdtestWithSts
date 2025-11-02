package com.kim.hrdtest.employees.controller;


import com.kim.hrdtest.employees.domain.Employees;
import com.kim.hrdtest.employees.service.EmployeesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeesController {
    private final EmployeesService employeesService;
//    전체 학생 목록 조회 READ-
    @GetMapping
public String list(Model model){
    log.info("사원 조회 목록 요청");
    model.addAttribute("employees",employeesService.getAllEmployees());
        return  "employees/list";


}
//학생 등록 폼CREATE
    @GetMapping("/new")
    public String createForm(Model model){
    log.info("사원 등록 폼 요청");
    model.addAttribute("employees", new Employees());

    return "employees/form";
    }
//    학생 수정폼 UPDATE
    @GetMapping("/{empNo}/edit")
    public String editForm(@PathVariable Long empNo,
                           Model model){
    log.info("사원 수정 폼 요청 - 사번 : {}",empNo);
    try {
        Employees employees = employeesService.getEmployeeById(empNo);
        if(employees == null ){
            log.warn("사원이 없습니다 - 사번 : {}",empNo);

            return "redirect:/employees";
        }
        model.addAttribute("employees",employees);
        return "employees/form";
    }catch (Exception e){
        e.fillInStackTrace();
        log.warn("사원 조회중 오류 발생" ,e.getMessage());
        return "redirect:/employees";

    }

    }

//폼 제출 처리 (등록 수정) update+create
    @PostMapping
    public String create(@ModelAttribute Employees employees
    , BindingResult bindingResult ,RedirectAttributes redirectAttributes){
//    검증 오류가 있으면 form으로 돌아가기
        if(bindingResult.hasErrors()){
            log.warn("사원정보 검증 실패 : {}" ,bindingResult.getAllErrors());
            return "employees/form";
        }
        try {
            if(employees.getEmpNo() == null){
//                등록
                log.info("새 사원 등록 요청 {}" ,employees.getEmpName());
                employeesService.insertEmployees(employees);
                redirectAttributes.addFlashAttribute("success",employees.getEmpName()+"님이 등록 되었습니다.");


            }else {
//                수정
                log.info("새 사원 수정요청 사번 : {}, 이름 : {} ",employees.getEmpNo(),employees.getEmpName());
                employeesService.updateEmployees(employees);
                redirectAttributes.addFlashAttribute("success",employees.getEmpName()+"님의 정보가 수정되었습니다.");


            }
            return  "redirect:/employees";
        }catch (Exception e){
            e.fillInStackTrace();
            log.error("사원 정보 저장중에 오류가 발생했습니다." ,e.getMessage());
            redirectAttributes.addFlashAttribute("error",e.getMessage());
            return "redirect:/employees";
        }
    }
//    삭제 DELETE
    @PostMapping("/{empNo}/delete")
    public  String delete(@PathVariable Long empNo ,RedirectAttributes redirectAttributes){
    log.info("사원 삭제 요청 사번 : {}",empNo);
    try {
//        삭제전 학생 존재 확인
        Employees employees = employeesService.getEmployeeById(empNo);
        if(employees == null){
            log.warn("삭제할 학생을 찾을 수가 없음 - 사번 : {}",empNo);
            redirectAttributes.addFlashAttribute("error"," 삭제할 사원을 찾을수 없습니다.");
            return "redirect:/employees";

        }
        String employeesName = employees.getEmpName();
        employeesService.deleteEmployees(empNo);
        log.info("사원 삭제 완료 사번 : {}, 이름 : {}",empNo,employeesName);
        redirectAttributes.addFlashAttribute("success",employeesName + "님이 삭제 되었습니다.");


    }catch (Exception e){
        e.fillInStackTrace();
        log.error("사원 정보 삭제 중에 오류가 발생했습니다." ,e.getMessage());
        redirectAttributes.addFlashAttribute("error",e.getMessage());


    }
        return "redirect:/employees";
    }
//    전체 예외처리는 이미  GlodalException 클래스에서 정함
}
