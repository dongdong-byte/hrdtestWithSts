
package com.kim.hrdtest.shopMember.dto;
// 회원별 총 구매금액을 구하시오.
//(출력: 회원번호, 회원성명, 총금액)

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberTotalPrice {

    private   Long custNo;
    private String custName;
    private Integer totalPrice;

}
