package com.portfolioapp.portfolio.app.enitity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="like_table")
@AllArgsConstructor
@NoArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Like(User user, Project project) {
        this.user = user;
        this.project = project;
    }


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
