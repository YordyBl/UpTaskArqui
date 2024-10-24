package com.uptask.api;

import com.uptask.dto.ProjectAssignmentRequest;
import com.uptask.dto.ProjectAssignmentResponse;
import com.uptask.service.ProjectAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project-assignments")
@RequiredArgsConstructor
public class ProjectAssignmentController {

    private final ProjectAssignmentService projectAssignmentService;

    @PostMapping
    public ResponseEntity<ProjectAssignmentResponse> createAssignment(@RequestBody ProjectAssignmentRequest request) {
        ProjectAssignmentResponse response = projectAssignmentService.createAssignment(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
