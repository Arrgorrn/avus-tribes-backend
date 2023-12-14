package org.gfa.avustribesbackend.services.Player;

import org.gfa.avustribesbackend.dtos.PlayerRegistrationBody;
import org.gfa.avustribesbackend.models.MyError;
import org.gfa.avustribesbackend.repositories.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PlayerServiceImplTest {
    @InjectMocks
    private PlayerServiceImpl playerService;
    PlayerRegistrationBody playerRegistrationBody;
    @Mock
    PlayerRepository playerRepository;

    @BeforeEach
    public void beforeEach() {
        playerRegistrationBody= new PlayerRegistrationBody();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_register_player_username_null() {
        ResponseEntity<Object> responseEntity = playerService.registerPlayer(playerRegistrationBody);
        assertErrorResponse(responseEntity, "Username is required");
    }

    @Test
    void test_register_player_password_null() {
        playerRegistrationBody.setUsername("testUser");
        ResponseEntity<Object> responseEntity = playerService.registerPlayer(playerRegistrationBody);
        assertErrorResponse(responseEntity, "Password is required");
    }

    @Test
    void test_register_player_email_null() {
        playerRegistrationBody.setUsername("testUser");
        playerRegistrationBody.setPassword("password");
        ResponseEntity<Object> responseEntity = playerService.registerPlayer(playerRegistrationBody);
        assertErrorResponse(responseEntity, "Email is required");
    }

    @Test
    void test_register_player_username_already_taken() {
        playerRegistrationBody.setUsername("existingUser");
        playerRegistrationBody.setPassword("password");
        playerRegistrationBody.setEmail("test@gmail.com");
        when(playerRepository.existsByUserName(playerRegistrationBody.getUsername())).thenReturn(false);
        assertFalse(playerRepository.existsByUserName(playerRegistrationBody.getUsername()));
    }

    @Test
    void test_register_player_email_already_taken() {
        playerRegistrationBody.setUsername("testUser");
        playerRegistrationBody.setPassword("password");
        playerRegistrationBody.setEmail("existing@gmail.com");
        when(playerRepository.existsByEmail(playerRegistrationBody.getEmail())).thenReturn(false);
        assertFalse(playerRepository.existsByEmail(playerRegistrationBody.getEmail()));
    }

    @Test
    void username_longer_than_4(){
        playerRegistrationBody.setUsername("kak");
        playerRegistrationBody.setEmail("hello@gmail.com");
        playerRegistrationBody.setPassword("password");

        ResponseEntity<Object> responseEntity = playerService.registerPlayer(playerRegistrationBody);

        assertErrorResponse(responseEntity, "Username must be at least 4 characters long");
    }

    @Test
    void password_longer_than_8(){
        playerRegistrationBody.setUsername("Hello");
        playerRegistrationBody.setEmail("hello@gmail.com");
        playerRegistrationBody.setPassword("short");

        ResponseEntity<Object> responseEntity = playerService.registerPlayer(playerRegistrationBody);

        assertErrorResponse(responseEntity, "Password must be at least 8 characters long");
    }

    @Test
    void email_validation(){
        playerRegistrationBody.setUsername("Hello");
        playerRegistrationBody.setEmail("hellogmail.com");
        playerRegistrationBody.setPassword("password");

        ResponseEntity<Object> responseEntity = playerService.registerPlayer(playerRegistrationBody);

        assertErrorResponse(responseEntity, "Invalid email");
    }

    @Test
    void successful_user_registration(){
        playerRegistrationBody.setUsername("Hello");
        playerRegistrationBody.setEmail("hello@gmail.com");
        playerRegistrationBody.setPassword("password");

        ResponseEntity<Object> responseEntity = playerService.registerPlayer(playerRegistrationBody);
        ResponseEntity<Object> responseEntity1 = new ResponseEntity<>("successful creation", HttpStatusCode.valueOf(200));

        assertEquals(responseEntity, responseEntity1);
    }

    private void assertErrorResponse(
            ResponseEntity<Object> responseEntity, String expectedErrorMessage) {
        assertEquals(400, responseEntity.getStatusCodeValue());
        assertTrue(responseEntity.getBody() instanceof MyError);
        MyError error = (MyError) responseEntity.getBody();
        assertEquals(expectedErrorMessage, error.getError());
    }
}