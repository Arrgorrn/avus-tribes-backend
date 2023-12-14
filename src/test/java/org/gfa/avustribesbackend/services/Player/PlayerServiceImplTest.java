package org.gfa.avustribesbackend.services.Player;

import org.gfa.avustribesbackend.dtos.PlayerRegistrationBody;
import org.gfa.avustribesbackend.models.MyError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
class PlayerServiceImplTest {
    private PlayerService playerService;
    PlayerRegistrationBody playerRegistrationBody;

    @BeforeEach
    public void beforeEach() {
        playerRegistrationBody= new PlayerRegistrationBody();
        playerService = Mockito.mock(PlayerService.class);
    }

    @Test
    void username_longer_than_4(){
        playerRegistrationBody.setUsername("kak");
        playerRegistrationBody.setEmail("hello@gmail.com");
        playerRegistrationBody.setPassword("password");

        MyError error = new MyError("Username must be at least 4 characters long");
        ResponseEntity<Object> responseEntity = new ResponseEntity<>(error,
                HttpStatusCode.valueOf(400));
        Mockito.when(playerService.registerPlayer(Mockito.any(PlayerRegistrationBody.class)))
                .thenReturn(responseEntity);
        assertEquals(responseEntity, playerService.registerPlayer(playerRegistrationBody));
    }

    @Test
    void password_longer_than_8(){
        playerRegistrationBody.setUsername("Hello");
        playerRegistrationBody.setEmail("hello@gmail.com");
        playerRegistrationBody.setPassword("short");

        MyError error = new MyError("Password must be at least 8 characters long");
        ResponseEntity<Object> responseEntity = new ResponseEntity<>(error,
                HttpStatusCode.valueOf(400));
        Mockito.when(playerService.registerPlayer(Mockito.any(PlayerRegistrationBody.class)))
                .thenReturn(responseEntity);
        assertEquals(responseEntity, playerService.registerPlayer(playerRegistrationBody));
    }

    @Test
    void email_validation(){
        playerRegistrationBody.setUsername("Hello");
        playerRegistrationBody.setEmail("hello@gmail");
        playerRegistrationBody.setPassword("password");

        MyError error = new MyError("Invalid email");
        ResponseEntity<Object> responseEntity = new ResponseEntity<>(error,
                HttpStatusCode.valueOf(400));
        Mockito.when(playerService.registerPlayer(Mockito.any(PlayerRegistrationBody.class)))
                .thenReturn(responseEntity);
        assertEquals(responseEntity, playerService.registerPlayer(playerRegistrationBody));
    }

    @Test
    void null_user(){

        MyError error = new MyError("Unknown error");
        ResponseEntity<Object> responseEntity = new ResponseEntity<>(error,
                HttpStatusCode.valueOf(400));
        Mockito.when(playerService.registerPlayer(Mockito.any(PlayerRegistrationBody.class)))
                .thenReturn(responseEntity);
        assertEquals(responseEntity, playerService.registerPlayer(playerRegistrationBody));
    }

    @Test
    void successful_user_registration(){
        playerRegistrationBody.setUsername("Hello");
        playerRegistrationBody.setEmail("hello@gmail.com");
        playerRegistrationBody.setPassword("password");

        ResponseEntity<Object> responseEntity = new ResponseEntity<>("successful creation",
                HttpStatusCode.valueOf(200));
        Mockito.when(playerService.registerPlayer(Mockito.any(PlayerRegistrationBody.class)))
                .thenReturn(responseEntity);
        assertEquals(responseEntity, playerService.registerPlayer(playerRegistrationBody));
    }
}