package com.example.wiki.controller;

import com.example.wiki.entity.Post;
import com.example.wiki.entity.Serialize;
import com.example.wiki.entity.User;
import com.example.wiki.service.PostService;
import com.example.wiki.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;
//--------------


    @GetMapping
    @JsonView(Serialize.PostWithUser.class)
    public ResponseEntity<List<Post>> getPosts() {
        return new ResponseEntity<>(postService.getAll(), HttpStatus.OK);
    }

//    @GetMapping("/")
//    @JsonView(Serialize.PostWithUser.class)
//    public ResponseEntity<List<Post>> MsgList() {
//        return new ResponseEntity<>(postService.getAll(), HttpStatus.OK);
//    }

    @GetMapping("/{id}")
    @JsonView(Serialize.PostWithUser.class)
    public Post getPost(@PathVariable("id") Long id) {
        return postService.getById(id);
    }

//    @GetMapping("/mini/{id}")
//    @JsonView(Serialize.PostView.class)
//    public Post getMsgMini(@PathVariable("id") Long id) {
//        return postService.getById(id);
//    }


    @PostMapping("add")
    public Post addPost(@RequestBody Post post, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        post.setUser(user);
        post.setDate(new Date());
        return postService.addPost(post);
    }

    @PostMapping("/{id}")
    public Post updatePost(@PathVariable("id") Post post) {
        return postService.addPost(post);
    }

    @DeleteMapping("/{id}")
    public void deleteMsg(@PathVariable("id") Long id) {
        postService.deleteById(id);
    }
}
