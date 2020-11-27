package com.example.wiki.service;

import com.example.wiki.dao.RoleRepo;
import com.example.wiki.dao.UserRepo;
import com.example.wiki.entity.Role;
import com.example.wiki.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepo userRepo;
    @Autowired
    public void setUserRepo(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    private RoleRepo roleRepo;
    @Autowired
    public void setRoleRepo(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    private PasswordEncoder passwordEncoder;
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    //------------------------
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }

    public User findByLogin(String login) {
        return userRepo.findByLogin(login);
    }

    public Optional<User> updateById(Long id, User usr) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findById(id);
        if (user == null) {
            throw new UsernameNotFoundException("Bad user id");
        }
        usr.setId(user.get().getId());
        userRepo.saveAndFlush(usr);
        return Optional.of(usr);
    }

    public Optional<User> updateByLogin(String login, User usr) {
        Optional<User> user = Optional.ofNullable(userRepo.findByLogin(login));
        if (user == null) {
            throw new UsernameNotFoundException("Bad login");
        }
        usr.setId(user.get().getId());
        return Optional.of(usr);
    }

    public User addUser(User user) throws Exception {
        if (user != null) {
            List<Role> roles = new ArrayList<Role>();
            roles.add(roleRepo.findByName("ROLE_USER"));
            user.setRoles(roles);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);
            return user;
        } else throw new Exception("User is empty");
    }

    public User findByLoginAndPassword(String login, String password) {
        User user = findByLogin(login);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }
}
