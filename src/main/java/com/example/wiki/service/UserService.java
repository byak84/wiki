package com.example.wiki.service;

import com.example.wiki.Email.EmailMessage;
import com.example.wiki.Email.MailRuSender;
import com.example.wiki.dao.UserRepo;
import com.example.wiki.entity.Role;
import com.example.wiki.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private MailRuSender mailRuSender;

    @Value("${backendHost}")
    private String backendHost;


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

    public User addUser(User user) throws Exception {
        User candidate = userRepo.findByUsername(user.getUsername());
        if (candidate == null) {
            user.setRoles(Collections.singleton(Role.ROLE_USER));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.saveAndFlush(user);
            sendActivationLink(user);
            return user;
        } else throw new Exception("User exists!");
    }

    public boolean sendActivationLink(User user) {
        EmailMessage emailMessage = new EmailMessage("Activation link...",
                "Hello "+ user.getFirstname() + "\n" +
                "Please visit "+backendHost+"/user/activate/"+user.getUuid()+ " to activate your user account!");
        return mailRuSender.sendMessage(user.getEmail(), emailMessage);
    }

    public User getByUUID(String uuid) throws Exception {
        User user = userRepo.findByUuid(uuid);
        if (user == null) throw new Exception("User not found!");
        return user;
    }

    public void activate(User user) {
        user.setActive(true);
        userRepo.saveAndFlush(user);
    }
}
