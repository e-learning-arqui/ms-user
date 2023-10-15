package com.discovery.msuser.dao;

import com.discovery.msuser.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<Student, Long> {
    Student findByUsername(String username);

    @Query("SELECT u FROM Student u WHERE u.keycloakId = :userKeycloakId")
    Student findByUserKeycloakId(String userKeycloakId);
}
