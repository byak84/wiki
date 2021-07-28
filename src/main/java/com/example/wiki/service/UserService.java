package com.example.wiki.service;

import com.example.wiki.dao.UserRepo;
import com.example.wiki.entity.Role;
import com.example.wiki.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    //------------------------
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

//    public Optional<User> updateById(Long id, User usr) throws UsernameNotFoundException {
//        Optional<User> user = userRepo.findById(id);
//        if (user == null) {
//            throw new UsernameNotFoundException("Bad user id");
//        }
//        usr.setId(user.get().getId());
//        userRepo.saveAndFlush(usr);
//        return Optional.of(usr);
//    }
//
//    public Optional<User> updateByLogin(String login, User usr) {
//        Optional<User> user = Optional.ofNullable(userRepo.findByLogin(login));
//        if (user == null) {
//            throw new UsernameNotFoundException("Bad login");
//        }
//        usr.setId(user.get().getId());
//        return Optional.of(usr);
//    }

    public User addUser(User user) throws Exception {
        User candidate = userRepo.findByUsername(user.getUsername());
        if (candidate == null) {
            user.setRoles(Collections.singleton(Role.ROLE_USER));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.saveAndFlush(user);
            return user;
        } else throw new Exception("User exists!");
    }

}
