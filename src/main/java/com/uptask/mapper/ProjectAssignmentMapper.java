package com.uptask.mapper;

import com.uptask.dto.ProjectAssignmentRequest;
import com.uptask.dto.ProjectAssignmentResponse;
import com.uptask.model.Project;
import com.uptask.model.ProjectAssignment;
import com.uptask.model.ProjectAssignmentId;
import com.uptask.model.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProjectAssignmentMapper {

    private final ModelMapper modelMapper;

    public ProjectAssignmentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureModelMapper();
    }

    private void configureModelMapper() {
        PropertyMap<ProjectAssignment, ProjectAssignmentResponse> projectAssignmentToResponseMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setProjectName(source.getProject().getName());
                using(ctx -> {
                    User user = ((ProjectAssignment) ctx.getSource()).getUser();
                    return Optional.ofNullable(user)
                            .map(User::getUserProfile)
                            .map(userProfile -> userProfile.getFirstName() + " " + userProfile.getLastName())
                            .orElse("");
                }).map(source, destination.getUserName());
            }
        };
        modelMapper.addMappings(projectAssignmentToResponseMap);
    }

    public ProjectAssignment projectAssignmentRequestToProjectAssignment(ProjectAssignmentRequest request, Project project, User user) {
        ProjectAssignment assignment = modelMapper.map(request, ProjectAssignment.class);
        assignment.setId(new ProjectAssignmentId(project.getId(), user.getId()));
        assignment.setProject(project);
        assignment.setUser(user);
        return assignment;
    }

    public ProjectAssignmentResponse projectAssignmentToProjectAssignmentResponse(ProjectAssignment assignment) {
        return modelMapper.map(assignment, ProjectAssignmentResponse.class);
    }
}
