package com.hackaboss.pruebatec4.repository;

import com.hackaboss.pruebatec4.model.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginUserRepository extends JpaRepository<LoginUser,Long> {
    LoginUser findByUserName(String userName);
}
