package com.uptask.api;

import com.uptask.dto.ProjectRequest;
import com.uptask.dto.ProjectResponse;
import com.uptask.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@RequestBody ProjectRequest projectRequest) {
        ProjectResponse projectResponse = projectService.createProject(projectRequest);
        return new ResponseEntity<>(projectResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long id, @RequestBody ProjectRequest projectRequest) {
        ProjectResponse projectResponse = projectService.updateProject(id, projectRequest);
        return new ResponseEntity<>(projectResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long id) {
        ProjectResponse projectResponse = projectService.getProjectById(id);
        return new ResponseEntity<>(projectResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {
        List<ProjectResponse> projects = projectService.getAllProjects();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProjectResponse>>
    searchProjectsByNameContaining(@RequestParam String name) {
        List<ProjectResponse> projects = projectService.findProjectsByNameContaining(name);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

}
