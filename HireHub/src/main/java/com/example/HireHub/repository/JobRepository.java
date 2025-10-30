package com.example.HireHub.repository;

import com.example.HireHub.model.ApplicationStatus;
import com.example.HireHub.model.Job;
import com.example.HireHub.model.RecruiterProfile;
import com.example.HireHub.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job,Integer> {

    Optional<Job> findById(int jobId);
    List<Job> findByRecruiterId(int recruiterId);

    List<Job> findByTitleContainingIgnoreCase(String title);

    List<Job> findByLocationContainingIgnoreCase(String location);

    List<Job> findBySkillsRequiredContainingIgnoreCase(String skill);

    List<Job> findByExperienceLessThanEqual(int experience);

}
