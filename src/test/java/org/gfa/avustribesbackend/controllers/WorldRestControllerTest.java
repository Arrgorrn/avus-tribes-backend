package org.gfa.avustribesbackend.controllers;

import org.gfa.avustribesbackend.models.Kingdom;
import org.gfa.avustribesbackend.models.World;
import org.gfa.avustribesbackend.repositories.KingdomRepository;
import org.gfa.avustribesbackend.repositories.WorldRepository;
import org.gfa.avustribesbackend.services.World.WorldService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WorldRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WorldService worldService;

    @Autowired
    private WorldRepository worldRepository;

    @Autowired
    private KingdomRepository kingdomRepository;

    @Test
    void index() throws  Exception{
        // Arrange
        World world = new World();
        world.setId(1L);

        Kingdom kingdom = new Kingdom("Utopia", 40.5, 45.4, world);
        kingdom.setId(1L);

        worldRepository.save(world);
        kingdomRepository.save(kingdom);

//        ResponseEntity<Object> responseEntity = new ResponseEntity<>("hi",
//                HttpStatusCode.valueOf(200));
        mockMvc.perform(get("/worlds"))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

//        mvc.perform(get("/hello?name=Dan"))
//                .andExpect(content().string("Hello, Dan"));

    }
}
