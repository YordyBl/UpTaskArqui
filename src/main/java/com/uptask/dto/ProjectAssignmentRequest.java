package com.uptask.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectAssignmentRequest {
    private Long projectId;
    private Long userId;
    private LocalDate assignedDate;
}
