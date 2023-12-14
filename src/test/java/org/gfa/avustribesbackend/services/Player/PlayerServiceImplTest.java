package org.gfa.avustribesbackend.services.Player;

import org.gfa.avustribesbackend.dtos.PlayerRegistrationBody;
import org.gfa.avustribesbackend.models.MyError;
import org.gfa.avustribesbackend.repositories.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PlayerServiceImplTest {
    private PlayerService playerService;
    private PlayerRepository playerRepository;

    @BeforeEach
    public void beforeEach() {
        PlayerRegistrationBody playerRegistrationBody = new PlayerRegistrationBody();
        playerService = Mockito.mock(PlayerService.class);
        playerRepository = Mockito.mock(PlayerRepository.class);
    }
//    @Test
//    void no_username(){
//        // Arrange
//        MyError error = new MyError("Username is required");
//        ResponseEntity<Object> responseEntity = new ResponseEntity<>(error,
//                HttpStatusCode.valueOf(404));
//
//        // Act
//        String name = "peter";
//
//        // Assert
//        assertEquals(name, name);
//    }

}