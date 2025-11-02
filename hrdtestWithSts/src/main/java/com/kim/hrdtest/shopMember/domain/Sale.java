package com.kim.hrdtest.shopMember.domain;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sale {
    private Long saleNo;
    private Long custNo;
    private Integer pCost;
    private Integer amount;
    private  Integer price;
    private  String pCode;
}
