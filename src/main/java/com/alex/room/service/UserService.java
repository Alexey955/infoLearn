package com.alex.room.service;

import com.alex.room.domain.User;
import com.alex.room.enums.Roles;
import com.alex.room.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    public boolean addUser(User user, String role) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if(userFromDb != null) {
            return false;
        }

        switch (Roles.valueOf(role)){
            case USER:
                user.setRoles(Collections.singleton(Roles.USER));
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepo.save(user);
                break;
            case ADMIN:
                user.setRoles(Collections.singleton(Roles.ADMIN));
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepo.save(user);
                break;
        }
        return true;
    }
}
