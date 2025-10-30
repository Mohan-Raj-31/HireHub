package com.example.HireHub.service;

import com.example.HireHub.model.*;
import com.example.HireHub.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class RecruiterService {

    @Autowired
    JobSeekerProfileRepository jobSeekerProfileRepository;

    @Autowired
    private RecruiterProfileRepository recruiterProfileRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationRepository applicationRepository;


    // post job
    public Job postJob(Job job, int userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        RecruiterProfile recruiterProfile = recruiterProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"RecruiterProfile not found"));

        job.setRecruiter(recruiterProfile);
        job.setPostedBy(user);
        job.setPostedAt(LocalDateTime.now());

        return jobRepository.save(job);
    }

    // update job
    public Job updateJob(int jobId, Job updatedJob, int userId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Job not Found"));

        if(job.getPostedBy().getId() != userId){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"you can only update your own jobs");
        }

        job.setTitle(updatedJob.getTitle());
        job.setDescription(updatedJob.getDescription());
        job.setLocation(updatedJob.getLocation());
        job.setSalary(updatedJob.getSalary());
        job.setSkillsRequired(updatedJob.getSkillsRequired());
        job.setExperience(updatedJob.getExperience());

        return jobRepository.save(job);
    }

    // delete job
    public void deleteJob(int jobId, int userId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Job not found"));

        if(job.getPostedBy().getId() != userId){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"you can only delete your own jobs");
        }

        jobRepository.delete(job);
    }

    // view/get posted jobs
    public List<Job> getPostedJobs(int userId) {
        RecruiterProfile recruiter = recruiterProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recruiter profile not found"));

        return jobRepository.findByRecruiterId(recruiter.getId());
    }

    // View Applications for a Job
    public List<Application> getApplicationsForJob(int jobId, int userId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Job Not found"));

        if(job.getPostedBy().getId() != userId){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"You can only view applicants for your jobs");
        }

        return applicationRepository.findByJob(job);
    }


    // Accept/Reject application
    public Application updateApplicationStatus(int applicationId, ApplicationStatus status, int userId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Application not found"));

        if(application.getJob().getPostedBy().getId() != userId){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"You can only update applications for your jobs");
        }
        
        application.setStatus(status);
        return applicationRepository.save(application);
    }

    // Total number of job applications (for all jobs of this recruiter)
    public Integer getTotalApplications(int userId) {
        return applicationRepository.findByJob_RecruiterId(userId).size();
    }

    // Update recruiter profile
    public RecruiterProfile updateRecruiterProfile(RecruiterProfile updatedProfile, int userId) {
        RecruiterProfile profile = recruiterProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Recruiter Profile not found"));

        profile.setLocation(updatedProfile.getLocation());
        profile.setCompanyName(updatedProfile.getCompanyName());
        profile.setCompanyDescription(updatedProfile.getCompanyDescription());
        profile.setCompanyWebsite(updatedProfile.getCompanyWebsite());
        profile.setSince(updatedProfile.getSince());

        return recruiterProfileRepository.save(profile);
    }

    // create jobseeker profile
    public String createProfile(RecruiterProfile recruiterProfile, int userId) {
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"user not found"));

        recruiterProfile.setUser(users);
        recruiterProfileRepository.save(recruiterProfile);
        return "Created Successfully";
    }

    // find jobSeekerProfile
    public boolean profileExists(int userId) {
        return recruiterProfileRepository.existsByUserId(userId);
    }

    // view resume
    public ResponseEntity<byte[]> getResumeById(int id) {
        JobSeekerProfile profile = jobSeekerProfileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"));

        if (profile.getResume() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No resume uploaded");
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,  "inline; filename=\"" + profile.getResume() +"\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(profile.getResume());
    }

}
