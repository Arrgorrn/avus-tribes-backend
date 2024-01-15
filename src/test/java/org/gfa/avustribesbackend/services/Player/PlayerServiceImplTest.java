package org.gfa.avustribesbackend.services.Player;

import org.gfa.avustribesbackend.controllers.PlayerController;
import org.gfa.avustribesbackend.dtos.PlayerInfoDTO;
import org.gfa.avustribesbackend.dtos.PlayerRegistrationBody;
import org.gfa.avustribesbackend.models.Player;
import org.gfa.avustribesbackend.models.RegistrationError;
import org.gfa.avustribesbackend.repositories.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerServiceImplTest {
  @InjectMocks
  private PlayerController playerController;

  @Mock
  private PlayerService playerService;

  @InjectMocks
  private PlayerServiceImpl playerServiceImpl;

  @Mock
  private PlayerRepository playerRepository;

  PlayerRegistrationBody playerRegistrationBody;

  @BeforeEach
  public void beforeEach() {
    playerRegistrationBody = new PlayerRegistrationBody();
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
    when(playerRepository.existsByEmailIgnoreCase(playerRegistrationBody.getEmail())).thenReturn(false);
    assertFalse(playerRepository.existsByEmailIgnoreCase(playerRegistrationBody.getEmail()));
  }

  @Test
  void username_longer_than_4() {
    playerRegistrationBody.setUsername("kak");
    playerRegistrationBody.setEmail("hello@gmail.com");
    playerRegistrationBody.setPassword("password");

    ResponseEntity<Object> responseEntity = playerService.registerPlayer(playerRegistrationBody);

    assertErrorResponse(responseEntity, "Username must be at least 4 characters long");
  }

  @Test
  void password_longer_than_8() {
    playerRegistrationBody.setUsername("Hello");
    playerRegistrationBody.setEmail("hello@gmail.com");
    playerRegistrationBody.setPassword("short");

    ResponseEntity<Object> responseEntity = playerService.registerPlayer(playerRegistrationBody);

    assertErrorResponse(responseEntity, "Password must be at least 8 characters long");
  }

  @Test
  void email_validation() {
    playerRegistrationBody.setUsername("Hello");
    playerRegistrationBody.setEmail("hellogmail.com");
    playerRegistrationBody.setPassword("password");

    ResponseEntity<Object> responseEntity = playerService.registerPlayer(playerRegistrationBody);

    assertErrorResponse(responseEntity, "Invalid email");
  }

  @Test
  void successful_user_registration() {
    playerRegistrationBody.setUsername("Hello");
    playerRegistrationBody.setEmail("hello@gmail.com");
    playerRegistrationBody.setPassword("password");

    ResponseEntity<Object> responseEntity = playerService.registerPlayer(playerRegistrationBody);
    ResponseEntity<Object> responseEntity1 =
        new ResponseEntity<>("successful creation", HttpStatusCode.valueOf(200));

    assertEquals(responseEntity, responseEntity1);
  }

  @Test
  void list_all_players() {
    Player player1 = new Player("username", "email@test.com", "password", "token");
    player1.setIsVerified(true);
    player1.setVerifiedAt(new Date(System.currentTimeMillis()));

    Player player2 = new Player("usernameee", "eeemail@test.com", "password", "tokeeen");
    player2.setIsVerified(true);
    player2.setVerifiedAt(new Date(System.currentTimeMillis()));

    when(playerRepository.save(any())).thenReturn(player1, player2);
    when(playerRepository.findAll()).thenReturn(Arrays.asList(player1, player2));

    when(playerService.listPlayerInfoDTO()).thenReturn(Arrays.asList(
            playerServiceImpl.createPlayerInfoDTO(player1),
            playerServiceImpl.createPlayerInfoDTO(player2)
    ));

    List<PlayerInfoDTO> list1 = new ArrayList<>();
    list1.add(playerServiceImpl.createPlayerInfoDTO(player1));
    list1.add(playerServiceImpl.createPlayerInfoDTO(player2));

    List<PlayerInfoDTO> list2 = playerServiceImpl.listPlayerInfoDTO();

    System.out.println("List1: " + list1);
    System.out.println("List2: " + list2);

    assertEquals(list1.size(), list2.size());
  }

  @Test
  void testIndexPlayerFound() {
    // Arrange
    long playerId = 1L;
    PlayerInfoDTO playerInfoDTO = new PlayerInfoDTO(playerId,"username",false,null);

    // Mocking behavior
    when(playerService.checkId(playerId)).thenReturn(true);
    when(playerService.findPlayerDTOById(playerId)).thenReturn(playerInfoDTO);

    // Act
    ResponseEntity<Object> responseEntity = playerController.index(playerId);

    // Assert
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(playerInfoDTO, responseEntity.getBody());
    verify(playerService, times(1)).checkId(playerId);
    verify(playerService, times(1)).findPlayerDTOById(playerId);
  }

  @Test
  void testIndexPlayerNotFound() {
    // Arrange
    long playerId = 2L;

    // Mocking behavior
    when(playerService.checkId(playerId)).thenReturn(false);

    // Act
    ResponseEntity<Object> responseEntity = playerController.index(playerId);

    // Assert
    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    assertEquals("Player not found", responseEntity.getBody());
    verify(playerService, times(1)).checkId(playerId);
    verify(playerService, never()).findPlayerDTOById(anyLong());
  }

  private void assertErrorResponse(
      ResponseEntity<Object> responseEntity, String expectedErrorMessage) {
    assertEquals(400, responseEntity.getStatusCodeValue());
    assertTrue(responseEntity.getBody() instanceof RegistrationError);
    RegistrationError error = (RegistrationError) responseEntity.getBody();
    assertEquals(expectedErrorMessage, error.getError());
  }
}
