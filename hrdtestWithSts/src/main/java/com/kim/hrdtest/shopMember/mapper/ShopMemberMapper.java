package com.kim.hrdtest.shopMember.mapper;

import com.kim.hrdtest.shopMember.domain.ExShopMemeber;
import com.kim.hrdtest.shopMember.domain.ShopMemeber;
import com.kim.hrdtest.shopMember.dto.MemberTotalPrice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Mapper
public interface ShopMemberMapper {
//    전체보여주기
//    1.전체 목록확인:READ
//select * from ShopMember order by CustNo;
//    쿼리를 주석으로 남겨놓아야 직관적으로 이해 할수 잇다
    List<ShopMemeber> findAll();
//2.개별 목록 확인 :READ
//    select  * from ShopMember where CustNo=#{CustNo};
    ShopMemeber findByCustNo(long CustNo);
//    3.추가하기 : CREATE
//insert into ShopMember value (#{CustNo},#{CustName},#{Phone},#{Address},#{JoinDate},#{Grade},#{City})
    void addmember(ShopMemeber shopMemeber);
//    4.업데이트 하기 : UPDATE
    void updatemember(ShopMemeber shopMemeber);
//5.삭제하가 : DELETE
    void deletemember(long CustNo);
//    6.고객등급이 A등급인 회원의 이름, 전화번호, 가입일자를 조회.
   List<ShopMemeber> findByGrade(@Param("grade") String grade);
//   7.가입일자가 2020년 이후( >2020)인 회원을 조회하시오.
    List<ShopMemeber> findByMembersJoinDateAfter2020(@Param("joinDate") String joinDate);
//    8.회원별 총 구매금액을 구하시오. (출력: 회원번호, 회원성명, 총금액)
//    회원별 총금액을 구하는 조건이라면 이건 특정 조건이 없는 그룹 집계 쿼리(Group) 이다
//    단순히 모든 회원별 합계를 구하는거라서 매개변수가 필요가 없다.
    List<MemberTotalPrice> findByTotalPriceByMembers();
}
