package com.example.HireHub.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobSeekerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String fullName;

    @Lob
    @Column(name = "resume", columnDefinition = "LONGBLOB")
    private byte[] resume; // store resume as bytes instead of file path

    private String email;
    private String skills;
    private int experience; // years
    private String education;
    private String college;
    private String previousOrganization;

    // One job seeker = one profile
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private Users user;
}

