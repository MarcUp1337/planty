package de.plant.controller;

import de.pi.plant.Plant;
import de.pi.plant.PlantObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Marcaroni on 01.08.2017.
 */
@Component
public class PlantController {

  private List<PlantObserver> plantObservers = new ArrayList<>();
  private List<Plant> plants = new ArrayList<>();
  private Map<Integer, List<Integer>> humidityHistory = new HashMap<>();

  @Autowired
  private SpiController spiController;

  public List<Plant> getPlants() {
    return plants;
  }

  public Plant getPlant(int id) throws PlantNotFoundException {
    if (plants == null || plants.size() < id) {
      throw new PlantNotFoundException();
    }
    return plants.get(id);
  }

  public Plant getPlant(String name) throws PlantNotFoundException {
    Plant plant = null;
    for (Plant tmpPlant : plants) {
      if (tmpPlant.getName().equals(name)) {
        plant = tmpPlant;
        break;
      }
    }
    if (plant == null) {
      throw new PlantNotFoundException();
    }
    return plant;
  }

  public boolean addPlant(Plant plant) {
    boolean added = plants.add(plant);
    if (added) {
      for (PlantObserver plantObserver : plantObservers) {
        plantObserver.updatePlants(plants);
      }
    }
    return added;
  }

  public Plant updetePlant(int id, Plant plant) throws PlantNotFoundException {
    Plant updatedPlant = null;
    for (Plant tmpPlant1 : getPlants()) {
      if (tmpPlant1.equals(plant)) {
        tmpPlant1.updete(plant);
        updatedPlant = tmpPlant1;
      }
    }
    if (updatedPlant == null) {
      throw new PlantNotFoundException();
    }
    return updatedPlant;
  }

  public int getHumidity(int id) throws PlantNotFoundException {
    return spiController.getHumidity(getPlant(id).getSpiChannel());
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

  public void addPlantObserver(PlantObserver plantObserver) {
    plantObservers.add(plantObserver);
  }
}
