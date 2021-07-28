package com.example.wiki.dao;

import com.example.wiki.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderById();

    Post getById(Long id);
}
