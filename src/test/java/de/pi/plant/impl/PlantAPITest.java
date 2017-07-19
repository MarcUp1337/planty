package de.pi.plant.impl;

import de.pi.plant.Plant;
import de.pi.plant.PlantAPI;
import de.piapi.gpio.SPIApi;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Created by Marcaroni on 11.07.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SPIApi.class})
public class PlantAPITest {

  PlantAPI plantAPI;

  @Mock
  SPIApi spiApi;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    plantAPI = new PlantAPI();
    plantAPI.setSpiApi(spiApi);
    Plant plantA = new Plant("PlantA", 0, 0);
    plantAPI.addPlant(plantA);
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
    Mockito.when(spiApi.getHumidity(Mockito.anyInt())).thenReturn( 40);
    int actual = plantAPI.getHumidity(0).getBody();
    Assert.assertEquals(40, actual);
  }

  @Test
  public void addPlant() {
    Plant plantB = new Plant("PlantB", 1, 1);
    plantAPI.addPlant(plantB);
    Assert.assertTrue(plantAPI.getPlants().contains(plantB));
  }

  @Test
  public void waterPlant() {
  }

  @Test
  public void getPlants() {
  }

}