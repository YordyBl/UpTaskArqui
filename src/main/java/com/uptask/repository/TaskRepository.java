package com.uptask.repository;

import com.uptask.model.Task;
import com.uptask.model.TaskPriority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


public interface TaskRepository extends JpaRepository<Task, Long> {
    // Consulta derivada para encontrar tareas en un rango de fechas
    List<Task> findByDueDateBetween(LocalDate startDate, LocalDate endDate);

    // Consulta JPQL para encontrar tareas en un rango de fechas
    @Query("SELECT t FROM Task t WHERE t.dueDate BETWEEN :startDate AND :endDate")
    List<Task> findTasksWithinDateRange(LocalDate startDate, LocalDate endDate);

    // Consulta SQL nativa para encontrar tareas en un rango de fechas
    @Query(value = "SELECT * FROM tasks WHERE due_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Task> findTasksWithinDateRangeNative(LocalDate startDate, LocalDate endDate);

    // Consulta para encontrar tareas por prioridad
    List<Task> findByPriority(TaskPriority priority);
    //Modificar un campo de alguna entidad
    @Modifying
    @Transactional
    @Query("UPDATE Task t SET t.completed = true WHERE t.id = :id")
    void markTaskAsCompleted(Long id);

    //Uso de procedures
    @Query(value = "CALL GetTaskReport();", nativeQuery = true)
    List<Object[]> getTaskReport();


    List<Task> findByProjectId(Long projectId);

    @Query("SELECT t FROM Task t JOIN t.project p WHERE p.name LIKE %:projectName%")
    List<Task> findTasksByProjectName(String projectName);



}
