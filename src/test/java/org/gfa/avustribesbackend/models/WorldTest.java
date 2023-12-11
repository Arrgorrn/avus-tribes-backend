package org.gfa.avustribesbackend.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorldTest {

    @Test
    void testGeneratorOfName() {
        World world = new World();
        String name = world.generatorOfName();
        assertNotNull(name);
    }

    @Test
    void testOfDefaultConstructor(){
        World world = new World();
        assertNotNull(world, world.getName());
    }

    @Test
    void testOfConstructorWithParam(){
        World world = new World("Erebor");
        assertEquals("Erebor", world.getName());
    }
}