package com.discovery.msuser.dao;

import com.discovery.msuser.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    @Query(" SELECT c FROM Card c WHERE c.userId.userId = :userId")
    List<Card> findAllCardsByUserId(Long userId);

}
