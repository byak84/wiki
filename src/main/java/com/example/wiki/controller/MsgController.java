package com.example.wiki.controller;

import com.example.wiki.entity.Msg;
import com.example.wiki.entity.User;
import com.example.wiki.service.MsgService;
import com.example.wiki.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/msg")
//@PreAuthorize("hasAuthority('ADMIN')")
public class MsgController {
    MsgService msgService;
    @Autowired
    public void setMsgService(MsgService msgService) {
        this.msgService = msgService;
    }

    UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Msg>> MsgList() {
        return new ResponseEntity<>(msgService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Msg getMsg(@PathVariable("id") Long id) { return msgService.getById(id); }

    @PostMapping("add")
    public Msg addMsg(@RequestBody Msg msg, Principal principal) {
        User user = userService.findByLogin(principal.getName());
        msg.setUser(user);
        return msgService.addMsg(msg);
    }

    @PutMapping("{id}")
    public Msg updateMsg(@PathVariable("id") Integer id, @RequestBody Msg msg) {
        return msgService.addMsg(msg);
    }

    @DeleteMapping("{id}")
    public void deleteMsg(@PathVariable("id") Long id) {
        msgService.deleteById(id);
    }
}
