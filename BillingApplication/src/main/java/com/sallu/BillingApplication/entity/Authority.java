package com.sallu.BillingApplication.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "authorities")
@Data
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "authority")
    private String authority;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username")
    private User user;

    public Authority(int id, String authority, User user) {
        this.id = id;
        this.authority = authority;
        this.user = user;
    }

    public Authority(String authority, User user) {
        this.authority = authority;
        this.user = user;
    }

    public Authority() {
    }
}
