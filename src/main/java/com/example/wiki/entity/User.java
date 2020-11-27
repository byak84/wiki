package com.example.wiki.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "usr")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;

    private String login;
    private String password;

    @JsonIgnoreProperties(value = "users", allowSetters = true)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "usr_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("user")
    private List<Msg> messages;

    public boolean isValid() {
        if ((this.login.equals("")) || (this.password.equals("")) ) return false;
        else return true;
    }

}
