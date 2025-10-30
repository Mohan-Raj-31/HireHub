package com.example.HireHub.service;

import com.example.HireHub.model.Job;
import com.example.HireHub.model.JobSeekerProfile;
import com.example.HireHub.model.RecruiterProfile;
import com.example.HireHub.model.Users;
import com.example.HireHub.repository.JobRepository;
import com.example.HireHub.repository.JobSeekerProfileRepository;
import com.example.HireHub.repository.RecruiterProfileRepository;
import com.example.HireHub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FrontService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private RecruiterProfileRepository recruiterProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 1. View all jobs
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    // 2. Search jobs
    public List<Job> searchJobsByTitle(String title) {
        return jobRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Job> searchJobsByLocation(String location) {
        return jobRepository.findByLocationContainingIgnoreCase(location);
    }

    public List<Job> searchJobsBySkill(String skill) {
        return jobRepository.findBySkillsRequiredContainingIgnoreCase(skill);
    }

    public List<Job> filterJobsByExperience(int experience) {
        return jobRepository.findByExperienceLessThanEqual(experience);
    }

    // 3. Job details
    public Job getJobDetails(int jobId) {
        return jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job Not found with this id: "+jobId));
    }

    // 4. View recruiter profile
    public RecruiterProfile getRecruiterProfile(int userId) {
        return recruiterProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Recruiter not found with id: " + userId));
    }

    // update username password
    public String updateUsernamePassword(Users user, int userId) {
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User Not Found"));

        users.setUsername(user.getUsername());
        users.setPassword(passwordEncoder.encode(user.getPassword()));
        users.setEmail(user.getEmail());

        userRepository.save(users);
        return "updated successfully";
    }

}
