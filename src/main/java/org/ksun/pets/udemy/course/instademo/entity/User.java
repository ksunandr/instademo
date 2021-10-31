package org.ksun.pets.udemy.course.instademo.entity;

import org.ksun.pets.udemy.course.instademo.entity.enums.ERole;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;
import java.util.*;

public class User {

    private Long id;
    private String name;
    private String username;
    private String lastname;
    private String email;
    private String bio;
    private String password;

    private Set<ERole> roles = new HashSet<>();
    private List<Post> posts = new ArrayList<>();
    private LocalDateTime createdDate;

    private Collection<? extends GrantedAuthority> authorities;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }

    public User() {
    }
}
