package com.uptask.service;

import com.uptask.dto.TaskReport;
import com.uptask.dto.TaskRequest;
import com.uptask.dto.TaskResponse;
import com.uptask.model.TaskPriority;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {
    TaskResponse createTask(TaskRequest request);
    TaskResponse updateTask(Long id, TaskRequest request);
    void deleteTask(Long id);
    TaskResponse getTaskById(Long id);
    List<TaskResponse> getAllTasks();

    List<TaskResponse> findTasksByDateRange(LocalDate startDate, LocalDate endDate);
    List<TaskResponse> findTasksByPriority(TaskPriority priority);

    void markTaskAsCompleted(Long id);

    List<TaskReport> getTaskReport();

    List<TaskResponse> findTasksByProjectId(Long projectId);
    List<TaskResponse> findTasksByProjectName(String projectName);

}
