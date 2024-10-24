package com.uptask.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;


@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectAssignmentId implements Serializable {
    @Column(name = "project_id")
    private Long projectId;
    @Column(name = "user_id")
    private Long userId;
}
