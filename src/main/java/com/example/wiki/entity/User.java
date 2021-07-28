package com.example.wiki.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "usr")
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView({Serialize.PostWithUser.class, Serialize.UserView.class})
    private Long id;

    @JsonView({Serialize.PostWithUser.class, Serialize.UserView.class})
    private String firstname;

    @JsonView({Serialize.PostWithUser.class, Serialize.UserView.class})
    private String lastname;

    @JsonView({Serialize.PostWithUser.class, Serialize.UserView.class})
    private String email;

    @JsonView({Serialize.PostWithUser.class, Serialize.UserView.class})
    private String username;
    @JsonView({Serialize.UserView.class})
    private String password;

    @JsonView({Serialize.UserView.class})
    private boolean active;

    @JsonView({Serialize.UserView.class})
    private String uuid_link;


    @JsonView({Serialize.UserView.class})
    @ElementCollection(fetch = FetchType.EAGER, targetClass = Role.class)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @JsonView({Serialize.UserWithPosts.class})
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    @Fetch(FetchMode.SUBSELECT)
    @ToString.Exclude
    private List<Post> postList;

    public User() {
        this.active = true;
        this.uuid_link = UUID.randomUUID().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;

        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return 562048007;
    }
}
