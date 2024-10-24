package com.uptask.service.impl;

import com.uptask.dto.TaskReport;
import com.uptask.dto.TaskRequest;
import com.uptask.dto.TaskResponse;
import com.uptask.exception.ResourceNotFoundException;
import com.uptask.mapper.TaskMapper;
import com.uptask.model.Project;
import com.uptask.model.Task;
import com.uptask.model.TaskPriority;
import com.uptask.repository.ProjectRepository;
import com.uptask.repository.TaskRepository;
import com.uptask.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository,
                           TaskMapper taskMapper){
        this.taskRepository = taskRepository;
        this.projectRepository=projectRepository;
        this.taskMapper=taskMapper;
    }

    @Override
    public TaskResponse createTask(TaskRequest request) {
        Task task = taskMapper.taskRequestToTask(request);

        Optional<Long> projectIdOptional = Optional.ofNullable(request.getProjectId());
        if (projectIdOptional.isPresent()) {
            Project project = projectRepository.findById(request.getProjectId())
                     //.orElseThrow(() -> new RuntimeException("Project not found"));
                    .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + request.getProjectId()));
            task.setProject(project);
        }

        task = taskRepository.save(task);
        return taskMapper.taskToTaskResponse(task);
    }


    @Override
    public TaskResponse updateTask(Long id, TaskRequest request) {
        Task task = taskRepository.findById(id)
                // .orElseThrow(() -> new RuntimeException("Task not found"));
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        Project project = projectRepository.findById(request.getProjectId())
                //.orElseThrow(() -> new RuntimeException("Project not found"));
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + request.getProjectId()));

        // Actualiza los campos de la tarea con los valores de request
        updateTaskFromRequest(task, request);
        task.setProject(project);
        task = taskRepository.save(task);
        return taskMapper.taskToTaskResponse(task);
    }


    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                //.orElseThrow(() -> new RuntimeException("Task not found"));
                 .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        return taskMapper.taskToTaskResponse(task);
    }

    @Override
    public List<TaskResponse> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(taskMapper::taskToTaskResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskResponse> findTasksByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Task> tasks = taskRepository.findByDueDateBetween(startDate, endDate);
        return tasks.stream()
                .map(taskMapper::taskToTaskResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskResponse> findTasksByPriority(TaskPriority priority) {
        List<Task> tasks = taskRepository.findByPriority(priority);
        return tasks.stream()
                .map(taskMapper::taskToTaskResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void markTaskAsCompleted(Long id) {
        if (!taskRepository.existsById(id)) {
            // throw new RuntimeException("Task not found");
            throw new ResourceNotFoundException("Task not found with id: " + id);
        }
        taskRepository.markTaskAsCompleted(id);
    }

    @Override
    public List<TaskReport> getTaskReport() {
        return taskRepository.getTaskReport().stream()
                .map(result -> new TaskReport(
                        (String) result[0], // ProjectName
                        ((Number) result[1]).intValue(), // CompletedTasks
                        ((Number) result[2]).intValue()  // IncompleteTasks
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskResponse> findTasksByProjectId(Long projectId) {
        List<Task> tasks = taskRepository.findByProjectId(projectId);
        return tasks.stream()
                .map(taskMapper::taskToTaskResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskResponse> findTasksByProjectName(String projectName) {
        List<Task> tasks = taskRepository.findTasksByProjectName(projectName);
        return tasks.stream()
                .map(taskMapper::taskToTaskResponse)
                .collect(Collectors.toList());
    }

    public void updateTaskFromRequest(Task task, TaskRequest request) {
        // Aquí asignas los valores de request a los campos correspondientes de task
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setPriority(request.getPriority());
    }

}
