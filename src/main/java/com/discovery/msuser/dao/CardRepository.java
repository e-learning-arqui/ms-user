package com.discovery.msuser.dao;

import com.discovery.msuser.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> { }
