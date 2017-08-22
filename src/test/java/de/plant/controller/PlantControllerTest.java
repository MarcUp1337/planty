package de.plant.controller;

import de.pi.plant.Plant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;

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

    plants = Arrays.asList(plantA, plantB);

    plantController.addPlant(plantA);
    plantController.addPlant(plantB);
  }

  @Test
  public void getPlants() throws Exception {
    Assert.assertEquals(2, plantController.getPlants().size());
  }

  @Test
  public void getPlant() throws Exception {
  }

  @Test
  public void addPlant() throws Exception {
  }

  @Test
  public void updetePlant() throws Exception {
  }

  @Test
  public void getHumidity() throws Exception {
  }

  @Test
  public void getHumidity1() throws Exception {
  }

  @Test
  public void waterPlant() throws Exception {
  }

}