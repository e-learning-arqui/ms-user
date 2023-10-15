package com.discovery.msuser.bl;

import com.discovery.msuser.dao.CardRepository;
import com.discovery.msuser.dto.CardDto;
import com.discovery.msuser.entity.Card;
import com.discovery.msuser.entity.Student;
import com.discovery.msuser.exception.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardBl {
    @Autowired
    private CardRepository cardRepository;

    Logger logger = LoggerFactory.getLogger(UserBl.class);

    public void addUserCard(Long userId, CardDto cardDto) throws UserException {
        logger.info("Starting to add card to user with id: {}", userId);
        Card card = new Card();
        Student student = new Student();
        student.setUserId(userId);
        card.setUserId(student);
        card.setBankName(cardDto.getBankName());
        card.setNumber(cardDto.getNumber());
        card.setCvv(cardDto.getCvv());
        card.setExpiration(cardDto.getExpiration());
        card.setTitular(cardDto.getTitular());
        cardRepository.saveAndFlush(card);
    }

}
