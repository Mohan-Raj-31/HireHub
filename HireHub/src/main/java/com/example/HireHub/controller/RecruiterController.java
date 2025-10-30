package com.example.HireHub.controller;

import com.example.HireHub.model.*;
import com.example.HireHub.service.RecruiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recruiter")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500","http://localhost:63342"})
public class RecruiterController {

    @Autowired
    private RecruiterService recruiterService;

    // Post Job
    @PostMapping("/jobs")
    public ResponseEntity<Job> postJob(@RequestBody Job job, @AuthenticationPrincipal UserPrincipal principal){
        return ResponseEntity.ok(recruiterService.postJob(job,principal.getId()));
    }

    // Update Job
    @PutMapping("/jobs/{jobId}")
    public ResponseEntity<Job> updateJob(@PathVariable int jobId,
                                         @RequestBody Job job,
                                         @AuthenticationPrincipal UserPrincipal principal){
        return ResponseEntity.ok(recruiterService.updateJob(jobId,job,principal.getId()));
    }

    // Delete Job
    @DeleteMapping("/jobs/{jobId}")
    public ResponseEntity<String> deleteJob(@PathVariable int jobId,
                                            @AuthenticationPrincipal UserPrincipal principal){
        recruiterService.deleteJob(jobId,principal.getId());
        return ResponseEntity.ok("Delete successfully");
    }

    // view/get posted jobs
    @GetMapping("/jobs")
    public ResponseEntity<List<Job>> getPostedJobs(@AuthenticationPrincipal UserPrincipal principal){
        return ResponseEntity.ok(recruiterService.getPostedJobs(principal.getId()));
    }

    // view applications for a job
    @GetMapping("/jobs/{jobId}/applicants")
    public ResponseEntity<List<Application>> getApplicationsForJob(@PathVariable int jobId,
                                                                   @AuthenticationPrincipal UserPrincipal principal){
        return ResponseEntity.ok(recruiterService.getApplicationsForJob(jobId,principal.getId()));
    }

    // Accept/Reject application
    @PostMapping("/applications/{applicationId}/status")
    public ResponseEntity<Application> updateApplicationStatus(@PathVariable int applicationId,
                                                               @RequestParam ApplicationStatus status,
                                                               @AuthenticationPrincipal UserPrincipal principal){
        return ResponseEntity.ok(recruiterService.updateApplicationStatus(applicationId,status,principal.getId()));
    }

    // Total number of job applications
    @GetMapping("application/total")
    public ResponseEntity<Integer> getTotalApplications(@AuthenticationPrincipal UserPrincipal principal){
        return ResponseEntity.ok(recruiterService.getTotalApplications(principal.getId()));
    }

    // Update recruiter profile
    @PutMapping("/update/profile")
    public ResponseEntity<RecruiterProfile> updateRecruiterProfile(@RequestBody RecruiterProfile recruiterProfile,
                                                                   @AuthenticationPrincipal UserPrincipal principal){
        return ResponseEntity.ok(recruiterService.updateRecruiterProfile(recruiterProfile,principal.getId()));
    }

    // create recruiter profile
    @PostMapping("/profile/create")
    public ResponseEntity<String> createProfile(@RequestBody RecruiterProfile recruiterProfile,
                                                @AuthenticationPrincipal UserPrincipal principal){
        return ResponseEntity.ok(recruiterService.createProfile(recruiterProfile,principal.getId()));
    }

    // find jobSeekerProfile
    @GetMapping("/profile/exists")
    public ResponseEntity<Boolean> profileExists(@AuthenticationPrincipal UserPrincipal principal){
        return ResponseEntity.ok(recruiterService.profileExists(principal.getId()));
    }

    // get resume view
    @GetMapping("/profile/resume/{id}")
    public ResponseEntity<byte[]> downloadResume(@PathVariable int id) {
        return recruiterService.getResumeById(id);
    }

}

