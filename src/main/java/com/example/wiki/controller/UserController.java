package com.example.wiki.controller;

import com.example.wiki.entity.Serialize;
import com.example.wiki.entity.User;
import com.example.wiki.jwt.JwtUtil;
import com.example.wiki.response.AuthResponse;
import com.example.wiki.response.ErrorResponse;
import com.example.wiki.service.CustomUserDetailsService;
import com.example.wiki.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService authService;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;


    @GetMapping
    @JsonView({Serialize.UserView.class})
    public ResponseEntity<List<User>> getAll() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User auth) {
        try {
            UserDetails userDetails = authService.loadUserByUsername(auth.getUsername());
            authManager.authenticate(new UsernamePasswordAuthenticationToken(userDetails, auth.getPassword()));

            User user = userService.findByUsername(auth.getUsername());

            AuthResponse  result = jwtUtil.generateTokens(user);
            System.out.println(result);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(Collections.singletonList(e.getLocalizedMessage())), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            System.out.println(user);
            return new ResponseEntity<>(userService.addUser(user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(Collections.singletonList(e.getLocalizedMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/activate/{uuid}")
    public ResponseEntity<?> activate(@PathVariable String uuid) {
        try {
            User user = userService.getByUUID(uuid);
            userService.activate(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(Collections.singletonList(e.getLocalizedMessage())), HttpStatus.BAD_REQUEST);
        }
    }

}
