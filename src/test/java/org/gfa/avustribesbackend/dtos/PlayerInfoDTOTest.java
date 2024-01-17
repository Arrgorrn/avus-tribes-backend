package org.gfa.avustribesbackend.dtos;

import org.gfa.avustribesbackend.models.Player;
import org.gfa.avustribesbackend.repositories.PlayerRepository;
import org.gfa.avustribesbackend.services.Player.PlayerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerInfoDTOTest {
  @InjectMocks private PlayerServiceImpl playerService;

  @Mock private PlayerRepository playerRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void create_PlayerInfoDTO_verified() {
    // Arrange
    Player player = new Player("username", "email@test.com", "password", "token");
    player.setIsVerified(true);
    player.setVerifiedAt(new Date(System.currentTimeMillis()));

    // Act
    PlayerInfoDTO dto = playerService.createPlayerInfoDTO(player);

    // Assert
    assertEquals(player.getUserName(), dto.getUsername());
    assertEquals(player.getIsVerified(), dto.getIsVerified());
    assertEquals(player.getVerifiedAt(), dto.getVerified_at());
  }

    @Test
    void create_PlayerInfoDTO_not_verified() {
        // Arrange
        Player player = new Player("username", "email@test.com", "password", "token");
        player.setIsVerified(false);

        // Act
        PlayerInfoDTO dto = playerService.createPlayerInfoDTO(player);

        // Assert
        assertEquals(player.getUserName(), dto.getUsername());
        assertEquals(player.getIsVerified(), dto.getIsVerified());
        assertEquals(player.getVerifiedAt(), dto.getVerified_at());
    }
}