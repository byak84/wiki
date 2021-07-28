package com.example.wiki.Email;

public interface EmailSender {
    public boolean sendMessage(String to, EmailMessage emailMessage);

}
