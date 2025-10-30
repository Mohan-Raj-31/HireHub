package com.example.HireHub.repository;

import com.example.HireHub.model.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobSeekerProfileRepository extends JpaRepository<JobSeekerProfile,Integer> {

    Optional<JobSeekerProfile> findByUserId(int userId);

    boolean existsByUserId(int userId);

}
