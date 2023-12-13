package org.gfa.avustribesbackend.services.Player;

import org.gfa.avustribesbackend.dtos.PlayerRegistrationBody;
import org.gfa.avustribesbackend.models.MyError;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
class PlayerServiceImplTest {
    PlayerRegistrationBody playerRegistrationBody = new PlayerRegistrationBody();
    private final PlayerServiceImpl playerService;

    PlayerServiceImplTest(PlayerServiceImpl playerService) {
        this.playerService = playerService;
    }

    @Test
    void no_username(){
        // Arrange
        MyError error = new MyError("Username is required");
        ResponseEntity<Object> responseEntity = new ResponseEntity<>(error,
                HttpStatusCode.valueOf(404));

        // Act

        // Assert
        assertEquals(responseEntity, playerService.registerPlayer(playerRegistrationBody));
    }

}