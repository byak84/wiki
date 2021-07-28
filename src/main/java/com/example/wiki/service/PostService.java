package com.example.wiki.service;

import com.example.wiki.dao.PostRepo;
import com.example.wiki.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    PostRepo postRepo;

    public List<Post> getAll() {
        return postRepo.findAllByOrderById();
    }

    public Post getById(Long id) {
        return postRepo.getById(id);

    }

    public Post addPost(Post post) {
        return postRepo.saveAndFlush(post);
    }

    public void deleteById(Long id) {
        postRepo.deleteById(id);
    }
}
