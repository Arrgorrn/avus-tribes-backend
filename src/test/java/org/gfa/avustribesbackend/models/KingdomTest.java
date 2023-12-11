package org.gfa.avustribesbackend.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KingdomTest {
    @Test
    void test_create_kingdom_all_fields() {
        // Arrange
        Long worldId = 1L;
        String name = "Test Kingdom";
        Double coordinateX = 10.0;
        Double coordinateY = 20.0;

        // Act
        Kingdom kingdom = new Kingdom(worldId, name, coordinateX, coordinateY);

        // Assert
        assertEquals(worldId, kingdom.getWorldId());
        assertEquals(name, kingdom.getName());
        assertEquals(coordinateX, kingdom.getCoordinateX());
        assertEquals(coordinateY, kingdom.getCoordinateY());
    }

    @Test
    void test_create_kingdom_without_coordinates() {
        // Arrange
        Long worldId = 1L;
        String name = "Test Kingdom";

        // Act
        Kingdom kingdom = new Kingdom(worldId, name, null, null);

        // Assert
        assertEquals(worldId, kingdom.getWorldId());
        assertEquals(name, kingdom.getName());
        assertNull(kingdom.getCoordinateX());
        assertNull(kingdom.getCoordinateY());
    }

}