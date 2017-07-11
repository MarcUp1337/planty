package de.pi.plant.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Spy;


/**
 * Created by Marcaroni on 11.07.2017.
 */
class PlantAPITest {

    @Spy
    PlantAPI plantAPI;

    @Before
    void setUp() {
    }

    @After
    void tearDown() {
    }

    @Test
    void getPlant() {
        plantAPI.getPlant(0);
    }

    @Test
    void getHumidity() {
    }

    @Test
    void addPlant() {
    }

    @Test
    void waterPlant() {
    }

    @Test
    void getPlants() {
    }

}