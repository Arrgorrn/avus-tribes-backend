package org.gfa.avustribesbackend.services.Troop;

import org.gfa.avustribesbackend.dtos.KingdomResponseDTO;
import org.gfa.avustribesbackend.exceptions.CreationException;
import org.gfa.avustribesbackend.models.Kingdom;
import org.gfa.avustribesbackend.models.Player;
import org.gfa.avustribesbackend.models.Resource;
import org.gfa.avustribesbackend.models.World;
import org.gfa.avustribesbackend.models.enums.ResourceTypeValue;
import org.gfa.avustribesbackend.repositories.BuildingRepository;
import org.gfa.avustribesbackend.repositories.ResourceRepository;
import org.gfa.avustribesbackend.repositories.TroopRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class TroopServiceImplTest {
  @Mock TroopRepository troopRepository;
  @Mock BuildingRepository buildingRepository;
  @Mock ResourceRepository resourceRepository;
  @InjectMocks TroopServiceImpl troopService;
  Kingdom kingdom;

  @BeforeEach
  public void beforeEach() {
    World world = new World();
    Player player = new Player();
    kingdom = new Kingdom(player, world);
  }

  @Test
  void creating_troop_no_enough_gold() {
    CreationException creationException =
        assertThrows(CreationException.class, () -> {
              troopService.creating(kingdom);
            });
    assertEquals("Not enough gold to create troop", creationException.getMessage());
  }

  @Test
  void creating_troop_no_academy() {
    when(resourceRepository.getResourceAmount(kingdom, ResourceTypeValue.GOLD)).thenReturn(1000);
    CreationException creationException =
        assertThrows(CreationException.class, () -> {
              troopService.creating(kingdom);
            });
    assertEquals("Need academy to train troops!", creationException.getMessage());
  }

  @Test
  void upgrading() {}
}
