package com.example.HireHub.repository;

import com.example.HireHub.model.RecruiterProfile;
import com.example.HireHub.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecruiterProfileRepository extends JpaRepository<RecruiterProfile,Integer> {

    Optional<RecruiterProfile> findByUserId(int userId);

    boolean existsByUserId(int userId);
}
