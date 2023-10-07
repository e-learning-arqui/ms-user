package com.discovery.msuser.dao;

import com.discovery.msuser.entitiy.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Student, Long> {
    Student findByUsername(String username);
}
