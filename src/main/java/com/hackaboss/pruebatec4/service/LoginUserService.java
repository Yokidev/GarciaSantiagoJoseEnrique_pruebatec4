package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.model.LoginUser;
import com.hackaboss.pruebatec4.repository.LoginUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LoginUserService implements UserDetailsService {

    @Autowired
    private LoginUserRepository loginUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUser user = loginUserRepository.findByUserName(username);
        if (user == null){
            throw new UsernameNotFoundException("El usuario no se encuentra registrado");
        }
        System.out.println(user.getUserName());
        System.out.println(user.getPassword());

        return new User(user.getUserName(),user.getPassword(),new ArrayList<>());
    }
}
