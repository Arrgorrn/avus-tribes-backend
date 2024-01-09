package org.gfa.avustribesbackend.models;

import org.gfa.avustribesbackend.repositories.PlayerRepository;
import org.gfa.avustribesbackend.services.Email.EmailVerificationServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailVerificationTest {

  @Mock private JavaMailSender javaMailSender;

  @Mock private PlayerRepository playerRepository;

  @InjectMocks private EmailVerificationServiceImpl emailVerificationService;

  @Test
  void sendVerificationEmail_shouldSendEmail() {
    // Arrange
    MockitoAnnotations.openMocks(this); // Initialize mocks

    String token = "someToken";
    Player player = new Player();
    player.setEmail("test@example.com");
    when(playerRepository.findByVerificationToken(token)).thenReturn(player);

    // Act
    emailVerificationService.sendVerificationEmail(token);

    // Assert
    verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
  }

  @Test
  void verifyEmail_withValidToken_shouldReturnTrue() {
    // Arrange
    MockitoAnnotations.openMocks(this); // Initialize mocks

    String token = "validToken";
    Player player = new Player();
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
    MockitoAnnotations.openMocks(this); // Initialize mocks

    String token = "expiredToken";
    Player player = new Player();
    player.setVerificationToken(token);
    Date pastDate = new Date(System.currentTimeMillis() - 1000000);
    player.setVerificationTokenExpiresAt(pastDate);

    when(playerRepository.findByVerificationToken(token)).thenReturn(player);

    // Act
    boolean result = emailVerificationService.verifyEmail(token);

    // Assert
    assertFalse(result);
    assertNull(player.getVerifiedAt());
  }

    @Test
    void verifyEmail_withInvalidToken_shouldReturnFalse() {
        // Arrange
        MockitoAnnotations.openMocks(this); // Initialize mocks

        String token = "invalidToken";
        Player player = new Player();
        player.setVerificationToken(token);
        Date pastDate = new Date(System.currentTimeMillis() - 1000000);
        player.setVerificationTokenExpiresAt(pastDate);

        playerRepository.save(player);

        EmailVerificationServiceImpl emailVerificationService =
                new EmailVerificationServiceImpl(javaMailSender, playerRepository);

        when(playerRepository.findByVerificationToken(token)).thenReturn(player);

        // Act
        boolean result = emailVerificationService.verifyEmail(token);

        // Assert
        assertFalse(result);
    }

}