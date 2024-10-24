package com.uptask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.uptask.model.Project;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    // Consulta derivada para buscar proyectos por nombre
    List<Project> findByNameContaining(String name);

    // Consulta JPQL para buscar proyectos por nombre
    @Query("SELECT p FROM Project p WHERE p.name LIKE %:name%")
    List<Project> searchByName(String name);

    // Consulta SQL nativa para buscar proyectos por nombre
    @Query(value = "SELECT * FROM projects WHERE name LIKE %:name%", nativeQuery = true)
    List<Project> searchByNameNative(String name);
}
