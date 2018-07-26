package com.platform.sales.repository;


import com.platform.sales.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsersRepository extends JpaRepository<Users, Integer> {

//    @Override
    Users findByUserNameAndUserRole(String userName, String userRole);

    Users findByUserNameAndPassword(String userName, String password);

    Users findByUserName(String name);

    // JPQL 用于查询用户登录信息是否合法
    @Query(value = "select user from Users user where user.userName = ?1 and user.password = ?2")
    Users login(String name, String pwd);
    // JPQL 用于查询注册信息是否重名
    @Query(value = "select user from Users user where user.userName = ?1 and user.userRole = ?2")
    Users register(String name, String pwd);

}
