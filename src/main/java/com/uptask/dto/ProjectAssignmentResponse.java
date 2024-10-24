package com.uptask.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectAssignmentResponse {
    private Long projectId;
    private Long userId;
    private LocalDate assignedDate;
    private String projectName;
    private String userName;
}
