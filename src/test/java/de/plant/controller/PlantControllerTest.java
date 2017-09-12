package de.plant.controller;

import de.plant.data.Plant;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by Marcaroni on 02.08.2017.
 */
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {PlantController.class, SpiController.class})
public class PlantControllerTest {

  @Autowired
  @InjectMocks
  PlantController plantController;

  @Mock
  SpiController spiController;

  List<Plant> plants;
  Plant plantA;
  Plant plantB;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    plantA = new Plant("Plant A", 0, 0, 70, 15);
    plantB = new Plant("Plant B", 2, 4, 70, 15);

    plants = new ArrayList<>();
    plants.add(plantA);
    plants.add(plantB);

    plantController.setPlants(plants);
  }

  @After
  public void tearDown() {
    plantController.setPlants(null);
  }

  @Test
  public void getPlants() throws Exception {
    Assert.assertEquals(2, plantController.getPlants().size());
  }

  @Test
  public void getPlant_Valid() throws PlantNotFoundException {
    Assert.assertEquals(2, plantController.getPlants().size());
    Assert.assertEquals("Plant{name='Plant A', waterPumpAddr=0, spiChannel=0, noWaterMark=70, waterDuration=15}", plantController.getPlant(0).toString());
    Assert.assertEquals("Plant{name='Plant B', waterPumpAddr=2, spiChannel=4, noWaterMark=70, waterDuration=15}", plantController.getPlant(1).toString());
  }

  @Test(expected = PlantNotFoundException.class)
  public void getPlant_Invalid() throws PlantNotFoundException {
    plantController.getPlant(3);
  }

  @Test
  public void addPlant() throws PlantNotFoundException {
    Plant plantC = new Plant("Plant C", 3, 5, 70, 15);
    plantController.addPlant(plantC);
    Assert.assertEquals("Plant{name='Plant C', waterPumpAddr=3, spiChannel=5, noWaterMark=70, waterDuration=15}", plantController.getPlant(2).toString());
  }

  @Test
  public void updetePlant() throws PlantNotFoundException {
    Plant plantBPpdate = new Plant("Plant B", 1, 2, 70, 15);
    Plant plantBPpdated = plantController.updetePlant(0, plantBPpdate);
    Assert.assertEquals(plantBPpdate, plantBPpdated);
  }

  @Test
  public void getHumidity() throws PlantNotFoundException {
    when(spiController.getHumidity(0)).thenReturn(50);
    Assert.assertEquals(50, plantController.getHumidity(0));
  }

  @Test
  public void getHumidities() throws PlantNotFoundException {
    when(spiController.getHumidity()).thenReturn(new int[]{50, 50});
    Assert.assertArrayEquals(new int[]{50, 50}, plantController.getHumidity());
  }

  @Test
  public void waterPlantSuccess() throws PlantNotFoundException {
    when(spiController.waterPlant(Mockito.any())).thenReturn(true);
    Assert.assertTrue(plantController.waterPlant(0));
  }

  @Test
  public void waterPlantFail() throws PlantNotFoundException {
    when(spiController.waterPlant(Mockito.any())).thenReturn(false);
    Assert.assertFalse(plantController.waterPlant(0));
  }

}