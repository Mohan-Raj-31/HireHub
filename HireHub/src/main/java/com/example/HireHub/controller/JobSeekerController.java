package com.example.HireHub.controller;

import com.example.HireHub.DTO.AppliedJob;
import com.example.HireHub.model.Application;
import com.example.HireHub.model.JobSeekerProfile;
import com.example.HireHub.model.UserPrincipal;
import com.example.HireHub.service.JobSeekerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/jobseeker")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500","http://localhost:63342"})
public class JobSeekerController {

    @Autowired
    private JobSeekerService jobSeekerService;

    // Apply for a Job
    @PostMapping("/apply/{jobId}")
    public ResponseEntity<Application> applyForJob(@PathVariable int jobId,
                                                   @AuthenticationPrincipal UserPrincipal principal){
        return ResponseEntity.ok(jobSeekerService.applyForJob(jobId,principal.getId()));
    }

    // View all applied jobs
    @GetMapping("/applications")
    public ResponseEntity<List<Application>> getAppliedJob(@AuthenticationPrincipal UserPrincipal principal){
        return ResponseEntity.ok(jobSeekerService.getAppliedJob(principal.getId()));
    }

    // create job Seeker profile
    @PostMapping("/profile/create")
    public ResponseEntity<String> createProfile(@RequestParam String fullName,
                                                @RequestParam String email,
                                                @RequestParam String skills,
                                                @RequestParam int experience,
                                                @RequestParam String education,
                                                @RequestParam String college,
                                                @RequestParam String previousOrganization,
                                                @RequestParam MultipartFile resume,
                                                @AuthenticationPrincipal UserPrincipal principal){
        return ResponseEntity.ok(jobSeekerService.createProfile(fullName,email,skills,experience,education,
                college,previousOrganization,resume,principal.getId()));
    }

    // Update JobSeeker Profile
    @PutMapping("/update/profile")
    public ResponseEntity<JobSeekerProfile> updateProfile(@RequestParam String fullName,
                                                          @RequestParam String email,
                                                          @RequestParam String skills,
                                                          @RequestParam int experience,
                                                          @RequestParam String education,
                                                          @RequestParam String college,
                                                          @RequestParam String previousOrganization,
                                                          @RequestParam(value = "file", required = false) MultipartFile file,
                                                          @AuthenticationPrincipal UserPrincipal principal) throws IOException {

        return ResponseEntity.ok(jobSeekerService.updateProfile(fullName,email,skills,experience,education,
                college,previousOrganization,file,principal.getId()));
    }

    // 5. view Job Seeker profile
    @GetMapping("/profile")
    public ResponseEntity<JobSeekerProfile> getJobSeekerProfile(@AuthenticationPrincipal UserPrincipal principal){
        return ResponseEntity.ok(jobSeekerService.getJobSeekerProfile(principal.getId()));
    }

    // get list applied jobs by jobseeker
    @GetMapping("/applied/jobs")
    public ResponseEntity<List<AppliedJob>> getAppliedJobs(@AuthenticationPrincipal UserPrincipal principal){
        return ResponseEntity.ok(jobSeekerService.getAppliedJobs(principal.getId()));
    }

    // find jobSeekerProfile
    @GetMapping("/profile/exists")
    public ResponseEntity<Boolean> profileExists(@AuthenticationPrincipal UserPrincipal principal){
        return ResponseEntity.ok(jobSeekerService.profileExists(principal.getId()));
    }

    // get resume
    @GetMapping("/profile/resume")
    public ResponseEntity<byte[]> downloadResume(@AuthenticationPrincipal UserPrincipal principal) {
       return jobSeekerService.getResumeById(principal.getId());
    }
}
