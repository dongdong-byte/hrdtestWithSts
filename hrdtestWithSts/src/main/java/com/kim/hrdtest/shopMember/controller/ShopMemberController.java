package com.kim.hrdtest.shopMember.controller;


import com.kim.hrdtest.shopMember.domain.ExShopMemeber;
import com.kim.hrdtest.shopMember.domain.ShopMemeber;
import com.kim.hrdtest.shopMember.service.ShopMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopMember")
@RequiredArgsConstructor
public class ShopMemberController {
    private final ShopMemberService shopMemberService;
//전체 member보여주기
//    @GetMapping
//    public String ListShopMembers() {
//
//
//
//        return shopMemberService.getmyString();
//    }
//@GetMapping("integer")
//    public Integer ListShopMembers2() {
//    return shopMemberService.getmyInteger();
//    }
//@GetMapping
//    public String myShopMember() {
//    return shopMemberService.getExShopMember();
//}

    @GetMapping
    public List<ExShopMemeber>ListShopMembers() {
    return shopMemberService.getAllShopMember();
    }


//    http://localhost:8085/api/shopMember/15
    @GetMapping("/{id}")
    public ResponseEntity<ExShopMemeber> getShopMemberById(@PathVariable long id) {
ExShopMemeber exShopMemeber = shopMemberService.memberfindById(id);
return ResponseEntity.ok(exShopMemeber);



        }

}
