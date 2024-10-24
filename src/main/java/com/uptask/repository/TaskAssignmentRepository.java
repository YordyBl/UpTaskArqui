package com.uptask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.uptask.model.TaskAssignment;
import java.time.LocalDate;
import java.util.List;

public interface TaskAssignmentRepository extends JpaRepository<TaskAssignment, Long> {

    // Consulta derivada para encontrar asignaciones de tareas en un rango de fechas
    List<TaskAssignment> findByAssignedDateBetween(LocalDate startDate, LocalDate endDate);

    // Consulta JPQL para encontrar asignaciones de tareas en un rango de fechas
    @Query("SELECT ta FROM TaskAssignment ta WHERE ta.assignedDate BETWEEN :startDate AND :endDate")
    List<TaskAssignment> findTaskAssignmentsWithinDateRange(LocalDate startDate, LocalDate endDate);

    // Consulta SQL nativa para encontrar asignaciones de tareas en un rango de fechas
    @Query(value = "SELECT * FROM task_assignments WHERE assigned_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<TaskAssignment> findTaskAssignmentsWithinDateRangeNative(LocalDate startDate, LocalDate endDate);
}
