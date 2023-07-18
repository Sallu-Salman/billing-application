package com.sallu.BillingApplication.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private int enabled;

    @Column(name = "userid")
    private String userid;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Authority authority;

    public User(String username, String password, int enabled, Authority authority, String userId) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.authority = authority;
        this.userid = userId;
    }

    public User() {
    }
}
