package org.gfa.avustribesbackend.services.Player;

import org.gfa.avustribesbackend.dtos.PlayerRegistrationBody;
import org.gfa.avustribesbackend.models.MyError;
import org.gfa.avustribesbackend.repositories.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
class PlayerServiceImplTest {
    private PlayerService playerService;
    private PlayerRepository playerRepository;
    PlayerRegistrationBody playerRegistrationBody;

    @BeforeEach
    public void beforeEach() {
        playerRegistrationBody= new PlayerRegistrationBody();
        playerService = Mockito.mock(PlayerService.class);
        playerRepository = Mockito.mock(PlayerRepository.class);
    }

    @Test
    void doubling() {
        playerRegistrationBody.setEmail("hello@gmail.com");
        playerRegistrationBody.setPassword("password");

        MyError error = new MyError("Username is required");
        ResponseEntity<Object> responseEntity = new ResponseEntity<>(error,
                HttpStatusCode.valueOf(400));


        Mockito.when(playerService.registerPlayer(Mockito.any(PlayerRegistrationBody.class)))
                .thenReturn(responseEntity);

        assertEquals(responseEntity, playerService.registerPlayer(playerRegistrationBody));
    }
}