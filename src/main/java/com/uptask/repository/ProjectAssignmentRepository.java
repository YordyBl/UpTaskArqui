package com.uptask.repository;

import com.uptask.model.Project;
import com.uptask.model.ProjectAssignmentId;
import com.uptask.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.uptask.model.ProjectAssignment;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface ProjectAssignmentRepository extends JpaRepository<ProjectAssignment, Long> {

    // Consulta derivada para encontrar asignaciones de proyecto en un rango de fechas
    List<ProjectAssignment> findByAssignedDateBetween(LocalDate startDate, LocalDate endDate);

    // Consulta JPQL para encontrar asignaciones de proyecto en un rango de fechas
    @Query("SELECT pa FROM ProjectAssignment pa WHERE pa.assignedDate BETWEEN :startDate AND :endDate")
    List<ProjectAssignment> findAssignmentsWithinDateRange(LocalDate startDate, LocalDate endDate);

    // Consulta SQL nativa para encontrar asignaciones de proyecto en un rango de fechas
    @Query(value = "SELECT * FROM project_assignments WHERE assigned_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<ProjectAssignment> findAssignmentsWithinDateRangeNative(LocalDate startDate, LocalDate endDate);

    @Procedure(name = "insertProjectAssignment")
    void insertAssignmentV1(Long projectId, Long userId, LocalDate assignedDate);


    @Modifying
    @Transactional
    @Query("INSERT INTO ProjectAssignment (id, project, user, assignedDate) VALUES (:id, :project, :user, :assignedDate)")
    void insertAssignmentV2(@Param("id") ProjectAssignmentId id,
                          @Param("project") Project project,
                          @Param("user") User user,
                          @Param("assignedDate") LocalDate assignedDate);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO project_assignments (project_id, user_id, assigned_date) VALUES (:projectId, :userId, :assignedDate)", nativeQuery = true)
    void insertAssignmentV3(@Param("projectId") Long projectId,
                          @Param("userId") Long userId,
                          @Param("assignedDate") LocalDate assignedDate);



    @Query("SELECT COUNT(pa) > 0 FROM ProjectAssignment pa WHERE pa.id.projectId = :projectId AND pa.id.userId = :userId")
    boolean existsById_ProjectIdAndId_UserId(@Param("projectId") Long projectId, @Param("userId") Long userId);




}
