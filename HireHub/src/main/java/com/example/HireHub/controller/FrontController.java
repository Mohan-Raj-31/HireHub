package com.example.HireHub.controller;

import com.example.HireHub.model.*;
import com.example.HireHub.service.FrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/front")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500","http://localhost:63342"})
public class FrontController {

    @Autowired
    private FrontService frontService;

    // 1. View all jobs
    @GetMapping("/jobs")
    public ResponseEntity<List<Job>> getAllJobs(){
        return ResponseEntity.ok(frontService.getAllJobs());
    }

    // 2. Search jobs
    @GetMapping("/jobs/search/title")
    public ResponseEntity<List<Job>> searchJobsByTitle(@RequestParam String title){
        return ResponseEntity.ok(frontService.searchJobsByTitle(title));
    }

    @GetMapping("/jobs/search/location")
    public ResponseEntity<List<Job>> searchByLocation(@RequestParam String location) {
        return ResponseEntity.ok(frontService.searchJobsByLocation(location));
    }

    @GetMapping("/jobs/search/skill")
    public ResponseEntity<List<Job>> searchBySkill(@RequestParam String skill) {
        return ResponseEntity.ok(frontService.searchJobsBySkill(skill));
    }

    @GetMapping("/jobs/search/experience")
    public ResponseEntity<List<Job>> filterByExperience(@RequestParam int experience) {
        return ResponseEntity.ok(frontService.filterJobsByExperience(experience));
    }

    // 3. Job details
    @GetMapping("/jobs/{jobId}")
    public ResponseEntity<Job> getJobDetails(@PathVariable int jobId){
        return ResponseEntity.ok(frontService.getJobDetails(jobId));
    }

    // 4. View recruiter profile
    @GetMapping("/recruiter/{userId}")
    public ResponseEntity<RecruiterProfile> getRecruiterProfile(@PathVariable int userId){
        return ResponseEntity.ok(frontService.getRecruiterProfile(userId));
    }

    // update username password
    @PutMapping("/update/username/password")
    public ResponseEntity<String> updateUsernamePassword(@RequestBody Users user,
                                                         @AuthenticationPrincipal UserPrincipal principal){
        return ResponseEntity.ok(frontService.updateUsernamePassword(user,principal.getId()));
    }

}
