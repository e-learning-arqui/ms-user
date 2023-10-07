package com.discovery.msuser.bl;

import com.discovery.msuser.dto.UserDto;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;

@Service
public class UserBl {

    @Autowired
    private final Keycloak keycloak;
    @Value("${keycloak.credentials.realm}")
    private String realm;
    @Value("${keycloak.credentials.resource}")
    private String clientId;
    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;


    public UserBl(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    Logger logger = LoggerFactory.getLogger(UserBl.class);
    public void createUser(UserDto userDto){
        CredentialRepresentation passwordRepresentation = preparePasswordRepresentation(userDto.getPassword());
        UserRepresentation userRepresentation = prepareUserRepresentation(userDto);
        Response response = keycloak.
                realm(realm)
                .users()
                .create(userRepresentation);
        logger.info("Response {}", response.getStatusInfo());

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
        return userRepresentation;
    }


}
