package com.example.HireHub.service;

import com.example.HireHub.DTO.AppliedJob;
import com.example.HireHub.model.*;
import com.example.HireHub.repository.ApplicationRepository;
import com.example.HireHub.repository.JobRepository;
import com.example.HireHub.repository.JobSeekerProfileRepository;
import com.example.HireHub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobSeekerService {

    @Autowired
    private JobSeekerProfileRepository jobSeekerProfileRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    // Apply for a job
    public Application applyForJob(int jobId, int userId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Job not found"));

        JobSeekerProfile jobSeekerProfile = jobSeekerProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"JobSeeker profile not found"));

        // Prevent duplicate applications
        if(applicationRepository.findByJobAndJobSeeker(job,jobSeekerProfile).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"you already applied for this job");
        }

        Application application = new Application();
        application.setJob(job);
        application.setAppliedAt(LocalDateTime.now());
        application.setStatus(ApplicationStatus.APPLIED);
        application.setJobSeeker(jobSeekerProfile);

        return applicationRepository.save(application);
    }

    // View applied jobs
    public List<Application> getAppliedJob(int userId) {
        JobSeekerProfile jobSeekerProfile = jobSeekerProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Job seeker profile not found"));

        return applicationRepository.findByJobSeeker(jobSeekerProfile);
    }

    // create jobseeker profile
    public String createProfile(String fullName, String email, String skills, int experience, String education,
                                String college, String previousOrganization, MultipartFile file, int userId) {
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"user not found"));

        JobSeekerProfile profile = new JobSeekerProfile();

        profile.setFullName(fullName);
        profile.setExperience(experience);
        try {
            profile.setResume(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        profile.setSkills(skills);
        profile.setEducation(education);
        profile.setEmail(email);
        profile.setCollege(college);
        profile.setPreviousOrganization(previousOrganization);
        profile.setUser(users);

        jobSeekerProfileRepository.save(profile);
        return "Created Successfully";
    }

    // Update JobSeeker Profile
    public JobSeekerProfile updateProfile(String fullName, String email, String skills, int experience, String education,
                                          String college, String previousOrganization, MultipartFile file, int userId) throws IOException {
        JobSeekerProfile profile = jobSeekerProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Job seeker profile not found"));

        profile.setFullName(fullName);
        profile.setExperience(experience);
        profile.setSkills(skills);
        profile.setEducation(education);
        profile.setEmail(email);
        profile.setCollege(college);
        profile.setPreviousOrganization(previousOrganization);

        // ✅ Update resume only if a new file is uploaded
        if (file != null && !file.isEmpty()) {
            profile.setResume(file.getBytes());
        }

        return jobSeekerProfileRepository.save(profile);
    }

    // 5. Job Seeker profile
    public JobSeekerProfile getJobSeekerProfile(int userId) {
        return jobSeekerProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Job Seeker not found with id: " + userId));
    }

    // get list applied jobs by jobseeker
    public List<AppliedJob> getAppliedJobs(int userId) {
        JobSeekerProfile seekerProfile = jobSeekerProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND , "Job seeker profile not found"));

        List<Application> applications = applicationRepository.findByJobSeeker(seekerProfile);

        return applications.stream()
                .map(app -> new AppliedJob(
                        app.getJob().getId(),
                        app.getJob().getTitle(),
                        app.getJob().getRecruiter().getCompanyName(),
                        app.getJob().getLocation(),
                        app.getAppliedAt(),
                        app.getStatus().name()
                )).collect(Collectors.toList());
    }

    // find jobSeekerProfile
    public boolean profileExists(int userId) {

        return jobSeekerProfileRepository.existsByUserId(userId);

    }

    // get resume
    public ResponseEntity<byte[]> getResumeById(int id) {
        JobSeekerProfile profile = jobSeekerProfileRepository.findByUserId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"));

        byte[] resumeData = profile.getResume();

        if (resumeData == null || resumeData.length == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No resume uploaded");
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,  "inline; filename=\"resume.pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resumeData);
    }

}
