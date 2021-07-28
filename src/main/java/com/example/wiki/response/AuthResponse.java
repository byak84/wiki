package com.example.wiki.response;

import com.example.wiki.entity.DTO.UserDTO;
import lombok.Data;

@Data
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private UserDTO user;

    public AuthResponse(String accessToken, String refreshToken, UserDTO user) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.user = user;
    }

    @Override
    public String toString() {
        return "AuthResponse { " +
                "\n\t accessToken='" + accessToken  + '\'' +
                ", \n\t refreshToken='" + refreshToken + '\'' +
                ", \n\t user=" + user +
                "\n}";
    }
}
