package de.pi.plant.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by Marcaroni on 11.07.2017.
 */
public class PlantAPITest {

    @Autowired
    PlantAPI plantAPI;

    @Before
    public void setUp() {
        plantAPI = new PlantAPI();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getPlant() {
        plantAPI.getPlant(0);
    }

    @Test
    public void getHumidity() {
        plantAPI.getHumidity(0);
    }

    @Test
    public void addPlant() {
    }

    @Test
    public void waterPlant() {
    }

    @Test
    public void getPlants() {
    }

}