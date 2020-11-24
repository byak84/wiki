package com.example.wiki.controller;

import com.example.wiki.entity.Msg;
import com.example.wiki.entity.User;
import com.example.wiki.service.MsgService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600, allowCredentials = "true", allowedHeaders = "Authorization", methods =
        {RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.POST})
@RestController
@RequestMapping("/msg")
//@PreAuthorize("hasAuthority('ADMIN')")
public class MsgController {
    final MsgService msgService;

    public MsgController(MsgService msgService) {
        this.msgService = msgService;
    }

    @GetMapping
    public ResponseEntity<List<Msg>> MsgList() {
        return new ResponseEntity<>(msgService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Msg getMsg(@PathVariable("id") Long id) { return msgService.getById(id); }

    @PostMapping("add")
    public Msg addMsg(@RequestBody Msg msg) {
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
