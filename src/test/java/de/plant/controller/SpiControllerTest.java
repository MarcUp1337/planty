package de.plant.controller;

import com.pi4j.io.gpio.GpioController;
import de.pi.plant.Plant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = {SpiController.class})
public class SpiControllerTest {
  @Autowired
  SpiController spiController;

  @Mock
  GpioController gpioController;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void getHumidity() throws Exception {
    Assert.assertEquals(new int[]{1,2}, spiController.getHumidity());
  }

  @Test
  public void getHumidity1() throws Exception {
    Assert.assertEquals(new int[]{1,2}, spiController.getHumidity(0));
  }

  @Test
  public void waterPlant() throws Exception {
    Plant plant = new Plant("Test", 0, 0, 70, 15);
    Assert.assertTrue(spiController.waterPlant(plant));
  }
}