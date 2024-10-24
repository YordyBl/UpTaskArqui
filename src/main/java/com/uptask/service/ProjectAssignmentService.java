package com.uptask.service;

import com.uptask.dto.ProjectAssignmentRequest;
import com.uptask.dto.ProjectAssignmentResponse;

public interface ProjectAssignmentService {
    ProjectAssignmentResponse createAssignment(ProjectAssignmentRequest request);

}
