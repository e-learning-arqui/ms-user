package com.discovery.msuser.dao;

import com.discovery.msuser.entitiy.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long>{
}
