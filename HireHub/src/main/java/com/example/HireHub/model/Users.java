package com.example.HireHub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(nullable=false,unique = true)
    private String username;

    @Column(nullable=false)
    private String password;

    @Column(nullable=false,unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Role role;

    // check for me

}
