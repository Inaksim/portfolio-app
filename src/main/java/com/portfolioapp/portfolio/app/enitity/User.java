package com.portfolioapp.portfolio.app.enitity;

import com.portfolioapp.portfolio.app.security.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false)
    private String firstName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String avatar;
    private String bio;

    @ElementCollection
    @CollectionTable(name = "user_social_links", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "social_network")
    @Column(name = "url")
    private Map<String, String> socialLinks;

    @Enumerated(EnumType.STRING)
    private Role role;
    private String verificationCode;
    private boolean banned;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Project> projects;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Like> likes;

    @OneToMany(mappedBy = "followed", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Follower> followers;

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Follower> following;


}
