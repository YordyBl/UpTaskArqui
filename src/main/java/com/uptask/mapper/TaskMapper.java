package com.uptask.mapper;

import com.uptask.dto.TaskRequest;
import com.uptask.dto.TaskResponse;
import com.uptask.model.Task;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskMapper {

    private final ModelMapper modelMapper;

    public TaskMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureModelMapper();
    }

    private void configureModelMapper() {
        modelMapper.typeMap(TaskRequest.class, Task.class).addMappings(mapper -> {
            mapper.map(TaskRequest::getTitle, Task::setTitle);
            mapper.map(TaskRequest::getDescription, Task::setDescription);
            mapper.map(TaskRequest::getDueDate, Task::setDueDate);
            mapper.map(TaskRequest::getPriority, Task::setPriority);
        });

        modelMapper.typeMap(Task.class, TaskResponse.class).addMappings(mapper -> {
            mapper.map(Task::getId, TaskResponse::setId);
            mapper.map(Task::getTitle, TaskResponse::setTitle);
            mapper.map(Task::getDescription, TaskResponse::setDescription);
            mapper.map(Task::getDueDate, TaskResponse::setDueDate);
            mapper.map(Task::isCompleted, TaskResponse::setCompleted);
            mapper.map(Task::getPriority, TaskResponse::setPriority);
            mapper.map(src -> src.getProject().getName(), TaskResponse::setProjectName);
        });
    }

    public Task taskRequestToTask(TaskRequest request) {

        return modelMapper.map(request, Task.class);
    }

    public TaskResponse taskToTaskResponse(Task task) {

        return modelMapper.map(task, TaskResponse.class);
    }

    public List<Task> taskRequestListToTaskList(List<TaskRequest> requests) {
        return requests.stream()
                .map(this::taskRequestToTask)
                .collect(Collectors.toList());
    }

    public List<TaskResponse> taskListToTaskResponseList(List<Task> tasks) {
        return tasks.stream()
                .map(this::taskToTaskResponse)
                .collect(Collectors.toList());
    }
}
