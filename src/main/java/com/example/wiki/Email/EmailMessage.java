package com.example.wiki.Email;

import lombok.Data;

@Data
public class EmailMessage {
    private String Subject;
    private String text;

    public EmailMessage(String subject, String text) {
        Subject = subject;
        this.text = text;
    }
}
