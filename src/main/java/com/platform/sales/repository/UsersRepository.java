package com.platform.sales.repository;


import com.platform.sales.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsersRepository extends JpaRepository<Users, Integer> {

//    @Override
    public Users findByUserNameAndUserRole(String userName, String userRole);

    public Users findByUserNameAndPassword(String userName, String password);

    // JPQL
    @Query(value = "select user from Users user where user.userName = ?1 and user.password = ?2")
    Users login(String name, String pwd);
}
