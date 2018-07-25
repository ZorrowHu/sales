package com.platform.sales.repository;


import com.platform.sales.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Integer> {

//    @Override
    public Users findByUserNameAndUserRole(String userName, String userRole);

    public Users findByUserNameAndPassword(String userName, String password);
}
