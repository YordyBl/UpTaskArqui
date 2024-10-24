package com.uptask.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "project_assignments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectAssignment {

    @EmbeddedId
    private ProjectAssignmentId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("projectId")
    @JoinColumn(name = "project_id", nullable = false, foreignKey = @ForeignKey(name = "FK_ProjectAssignment_Project"))
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_ProjectAssignment_User"))
    private User user;

    @Column(name = "assigned_date", nullable = false)
    private LocalDate assignedDate;
}
