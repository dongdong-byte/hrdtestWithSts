package com.kim.hrdtest.shopMember.service;

import com.kim.hrdtest.shopMember.domain.ExShopMemeber;
import com.kim.hrdtest.shopMember.mapper.ShopMemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShopMemberService {

    private ShopMemberMapper shopMemberMapper;

    public List<ExShopMemeber> getAllShopMember() {
        return null;
    }

    public ExShopMemeber memberfindById(long id) {
        return null;
    }

//    public List<ExShopMemeber> getAllShopMember() {
////        mapper오브젝트에게 값을 요
//        return shopMemberMapper.findAll();
//    }


//    public ExShopMemeber getAllShopMember(){
////        자바 방식
//        ExShopMemeber exShopMemeber = new ExShopMemeber();
//        exShopMemeber.setName("홀길동");
//
//        return exShopMemeber;
//
//    };
//
//    public String getmyString() {
//        ExShopMemeber exShopMemeber = new ExShopMemeber();
//        exShopMemeber.setName("홀길동");
//
//        return exShopMemeber.getName();
//    }

//    public Integer getmyInteger() {
//        return 999;
//    }
//
//    public String getExShopMember() {
//        ExShopMemeber exShopMemeber2 = new ExShopMemeber();
//        exShopMemeber2.setName("ㅇ리지매");
//        return exShopMemeber2.getName();
//    }
//
//    public ExShopMemeber memberfindById(long id) {
//      ExShopMemeber exShopMemeber = shopMemberMapper.findById(id);
//
//        return exShopMemeber;
//    }


}
