package com.example.wiki.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Serialize.PostView.class)
    private Long id;

    @JsonView(Serialize.PostView.class)
    private String title;

    @JsonView(Serialize.PostView.class)
    private String text;

    @JsonView(Serialize.PostView.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    @JsonView(Serialize.PostWithUser.class)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties(value = "postList")
    private User user;

    public Post() {
        date = new Date();
    }

//    public Msg(String title, String text) {
//        this.title = title;
//        this.text = text;
//    }

//    public String getShot() {
//        return (this.text.length() > 50) ? this.text.substring(0,50)+" ...->" : this.text;
//    }

//    public boolean isLong() {
//        return this.text.length() > 50;
//    }

}