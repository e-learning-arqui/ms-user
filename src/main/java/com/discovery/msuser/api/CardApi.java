package com.discovery.msuser.api;

import com.discovery.msuser.bl.CardBl;
import com.discovery.msuser.bl.UserBl;
import com.discovery.msuser.dto.CardDto;
import com.discovery.msuser.dto.ResponseDto;
import com.discovery.msuser.dto.UserDto;
import com.discovery.msuser.exception.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class CardApi {
    @Autowired
    private CardBl cardBl;
    private Logger logger = LoggerFactory.getLogger(UserApi.class);



    //agregar card
    @PostMapping("/card")
    public ResponseEntity<ResponseDto<String>> addUserCard(@RequestBody CardDto cardDto) throws UserException {
        cardBl.addUserCard(cardDto);
        return ResponseEntity.ok(new ResponseDto<>(null, "0000", "Card added successfully"));
    }

    //obtener todas las cards por id
    @GetMapping("/{id}/card")
    public ResponseEntity<ResponseDto<List<CardDto>>> getUserCard(@PathVariable("id") String userId) throws UserException {
        logger.info("Starting to get cards to user with id: {}", userId);
        return ResponseEntity.ok(new ResponseDto<>( "", "0000", cardBl.getUserCards(userId)));
    }


    // borrado de card por status
    /*@PutMapping("/card/{cardId}")
    public ResponseEntity<ResponseDto<String>> deleteUserCard(@PathVariable("cardId") Integer cardId) throws UserException {
        logger.info("Starting to delete card with id: {}", cardId);
        cardBl.deleteUserCard(cardId);
        return ResponseEntity.ok(new ResponseDto<>(null, "0000", "Card deleted successfully"));
    }*/

    // borrado
    @DeleteMapping("/card/{cardId}")
    public ResponseEntity<ResponseDto<String>> deleteUserCard(@PathVariable("cardId") Integer cardId) throws UserException {
        logger.info("Starting to delete card with id: {}", cardId);
        cardBl.deleteUserCard(cardId);
        return ResponseEntity.ok(new ResponseDto<>(null, "0000", "Card deleted successfully"));
    }



}
