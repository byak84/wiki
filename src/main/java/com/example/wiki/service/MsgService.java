package com.example.wiki.service;

import com.example.wiki.dao.MsgRepo;
import com.example.wiki.entity.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MsgService {
    @Autowired
    MsgRepo msgRepo;

    public List<Msg> getAll() {
        return msgRepo.findAllByOrderById();
    }

    public Msg getById(Long id) {
        return msgRepo.getById(id);

    }

    public Msg addMsg(Msg msg) {
        return msgRepo.saveAndFlush(msg);
    }

    public void deleteById(Long id) {
        msgRepo.deleteById(id);
    }
}
