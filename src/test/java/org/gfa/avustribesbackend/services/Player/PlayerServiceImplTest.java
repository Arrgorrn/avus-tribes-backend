package org.gfa.avustribesbackend.services.Player;

import org.gfa.avustribesbackend.dtos.PlayerRegistrationBody;
import org.gfa.avustribesbackend.exceptions.CredentialException;
import org.gfa.avustribesbackend.exceptions.ErrorResponse;
import org.gfa.avustribesbackend.repositories.PlayerRepository;
import org.gfa.avustribesbackend.services.Email.EmailVerificationService;
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
  @InjectMocks private PlayerServiceImpl playerService;
  PlayerRegistrationBody playerRegistrationBody;
  @Mock PlayerRepository playerRepository;
  @Mock EmailVerificationService emailVerificationService;

  @BeforeEach
  public void beforeEach() {
    playerRegistrationBody = new PlayerRegistrationBody();
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void test_register_player_username_null() {
    Exception exception = assertThrows(CredentialException.class, () ->playerService.registerPlayer(playerRegistrationBody));
    String expectedMessage = "Username is required";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void test_register_player_password_null() {
    playerRegistrationBody.setUsername("testUser");
    Exception exception = assertThrows(CredentialException.class, () ->playerService.registerPlayer(playerRegistrationBody));
    String expectedMessage = "Password is required";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void test_register_player_email_null() {
    playerRegistrationBody.setUsername("testUser");
    playerRegistrationBody.setPassword("password");
    Exception exception = assertThrows(CredentialException.class, () ->playerService.registerPlayer(playerRegistrationBody));
    String expectedMessage = "Email is required";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
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
    Exception exception = assertThrows(CredentialException.class, () ->playerService.registerPlayer(playerRegistrationBody));
    String expectedMessage = "Username must be at least 4 characters long";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void password_longer_than_8() {
    playerRegistrationBody.setUsername("Hello");
    playerRegistrationBody.setEmail("hello@gmail.com");
    playerRegistrationBody.setPassword("short");
    Exception exception = assertThrows(CredentialException.class, () ->playerService.registerPlayer(playerRegistrationBody));
    String expectedMessage = "Password must be at least 8 characters long";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void email_validation() {
    playerRegistrationBody.setUsername("Hello");
    playerRegistrationBody.setEmail("hellogmail.com");
    playerRegistrationBody.setPassword("password");
    Exception exception = assertThrows(CredentialException.class, () ->playerService.registerPlayer(playerRegistrationBody));
    String expectedMessage = "Invalid email";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
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

  private void assertErrorResponse(
      ResponseEntity<Object> responseEntity, String expectedErrorMessage) {
    assertEquals(400, responseEntity.getStatusCodeValue());
    assertTrue(responseEntity.getBody() instanceof ErrorResponse);
    ErrorResponse error = (ErrorResponse) responseEntity.getBody();
    assertEquals(expectedErrorMessage, error.getMessage());
  }
}
