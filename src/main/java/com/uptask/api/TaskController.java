package com.uptask.api;

import com.uptask.dto.TaskReport;
import com.uptask.dto.TaskRequest;
import com.uptask.dto.TaskResponse;
import com.uptask.model.TaskPriority;
import com.uptask.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest taskRequest) {
        TaskResponse taskResponse = taskService.createTask(taskRequest);
        return new ResponseEntity<>(taskResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id, @RequestBody TaskRequest taskRequest) {
        TaskResponse taskResponse = taskService.updateTask(id, taskRequest);
        return new ResponseEntity<>(taskResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        TaskResponse taskResponse = taskService.getTaskById(id);
        return new ResponseEntity<>(taskResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks(@RequestParam Optional<Long> projectId,
                                                          @RequestParam Optional<String> projectName) {
        List<TaskResponse> tasks;
        if (projectId.isPresent()) {
            tasks = taskService.findTasksByProjectId(projectId.get());
        } else if (projectName.isPresent()) {
            tasks = taskService.findTasksByProjectName(projectName.get());
        } else {
            tasks = taskService.getAllTasks();
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }


    @GetMapping("/byDateRange")
    public ResponseEntity<List<TaskResponse>> getTasksByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<TaskResponse> tasks = taskService.findTasksByDateRange(startDate, endDate);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/byPriority")
    public ResponseEntity<List<TaskResponse>> getTasksByPriority(@RequestParam TaskPriority priority) {
        List<TaskResponse> tasks = taskService.findTasksByPriority(priority);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }


    @PatchMapping("/{id}/markCompleted")
    public ResponseEntity<Void> markTaskAsCompleted(@PathVariable Long id) {
        taskService.markTaskAsCompleted(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/report/by-project")
    public ResponseEntity<List<TaskReport>> getTaskReportByProject() {
        List<TaskReport> report = taskService.getTaskReport();
        return ResponseEntity.ok(report);
    }
}
