package com.discovery.msuser.bl;

import com.discovery.msuser.dao.UserRepository;
import com.discovery.msuser.dto.KeycloakUserDto;
import com.discovery.msuser.dto.UserDto;
import com.discovery.msuser.entitiy.Student;
import com.discovery.msuser.exception.UserException;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Date;

@Service
public class UserBl {

    @Autowired
    private final Keycloak keycloak;
    @Autowired
    private final UserRepository userRepository;

    @Value("${keycloak.credentials.realm}")
    private String realm;
    @Value("${keycloak.credentials.resource}")
    private String clientId;
    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;


    public UserBl(Keycloak keycloak, UserRepository userRepository) {
        this.keycloak = keycloak;
        this.userRepository = userRepository;
    }

    Logger logger = LoggerFactory.getLogger(UserBl.class);
    public void createUser(UserDto userDto, String groupName) throws UserException {
        if(userDto.getUsername() == null || userDto.getUsername().isEmpty()) {
            throw new UserException(HttpStatus.BAD_REQUEST.value(),"Username is required");
        }

        CredentialRepresentation passwordRepresentation = preparePasswordRepresentation(userDto.getPassword());
        UserRepresentation userRepresentation = prepareUserRepresentation(userDto);
        Response response = keycloak.
                realm(realm)
                .users()
                .create(userRepresentation);
        logger.info("Response {}", response.getStatus());

        if(response.getStatus() != 201) {
            throw new UserException(HttpStatus.BAD_REQUEST.value(),"User already exists");
        }
        String keycloakUserId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
        if(groupName.equals("student")) {
            logger.info("Starting to add user to database");
            Student student = new Student();
            student.setUsername(userDto.getUsername());
            student.setEmail(userDto.getEmail());
            student.setStatus(true);
            student.setKeycloakId(keycloakUserId);
            student.setTxUser("admin");
            student.setTxHost("localhost");
            Date date = new Date();
            student.setTxDate(date);
            userRepository.save(student);
            logger.info("User added to database with keycloak id {}", keycloakUserId);
        }

    }

    public KeycloakUserDto getUserByKeycloakId(String keycloakId) throws UserException {
        KeycloakUserDto keycloakUserDto = new KeycloakUserDto();
        UserRepresentation userRepresentation = keycloak.realm(realm).users().get(keycloakId).toRepresentation();
        if(userRepresentation == null) {
            throw new UserException(HttpStatus.BAD_REQUEST.value(),"User not found");
        }

        return  keycloakUserDto = convertToKeycloakUserDto(userRepresentation);
    }






    private CredentialRepresentation preparePasswordRepresentation(
            String password
    ) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        return credentialRepresentation;
    }

    private UserRepresentation prepareUserRepresentation(
            UserDto userDto
    ) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(userDto.getUsername());
        userRepresentation.setEmail(userDto.getEmail());
        userRepresentation.setFirstName(userDto.getFirstName());
        userRepresentation.setLastName(userDto.getLastName());
        userRepresentation.setEnabled(true);
        return userRepresentation;
    }


    private KeycloakUserDto convertToKeycloakUserDto(UserRepresentation userRepresentation) {
        KeycloakUserDto keycloakUserDto = new KeycloakUserDto();
        keycloakUserDto.setId(userRepresentation.getId());
        keycloakUserDto.setUsername(userRepresentation.getUsername());
        keycloakUserDto.setEnabled(userRepresentation.isEnabled());
        keycloakUserDto.setEmailVerified(userRepresentation.isEmailVerified());
        keycloakUserDto.setFirstName(userRepresentation.getFirstName());
        keycloakUserDto.setLastName(userRepresentation.getLastName());
        keycloakUserDto.setEmail(userRepresentation.getEmail());
        return keycloakUserDto;
    }

}
