package com.discovery.msuser.dao;

import com.discovery.msuser.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    @Query(" SELECT c FROM Card c WHERE c.userId.userId = :userId")
    List<Card> findAllCardsByUserId(Long userId);


    @Query(" SELECT c FROM Card c WHERE c.userId.keycloakId = :keycloakId")
    Card findCardByKeycloakId(@Param("keycloakId") String keycloakId);
}
