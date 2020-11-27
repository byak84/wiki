package com.example.wiki.controller;

import com.example.wiki.entity.User;
import com.example.wiki.jwt.JwtUtil;
import com.example.wiki.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private JwtUtil jwtUtil;

    @Autowired
    public void setJwtUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> myUsers = userService.getAllUsers();
        return new ResponseEntity<>(myUsers, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User auth) {
        System.out.println(auth);
        User user = userService.findByLoginAndPassword(auth.getLogin(), auth.getPassword());
        if (user != null) {
            return new ResponseEntity<>(jwtUtil.generateToken(user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        if (user.isValid()) {
            try {
                userService.addUser(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        else return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
    }

}
