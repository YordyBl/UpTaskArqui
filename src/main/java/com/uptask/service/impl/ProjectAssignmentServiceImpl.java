package com.uptask.service.impl;

import com.uptask.dto.ProjectAssignmentRequest;
import com.uptask.dto.ProjectAssignmentResponse;
import com.uptask.exception.AssignmentAlreadyExistsException;
import com.uptask.exception.ResourceNotFoundException;
import com.uptask.mapper.ProjectAssignmentMapper;
import com.uptask.model.Project;
import com.uptask.model.ProjectAssignment;
import com.uptask.model.User;
import com.uptask.repository.ProjectAssignmentRepository;
import com.uptask.repository.ProjectRepository;
import com.uptask.repository.UserRepository;
import com.uptask.service.ProjectAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectAssignmentServiceImpl implements ProjectAssignmentService {

    private final ProjectAssignmentRepository projectAssignmentRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectAssignmentMapper projectAssignmentMapper;

    @Autowired
    public ProjectAssignmentServiceImpl(ProjectAssignmentRepository projectAssignmentRepository,
                                        ProjectRepository projectRepository,
                                        UserRepository userRepository,
                                        ProjectAssignmentMapper projectAssignmentMapper) {
        this.projectAssignmentRepository = projectAssignmentRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.projectAssignmentMapper = projectAssignmentMapper;
    }


    @Override
    public ProjectAssignmentResponse createAssignment(ProjectAssignmentRequest request) {



        if (projectAssignmentRepository.existsById_ProjectIdAndId_UserId(request.getProjectId(), request.getUserId())) {
            throw new AssignmentAlreadyExistsException("Assignment already exists for project " + request.getProjectId() + " and user " + request.getUserId());
        }

        Project project = projectRepository.findById(request.getProjectId())
                //.orElseThrow(() -> new RuntimeException("Project not found"));
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + request.getProjectId()));
        User user = userRepository.findById(request.getUserId())
                //.orElseThrow(() -> new RuntimeException("User not found"));
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));


        ProjectAssignment assignment = projectAssignmentMapper.projectAssignmentRequestToProjectAssignment(request, project, user);
        //projectAssignmentRepository.insertAssignmentV3(project.getId(), user.getId(), assignment.getAssignedDate());
        projectAssignmentRepository.insertAssignmentV1(project.getId(), user.getId(), assignment.getAssignedDate());

        return projectAssignmentMapper.projectAssignmentToProjectAssignmentResponse(assignment);
    }



}
