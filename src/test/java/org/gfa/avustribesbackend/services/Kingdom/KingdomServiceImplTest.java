package org.gfa.avustribesbackend.services.Kingdom;

import org.gfa.avustribesbackend.controllers.KingdomController;
import org.gfa.avustribesbackend.dtos.KingdomResponseDTO;
import org.gfa.avustribesbackend.exceptions.CredentialException;
import org.gfa.avustribesbackend.models.Kingdom;
import org.gfa.avustribesbackend.models.Player;
import org.gfa.avustribesbackend.repositories.KingdomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class KingdomServiceImplTest {
  @InjectMocks private KingdomController kingdomController;

  @Mock private KingdomService kingdomService;

  @InjectMocks private KingdomServiceImpl kingdomServiceImpl;

  @Mock private KingdomRepository kingdomRepository;

  @BeforeEach
  public void beforeEach() {
    KingdomResponseDTO kingdomResponseDTO =
        new KingdomResponseDTO(1L, 1L, 1L, "testKingdom", 10.0, 20.0);
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void list_all_kingdoms() {
    Kingdom kingdom1 = new Kingdom();
    kingdom1.setId(1L);
    kingdom1.setName("Kingdom1");
    kingdom1.setCoordinateX(10.0);
    kingdom1.setCoordinateY(20.0);

    org.gfa.avustribesbackend.models.World world1 = new org.gfa.avustribesbackend.models.World();
    world1.setId(1L);
    kingdom1.setWorld(world1);

    Player player1 = new Player();
    player1.setId(1L);
    kingdom1.setPlayer(player1);

    Kingdom kingdom2 = new Kingdom();
    kingdom2.setId(2L);
    kingdom2.setName("Kingdom2");
    kingdom2.setCoordinateX(15.0);
    kingdom2.setCoordinateY(25.0);

    org.gfa.avustribesbackend.models.World world2 = new org.gfa.avustribesbackend.models.World();
    world2.setId(2L);
    kingdom2.setWorld(world2);

    Player player2 = new Player();
    player2.setId(2L);
    kingdom2.setPlayer(player2);

    when(kingdomRepository.findAll()).thenReturn(Arrays.asList(kingdom1, kingdom2));

    when(kingdomService.listKingdoms())
        .thenReturn(
            Arrays.asList(
                kingdomServiceImpl.createKingdomDTO(kingdom1),
                kingdomServiceImpl.createKingdomDTO(kingdom2)));

    List<KingdomResponseDTO> list1 = new ArrayList<>();
    list1.add(kingdomServiceImpl.createKingdomDTO(kingdom1));
    list1.add(kingdomServiceImpl.createKingdomDTO(kingdom2));

    List<KingdomResponseDTO> list2 = kingdomServiceImpl.listKingdoms();

    assertEquals(list1.size(), list2.size());
  }

  @Test
  void show_one_kingdom_found_returns_DTO() {
    // Arrange
    long kingdomId = 1L;
    KingdomResponseDTO kingdomResponseDTO =
        new KingdomResponseDTO(kingdomId, 1L, 1L, "test", 10.0, 10.0);

    // Mocking behavior
    when(kingdomService.checkId(kingdomId)).thenReturn(true);
    when(kingdomService.returnKingdomDTOById(kingdomId)).thenReturn(kingdomResponseDTO);

    // Act
    ResponseEntity<Object> responseEntity = kingdomController.index(kingdomId);

    // Assert
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(kingdomResponseDTO, responseEntity.getBody());
    verify(kingdomService, times(1)).checkId(kingdomId);
    verify(kingdomService, times(1)).returnKingdomDTOById(kingdomId);
  }

  @Test
  void show_one_kingdom_not_found_throws_exception() {
    // Arrange
    long kingdomId = 2L;

    // Mocking behavior
    when(kingdomService.checkId(kingdomId)).thenReturn(false);
    when(kingdomService.returnKingdomDTOById(kingdomId))
        .thenThrow(new CredentialException("Kingdom not found"));

    // Act
    CredentialException exception =
        assertThrows(CredentialException.class, () -> kingdomController.index(kingdomId));

    // Assert
    assertEquals("Kingdom not found", exception.getMessage());
  }
}
