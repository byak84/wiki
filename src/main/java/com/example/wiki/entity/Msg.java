package com.example.wiki.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Msg {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("messages")
    private User user;

    public Msg() {

    }

    public Msg(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public String getShot() {
        return (this.text.length() > 50) ? this.text.substring(0,50)+" ...->" : this.text;
    }

    public boolean isLong() {
        return this.text.length() > 50;
    }

}