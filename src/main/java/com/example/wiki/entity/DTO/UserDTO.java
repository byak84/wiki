package com.example.wiki.entity.DTO;

import com.example.wiki.entity.Role;
import com.example.wiki.entity.User;
import lombok.Getter;

import java.util.Set;

@Getter
public class UserDTO {
    private final String firstname;
    private final String lastname;
    private final String username;
    private final Set<Role> roles;

    public UserDTO(User user) {
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.username = user.getUsername();
        this.roles = user.getRoles();
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", roles=" + roles +
                '}';
    }
}
