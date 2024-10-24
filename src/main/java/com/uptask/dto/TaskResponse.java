package com.uptask.dto;


import com.uptask.model.TaskPriority;
import lombok.*;

import java.time.LocalDate;

//TaskResponse sería para devolver información de las tareas.

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private boolean completed;
    private TaskPriority priority;
    private String projectName;
}