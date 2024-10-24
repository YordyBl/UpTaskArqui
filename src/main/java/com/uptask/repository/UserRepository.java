package com.uptask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.uptask.model.User;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    // Consulta derivada para buscar usuarios por coincidencia de email
    List<User> findByEmailContaining(String email);

    // Consulta JPQL para buscar usuarios por coincidencia de email
    @Query("SELECT u FROM User u WHERE u.email LIKE %:email%")
    List<User> searchByEmail(String email);

    // Consulta SQL nativa para buscar usuarios por coincidencia de email
    @Query(value = "SELECT * FROM users WHERE email LIKE %:email%", nativeQuery = true)
    List<User> searchByEmailNative(String email);
}
