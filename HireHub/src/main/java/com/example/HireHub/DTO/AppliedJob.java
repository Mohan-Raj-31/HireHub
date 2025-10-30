package com.example.HireHub.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppliedJob {

    private int jobId;
    private String jobTitle;
    private String companyName;
    private String location;
    private LocalDateTime appliedAt;
    private String status;

}
