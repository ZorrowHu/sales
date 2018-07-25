package com.platform.sales.surface;

import com.platform.sales.entity.Users;
import com.platform.sales.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public Users addUsers(Users user) {
        return usersRepository.save(user);
    }

    @Override
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public Users findByNameAndRole(String name, String role) {
        return usersRepository.findByUserNameAndUserRole(name, role);
    }

    @Override
    public Users findByNameAndPwd(String name, String pwd) {
        return usersRepository.findByUserNameAndPassword(name, pwd);
    }

    @Override
    public Users userLogin(String name, String pwd) {
        return usersRepository.login(name, pwd);
    }

    @Override
    public Users userRegister(String name, String role) {
        return usersRepository.register(name, role);
    }
}
