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

@RestController
@RequestMapping("/api/v1/users")
public class CardApi {
    @Autowired
    private CardBl cardBl;
    private Logger logger = LoggerFactory.getLogger(UserApi.class);


    @PostMapping("/{id}/card")
    public ResponseEntity<ResponseDto<String>> addUserCard(@PathVariable("id") Long userId, @RequestBody CardDto cardDto) throws UserException {
        logger.info("Starting to add card to user with id: {}", userId);
        cardBl.addUserCard(userId, cardDto);
        return ResponseEntity.ok(new ResponseDto<>(null, "0000", "Card added successfully"));
    }



}
