package com.example.HireHub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruiterProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String companyName;

    private String companyWebsite;
    private String companyDescription;
    private String location;
    private int since;

    // One recruiter = one profile
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private Users user;
}

