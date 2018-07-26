package com.platform.sales.repository;

import com.platform.sales.entity.Account;
import com.platform.sales.entity.SellerInfo;
import com.platform.sales.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SellerinfoRepository extends JpaRepository<SellerInfo,Integer> {
    @Query(value = "select s from SellerInfo s where s.user.userId=?1")
    List<SellerInfo> findSellerInfosByUser_UserId(Integer id);
    @Query(value="select u from Users u where u.userId=?1")
    List<Users> getUser(Integer id);
}
