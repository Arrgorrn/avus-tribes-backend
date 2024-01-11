package org.gfa.avustribesbackend.services.ResetPassword;

import org.gfa.avustribesbackend.dtos.EmailRequestDTO;
import org.gfa.avustribesbackend.dtos.PasswordRequestDTO;
import org.gfa.avustribesbackend.dtos.TokenRequestDTO;
import org.gfa.avustribesbackend.exceptions.CredentialException;
import org.gfa.avustribesbackend.exceptions.ErrorResponse;
import org.gfa.avustribesbackend.exceptions.VerificationException;
import org.gfa.avustribesbackend.models.Player;
import org.gfa.avustribesbackend.repositories.PlayerRepository;
import org.gfa.avustribesbackend.services.Player.PlayerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResetPasswordServiceImplTest {

  @InjectMocks
  private ResetPasswordServiceImpl resetPasswordService;
  @Mock
  private PlayerRepository playerRepository;
  @Mock
  private PlayerService playerService;
  @Mock
  private JavaMailSender javaMailSender;
  private EmailRequestDTO emailRequestDTO;
  private Player player;
  private ErrorResponse errorResponse;
  private TokenRequestDTO tokenRequestDTO;
  PasswordRequestDTO passwordRequestDTO;
  private Date date;

  @Test
  void sendResetPasswordEmail_with_no_errors_should_response_with_status_200_and_set_password_token_and_password_expiration_to_player() {
    emailRequestDTO = new EmailRequestDTO("example@example.com");

    player = new Player();
    player.setVerified(true);

    when(playerService.verificationToken()).thenReturn("token");

    String expectedToken = "token";

    ResponseEntity<Object> expected = new ResponseEntity<>(HttpStatusCode.valueOf(200));

    when(playerRepository.existsByEmailIgnoreCase(emailRequestDTO.getEmail())).thenReturn(true);
    when(playerRepository.findByEmailIgnoreCase(emailRequestDTO.getEmail())).thenReturn(player);

    ResponseEntity<Object> actual = resetPasswordService.sendResetPasswordEmail(emailRequestDTO);

    String actualToken = player.getForgottenPasswordToken();

    assertTrue(expectedToken.contains(actualToken));
    assertNotNull(player.getForgottenPasswordToken());
    assertNotNull(player.getForgottenPasswordTokenExpiresAt());
    assertEquals(expected.getStatusCode(), actual.getStatusCode());
  }

  @Test
  void sendResetPasswordEmail_with_not_existing_email_in_database_should_throw_credential_exception() {
    emailRequestDTO = new EmailRequestDTO("example@example.com");

    when(playerRepository.existsByEmailIgnoreCase(emailRequestDTO.getEmail())).thenReturn(false);

    Exception exception = assertThrows(CredentialException.class, () -> resetPasswordService.sendResetPasswordEmail(emailRequestDTO));

    String expectedMessage = "Invalid email!";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void sendResetPasswordEmail_with_null_emailRequestDTO_should_throw_credential_exception() {
    emailRequestDTO = null;

    Exception exception = assertThrows(CredentialException.class, () -> resetPasswordService.sendResetPasswordEmail(emailRequestDTO));

    String expectedMessage = "Invalid email!";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void sendResetPasswordEmail_with_empty_email_in_emailRequestDTO_should_throw_credential_exception() {
    emailRequestDTO = new EmailRequestDTO("");

    Exception exception = assertThrows(CredentialException.class, () -> resetPasswordService.sendResetPasswordEmail(emailRequestDTO));

    String expectedMessage = "Invalid email!";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void sendResetPasswordEmail_with_null_email_in_emailRequestDTO_should_throw_credential_exception() {
    emailRequestDTO = new EmailRequestDTO();

    Exception exception = assertThrows(CredentialException.class, () -> resetPasswordService.sendResetPasswordEmail(emailRequestDTO));

    String expectedMessage = "Invalid email!";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void sendResetPasswordEmail_with_unverified_player_should_throw_verification_exception() {
    emailRequestDTO = new EmailRequestDTO("example@example.com");

    player = new Player();

    when(playerRepository.existsByEmailIgnoreCase(emailRequestDTO.getEmail())).thenReturn(true);
    when(playerRepository.findByEmailIgnoreCase(emailRequestDTO.getEmail())).thenReturn(player);

    Exception exception = assertThrows(VerificationException.class, () -> resetPasswordService.sendResetPasswordEmail(emailRequestDTO));

    String expectedMessage = "Unverified email!";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void resetPassword_with_null_PasswordRequestDTO_should_throw_credential_exception() {
    passwordRequestDTO = null;
    tokenRequestDTO = new TokenRequestDTO("token");

    Exception exception = assertThrows(CredentialException.class, () -> resetPasswordService.resetPassword(tokenRequestDTO, passwordRequestDTO));

    String expectedMessage = "Password is required";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void resetPassword_with_null_password_in_PasswordRequestDTO_should_throw_credential_exception() {
    passwordRequestDTO = new PasswordRequestDTO();
    tokenRequestDTO = new TokenRequestDTO("token");

    Exception exception = assertThrows(CredentialException.class, () -> resetPasswordService.resetPassword(tokenRequestDTO, passwordRequestDTO));

    String expectedMessage = "Password is required";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void resetPassword_with_empty_password_in_PasswordRequestDTO_should_throw_credential_exception() {
    passwordRequestDTO = new PasswordRequestDTO("");
    tokenRequestDTO = new TokenRequestDTO("token");

    Exception exception = assertThrows(CredentialException.class, () -> resetPasswordService.resetPassword(tokenRequestDTO, passwordRequestDTO));

    String expectedMessage = "Password is required";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void resetPassword_with_null_TokenRequestDTO_should_throw_verification_exception() {
    passwordRequestDTO = new PasswordRequestDTO("password");
    tokenRequestDTO = null;

    Exception exception = assertThrows(VerificationException.class, () -> resetPasswordService.resetPassword(tokenRequestDTO, passwordRequestDTO));

    String expectedMessage = "Invalid token";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void resetPassword_with_null_token_in_TokenRequestDTO_should_throw_verification_exception() {
    passwordRequestDTO = new PasswordRequestDTO("password");
    tokenRequestDTO = new TokenRequestDTO();

    Exception exception = assertThrows(VerificationException.class, () -> resetPasswordService.resetPassword(tokenRequestDTO, passwordRequestDTO));

    String expectedMessage = "Invalid token";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void resetPassword_with_empty_token_in_TokenRequestDTO_should_throw_verification_exception() {
    passwordRequestDTO = new PasswordRequestDTO("password");
    tokenRequestDTO = new TokenRequestDTO("");

    Exception exception = assertThrows(VerificationException.class, () -> resetPasswordService.resetPassword(tokenRequestDTO, passwordRequestDTO));

    String expectedMessage = "Invalid token";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void resetPassword_with_not_existing_token_in_database_should_throw_verification_exception() {
    passwordRequestDTO = new PasswordRequestDTO("password");
    tokenRequestDTO = new TokenRequestDTO("token");

    when(playerRepository.existsByForgottenPasswordToken(tokenRequestDTO.getToken())).thenReturn(false);

    Exception exception = assertThrows(VerificationException.class, () -> resetPasswordService.resetPassword(tokenRequestDTO, passwordRequestDTO));

    String expectedMessage = "Invalid token";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void resetPassword_with_password_length_less_than_8_should_throw_credential_exception() {
    passwordRequestDTO = new PasswordRequestDTO("passw");
    tokenRequestDTO = new TokenRequestDTO("token");

    when(playerRepository.existsByForgottenPasswordToken(tokenRequestDTO.getToken())).thenReturn(true);

    Exception exception = assertThrows(CredentialException.class, () -> resetPasswordService.resetPassword(tokenRequestDTO, passwordRequestDTO));

    String expectedMessage = "Password must be at least 8 characters long";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void resetPassword_with_expired_verification_token_should_throw_verification_exception() {
    passwordRequestDTO = new PasswordRequestDTO("password");
    tokenRequestDTO = new TokenRequestDTO("token");
    player = new Player();
    date = new Date(System.currentTimeMillis() - 1000 * 60 * 60);
    player.setForgottenPasswordTokenExpiresAt(date);

    when(playerRepository.existsByForgottenPasswordToken(tokenRequestDTO.getToken())).thenReturn(true);
    when(playerRepository.findByForgottenPasswordToken(tokenRequestDTO.getToken())).thenReturn(player);

    Exception exception = assertThrows(VerificationException.class, () -> resetPasswordService.resetPassword(tokenRequestDTO, passwordRequestDTO));

    String expectedMessage = "Expired token";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void resetPassword_with_no_errors_should_respond_with_status_200_and_set_new_password_to_player() {
    String expectedPassword = "newPassword";
    passwordRequestDTO = new PasswordRequestDTO(expectedPassword);
    tokenRequestDTO = new TokenRequestDTO("token");
    player = new Player();
    date = new Date(System.currentTimeMillis() + 1000 * 60 * 60);
    player.setForgottenPasswordTokenExpiresAt(date);
    player.setPassword("oldPassword");

    when(playerRepository.existsByForgottenPasswordToken(tokenRequestDTO.getToken())).thenReturn(true);
    when(playerRepository.findByForgottenPasswordToken(tokenRequestDTO.getToken())).thenReturn(player);

    ResponseEntity<Object> expected = new ResponseEntity<>(HttpStatusCode.valueOf(200));
    ResponseEntity<Object> actual = resetPasswordService.resetPassword(tokenRequestDTO, passwordRequestDTO);

    String actualPassword = player.getPassword();

    assertTrue(expectedPassword.contains(actualPassword));
    assertEquals(expected.getStatusCode(), actual.getStatusCode());
  }
}
