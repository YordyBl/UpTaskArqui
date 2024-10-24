package com.uptask.mapper;

import com.uptask.dto.ProjectRequest;
import com.uptask.dto.ProjectResponse;
import com.uptask.model.Project;
import com.uptask.model.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProjectMapper {

    private final ModelMapper modelMapper;

    public ProjectMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureModelMapper();
    }

    PropertyMap<Project, ProjectResponse> projectToResponseMap = new PropertyMap<>() {
        @Override
        protected void configure() {
            map().setId(source.getId());
            map().setName(source.getName());
            using(ctx -> {
                Project sourceProject = (Project) ctx.getSource();
                return Optional.ofNullable(sourceProject.getUser())
                        .map(User::getUserProfile)
                        .map(userProfile -> userProfile.getFirstName() + " " + userProfile.getLastName())
                        .orElse("");
            }).map(source, destination.getUserName());
        }
    };


    private void configureModelMapper() {
        modelMapper.addMappings(projectToResponseMap);
    }

    public Project projectRequestToProject(ProjectRequest request) {
        return modelMapper.map(request, Project.class);
    }

    public ProjectResponse projectToProjectResponse(Project project) {
        return modelMapper.map(project, ProjectResponse.class);
    }

    public List<ProjectResponse> projectsToProjectResponses(List<Project> projects) {
        return projects.stream()
                .map(this::projectToProjectResponse)
                .collect(Collectors.toList());
    }
}
