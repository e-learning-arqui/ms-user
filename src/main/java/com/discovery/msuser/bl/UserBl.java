package com.discovery.msuser.bl;

import com.discovery.msuser.dao.ProfessorRepository;
import com.discovery.msuser.dao.UserRepository;
import com.discovery.msuser.dto.KeycloakUserDto;
import com.discovery.msuser.dto.NotificationDto;
import com.discovery.msuser.dto.UserDto;
import com.discovery.msuser.entity.Professor;
import com.discovery.msuser.entity.Student;
import com.discovery.msuser.exception.UserException;
import com.discovery.msuser.producer.NotificationProducer;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Date;

import static org.hibernate.internal.util.collections.CollectionHelper.listOf;

@Service
public class UserBl {

    @Autowired
    private final Keycloak keycloak;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ProfessorRepository professorRepository;

    @Autowired
    private final NotificationProducer notificationProducer;


    @Value("${keycloak.credentials.realm}")
    private String realm;
    @Value("${keycloak.credentials.resource}")
    private String clientId;
    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;


    public UserBl(Keycloak keycloak,
                  UserRepository userRepository,
                  ProfessorRepository professorRepository,
                  NotificationProducer notificationProducer) {
        this.keycloak = keycloak;
        this.userRepository = userRepository;
        this.professorRepository = professorRepository;
        this.notificationProducer = notificationProducer;
    }

    Logger logger = LoggerFactory.getLogger(UserBl.class);
    public void createUser(UserDto userDto) throws UserException {
        if(userDto.getUsername() == null || userDto.getUsername().isEmpty()) {
            throw new UserException(HttpStatus.BAD_REQUEST.value(),"Username is required");
        }

        CredentialRepresentation passwordRepresentation = preparePasswordRepresentation(userDto.getPassword());
        UserRepresentation userRepresentation = prepareUserRepresentation(userDto, passwordRepresentation);
        Response response = keycloak
                .realm(realm)
                .users()
                .create(userRepresentation);
        logger.info("Response {}", response.getStatus());

        if(response.getStatus() != 201) {
            throw new UserException(HttpStatus.BAD_REQUEST.value(),"User already exists");
        }
        String keycloakUserId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
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

    public void createProfessor(UserDto userDto) throws UserException {
        if(userDto.getUsername() == null || userDto.getUsername().isEmpty()) {
            throw new UserException(HttpStatus.BAD_REQUEST.value(),"Username is required");
        }

        CredentialRepresentation passwordRepresentation = preparePasswordRepresentation(userDto.getPassword());
        UserRepresentation userRepresentation = prepareUserRepresentation(userDto, passwordRepresentation);
        Response response = keycloak.
                realm(realm)
                .users()
                .create(userRepresentation);
        logger.info("Response {}", response.getStatus());

        if(response.getStatus() != 201) {
            throw new UserException(HttpStatus.BAD_REQUEST.value(),"User already exists");
        }
        String keycloakUserId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
        GroupRepresentation professorGroup = keycloak.realm(realm).groups().groups("professor", 0, 1).get(0); // Asume que solo hay un grupo con el nombre "professor"
        keycloak.realm(realm).users().get(keycloakUserId).joinGroup(professorGroup.getId());
        logger.info("Starting to add professor to database");
        Professor professor = new Professor();
        professor.setUsername(userDto.getUsername());
        professor.setEmail(userDto.getEmail());
        professor.setStatus(true);
        professor.setKeycloakId(keycloakUserId);
        professor.setTxUser("admin");
        professor.setTxHost("localhost");
        Date date = new Date();
        professor.setTxDate(date);
        professorRepository.save(professor);
        logger.info("Professor added to database with keycloak id {}", keycloakUserId);

    }

    public Professor getProfessorById(Long id) throws UserException {
        Professor professor = professorRepository.findById(id).orElseThrow(() -> new UserException(HttpStatus.BAD_REQUEST.value(),"Professor not found"));
        return professor;
    }

    public KeycloakUserDto getUserByKeycloakId(String keycloakId) throws UserException {
        KeycloakUserDto keycloakUserDto = new KeycloakUserDto();
        UserRepresentation userRepresentation = keycloak.realm(realm).users().get(keycloakId).toRepresentation();
        if(userRepresentation == null) {
            throw new UserException(HttpStatus.BAD_REQUEST.value(),"User not found");
        }
        return convertToKeycloakUserDto(userRepresentation);
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
            UserDto userDto,
            CredentialRepresentation credentialsRepresentation
    ) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(userDto.getUsername());
        userRepresentation.setEmail(userDto.getEmail());
        userRepresentation.setFirstName(userDto.getFirstName());
        userRepresentation.setLastName(userDto.getLastName());
        userRepresentation.setEnabled(true);
        userRepresentation.setCredentials(listOf(credentialsRepresentation));
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

    public String sendNotification(String message, String routingKey) {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setMessage(message);
        notificationDto.setDate(new Date());
        return notificationProducer.sendNotification(notificationDto, routingKey);
    }

}
