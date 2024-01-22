package org.gfa.avustribesbackend.services.Email;

import org.gfa.avustribesbackend.controllers.PlayerController;
import org.gfa.avustribesbackend.exceptions.EmailException;
import org.gfa.avustribesbackend.models.Player;
import org.gfa.avustribesbackend.repositories.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailVerificationTest {
  @MockBean private JavaMailSender javaMailSender;
  @Mock private PlayerRepository playerRepository;
  @InjectMocks private PlayerController playerController;
  @Mock private EmailVerificationServiceImpl emailVerificationService;

  @Test
  void sendVerificationEmail_shouldSendEmail() {
    // Arrange
    MockitoAnnotations.openMocks(this);

    String token = "someToken";
    Player player = new Player();
    player.setIsVerified(false);
    player.setUserName("Username");
    player.setEmail("test@example.com");
    when(playerRepository.findByVerificationToken(token)).thenReturn(player);

    // Act
    emailVerificationService.sendVerificationEmail(token);

    // Assert
    verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
  }

  @Test
  void verifyEmail_alreadyVerified_shouldThrowException() {
    // Arrange
    MockitoAnnotations.openMocks(this);
    String token = "someToken";
    Player player = new Player();
    player.setVerificationToken(token);
    player.setIsVerified(true);
    when(playerRepository.findByVerificationToken(token)).thenReturn(player);
    when(emailVerificationService.isVerified(player)).thenReturn(false);

    // Assert that the EmailException is thrown
    assertThrows(
        EmailException.class,
        () -> playerController.verifyEmail(token));
  }

  @Test
  void verifyEmail_withValidToken_shouldReturnTrue() {
    // Arrange
    MockitoAnnotations.openMocks(this);

    String token = "validToken";
    Player player = new Player();
    player.setIsVerified(false);
    player.setVerificationToken(token);
    Date futureDate = new Date(System.currentTimeMillis() + 1000000);
    player.setVerificationTokenExpiresAt(futureDate);

    when(playerRepository.findByVerificationToken(token)).thenReturn(player);

    // Act
    boolean result = emailVerificationService.verifyEmail(token);

    // Assert
    assertTrue(result);
    assertNotNull(player.getVerifiedAt());
  }

  @Test
  void verifyEmail_withExpiredToken_shouldReturnFalse() {
    // Arrange
    MockitoAnnotations.openMocks(this);

    String token = "expiredToken";
    Player player = new Player();
    player.setVerificationToken(token);
    player.setIsVerified(false);
    Date pastDate = new Date(System.currentTimeMillis() - 1000000);
    player.setVerificationTokenExpiresAt(pastDate);

    when(playerRepository.findByVerificationToken(token)).thenReturn(player);

    // Act
    boolean result = emailVerificationService.verifyEmail(token);

    // Assert
    assertFalse(result);
    assertNull(player.getVerifiedAt());
  }
}
