package com.example.HireHub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private String location;

    private double salary;

    @Column(nullable = false)
    private String skillsRequired;

    private int experience;

    @Column(nullable = false)
    private LocalDateTime postedAt;

    @ManyToOne
    @JoinColumn(name = "posted_by", nullable = false)
    private Users postedBy;

    @ManyToOne
    @JoinColumn(name = "recruiter_id", nullable = false)
    private RecruiterProfile recruiter;
}
