package org.gfa.avustribesbackend.services.World;

import org.gfa.avustribesbackend.dtos.WorldResponseDto;
import org.gfa.avustribesbackend.models.Kingdom;
import org.gfa.avustribesbackend.models.World;
import org.gfa.avustribesbackend.repositories.KingdomRepository;
import org.gfa.avustribesbackend.repositories.WorldRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class WorldServiceImplTest {
  @Autowired private WorldServiceImpl worldService;
  @Autowired private KingdomRepository kingdomRepository;
  @Autowired private WorldRepository worldRepository;

  @Test
  void find_one_world() {
    // Arrange
    World world = new World();
    world.setId(1L);

    Kingdom kingdom = new Kingdom("Utopia", 40.5, 45.4, world);
    kingdom.setId(1L);

    worldRepository.save(world);
    kingdomRepository.save(kingdom);

    // Act
    WorldResponseDto worldResponseDto = new WorldResponseDto(
            world.getId(), world.getName(), kingdomRepository.countAllByWorld_Id(world.getId()));

    List<WorldResponseDto> allDTOs = new ArrayList<>();

    allDTOs.add(worldResponseDto);

    ResponseEntity<Object> goodResponse = new ResponseEntity<>(allDTOs,
            HttpStatusCode.valueOf(200));

    // Assert
    assertNotNull(worldService.index());
    assertEquals(goodResponse.getStatusCode(), worldService.index().getStatusCode());
    assertEquals(goodResponse.hasBody(), worldService.index().hasBody());
    assertEquals(
        Objects.requireNonNull(goodResponse.getBody()).getClass(),
        Objects.requireNonNull(worldService.index().getBody()).getClass());
  }

  @Test
  void find_no_world() {
    ResponseEntity<Object> badResponse =
        new ResponseEntity<>("Error! No world!", HttpStatusCode.valueOf(404));

    assertNotNull(worldService.index());
    assertEquals(badResponse.getStatusCode(), worldService.index().getStatusCode());
    assertEquals(badResponse.hasBody(), worldService.index().hasBody());
    assertEquals(
        Objects.requireNonNull(badResponse.getBody()).getClass(),
        Objects.requireNonNull(worldService.index().getBody()).getClass());
  }
}
