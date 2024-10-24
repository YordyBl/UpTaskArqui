package com.uptask.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskReport {
    private String projectName;
    private int completedTasks;
    private int incompleteTasks;
}
