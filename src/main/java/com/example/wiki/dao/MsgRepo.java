package com.example.wiki.dao;

import com.example.wiki.entity.Msg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MsgRepo extends JpaRepository<Msg, Long> {
    List<Msg> findAllByOrderById();
    Msg getById(Long id);
}
