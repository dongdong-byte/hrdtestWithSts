package com.kim.hrdtest.employees.domain;



import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


import java.time.LocalDate;

@Data
//getter+setter+tostring+equals+hashcode+requriedArgConstructor
//엔티티에서 (JPA)클래스에서 @DATA를 사용하면 equals /hashcode가 연관 필드 를 건드려서 문제를 일으킬수 있습니다. 보통 엔티티에서는 getter,setter만 사용하는게 일반적임
//민감한 필드(비밀번호가) roString에 노출될수 있으니 주의해야한다.
//@DATA에 기본 생성자@NoArgsConstructo,전체 필드를 매게변수로 받는 생성자 @AllArgsConstructor는 포함되지 않는다.
//@Getter
//@Setter

//@RequiredArgsConstructor
//final 을 사용할때나 사용하는것
@AllArgsConstructor
//모든 필드를 인자로 받는 생성자 생성
//DTO나 테스트에서 모든필드를 바로 채워 객체 생성할때 편리합니다.
@NoArgsConstructor
//파라미터 없는 기본 생성자 생성(JPA엔티티에서 자주 필요)
@Builder
//빌더 패턴을 자동으로 만들어서 가독성 높은 객체 생성가능
//가독성 좋음 , 선택적 필드가 많을때 유용, 불변 객체와 잘 어울림(@Builder+@AllArgsConstructor+final)
//동일한 이름에 정적 팩토리 메서드가 있거나 생성자와 충동할수가 있음
//단순 DTO/데이터 클래스에서는 @DATA,@Bulider가 생산성을 크게 올려줍니다.
public class Employees {
// 사원번호 자동생성

//    auto_increment

    private  Long EmpNo;
//    사원이름
//    예외처리
//    1.이름은 필수
//    2.2자이상 50자 이하
//    3.이름은 중복될수 없다.
    @NotBlank(message = "이름은 필수 입니다.")
    @Size(min = 2,max = 50,message = "이름은 2자이상 50자 이하입니다")
//    중복 방지

    private String EmpName;
//부서명
//    1.부서명은 필수
@NotBlank(message = "부서명은 필수 입니다.")

    private String Dept;
//입사일
//    1.입사일은 필수
    @NotNull(message = "입사일은 필수 입니다.")

    private LocalDate HireDate;
//    급여
//    1.2,000,000원이상만입력가능하다
    @Min(value = 2000000 ,message = "급여는 2,000,000원이상만입력가능하다")

    private Integer Salary;

}
