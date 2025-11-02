package com.kim.hrdtest.shopMember.domain;


import lombok.*;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopMemeber {
    private Long custNo;
    private String custName;
    private String phone;
    private String address;
    private Date joinDate;
    private String grade;
    private String city;
}
