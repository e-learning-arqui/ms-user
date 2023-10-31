package com.discovery.msuser.bl;

import com.discovery.msuser.dao.CardRepository;
import com.discovery.msuser.dao.UserRepository;
import com.discovery.msuser.dto.CardDto;
import com.discovery.msuser.entity.Card;
import com.discovery.msuser.entity.Student;
import com.discovery.msuser.exception.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardBl {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(UserBl.class);

    public void addUserCard( CardDto cardDto) throws UserException {
        logger.info("Starting to add card to user with id: {}", cardDto.getUserKeycloakId());
        Student student = userRepository.findByUserKeycloakId(cardDto.getUserKeycloakId());
        Card card = new Card();
        card.setUserId(student);
        card.setBankName(cardDto.getBankName());
        card.setNumber(cardDto.getNumber());
        card.setCvv(cardDto.getCvv());
        card.setExpiration(cardDto.getExpiration());
        card.setTitular(cardDto.getTitular());
        cardRepository.saveAndFlush(card);
    }
    public CardDto getUserCardByKeycloakId(String userKeycloakId) throws UserException {
        logger.info("Starting to get card to user with id: {}", userKeycloakId);
        Card card = cardRepository.findCardByKeycloakId(userKeycloakId);
        CardDto cardDto = new CardDto();
        cardDto.setBankName(card.getBankName());
        cardDto.setNumber(card.getNumber());
        cardDto.setCvv(card.getCvv());
        cardDto.setExpiration(card.getExpiration());
        cardDto.setTitular(card.getTitular());
        return cardDto;
    }



    public List<CardDto> getUserCards(String userId) throws UserException {
        //logger.info("Starting to get cards to user with id: {}", userId);
        Student student = userRepository.findByUserKeycloakId(userId);
        List<Card> cards = cardRepository.findAllCardsByUserId(student.getUserId());

        List<CardDto> ListcardDto = new ArrayList<>();
        // convert all list of cards to list of cardsDto
        for(Card card: cards){
            CardDto cardDto = new CardDto();
            cardDto.setBankName(card.getBankName());
            cardDto.setNumber(card.getNumber());
            cardDto.setCvv(card.getCvv());
            cardDto.setExpiration(card.getExpiration());
            cardDto.setTitular(card.getTitular());
            ListcardDto.add(cardDto);
        }
        return ListcardDto;

    }

}
