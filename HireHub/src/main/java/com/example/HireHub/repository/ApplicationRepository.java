package com.example.HireHub.repository;

import com.example.HireHub.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,Integer> {

    List<Application> findByJob(Job job);
    Optional<Application> findById(int applicationId);
    List<Application> findByJob_RecruiterId(int recruiterProfileId);

    Optional<Application> findByJobAndJobSeeker(Job job, JobSeekerProfile jobSeeker);

    List<Application> findByJobSeeker(JobSeekerProfile jobSeeker);
}
