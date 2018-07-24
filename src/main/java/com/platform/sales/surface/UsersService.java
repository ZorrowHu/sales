package com.platform.sales.surface;

import com.platform.sales.entity.Users;

import java.util.List;

public interface UsersService {


    // 添加新的用户
    Users addUsers(Users user);

    // 查询所有用户
    List<Users> getAllUsers();

    // 根据user_name和user_role查询用户
    Users findByNameAndRole(String name, String role);

}
