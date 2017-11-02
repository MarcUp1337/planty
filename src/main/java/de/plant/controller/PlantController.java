package de.plant.controller;

import com.pi4j.io.gpio.GpioFactory;
import de.plant.data.Plant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Marcaroni on 01.08.2017.
 */
@Component
public class PlantController {
  final static Logger LOG = Logger.getLogger(PlantController.class);

  private List<Plant> plants = new ArrayList<>();
  private Map<Integer, List<Integer>> humidityHistory = new HashMap<>();

  @Autowired
  private SpiController spiController;

  @PostConstruct
  private void init() {
    spiController.init(GpioFactory.getInstance());
  }

  public List<Plant> getPlants() {
    return plants;
  }

  public void setPlants(List<Plant> plants) {
    this.plants = plants;
  }

  public Plant getPlant(int id) throws PlantNotFoundException {
    if (plants == null || plants.size() < id) {
      throw new PlantNotFoundException();
    }
    return plants.get(id);
  }

  public boolean addPlant(Plant plant) {
    boolean added = plants.add(plant);
    return added;
  }

  public Plant updetePlant(int id, Plant plant) throws PlantNotFoundException {
    Plant updatedPlant = getPlant(id);
    boolean updated = updatedPlant.updete(plant);
    return updatedPlant;
  }

  public int getHumidity(int id) throws PlantNotFoundException {
    return spiController.getHumidity(getPlant(id).getSpiChannel());
  }

  public int[] getHumidity() {
    return spiController.getHumidity();
  }

  public List<Integer> getHumidity(int id, int interval) throws PlantNotFoundException {
    if (!this.humidityHistory.containsKey(id)) {
      throw new PlantNotFoundException();
    }
    return this.humidityHistory.get(id);
  }

  public boolean waterPlant(int id) throws PlantNotFoundException {
    return this.spiController.waterPlant(getPlant(id));
  }
}
