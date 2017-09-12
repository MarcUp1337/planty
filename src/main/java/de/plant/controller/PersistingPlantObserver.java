package de.plant.controller;

import de.plant.data.Plant;
import de.plant.data.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by Marcaroni on 21.07.2017.
 */
@Component
public class PersistingPlantObserver implements PlantObserver {

  @Autowired
  PlantController plantController;

  @Autowired
  PlantRepository plantRepository;

  @PostConstruct
  private void register() {
    plantController.addPlantObserver(this);
  }

  @Override
  public void addPlant(Plant plant) {
    plantRepository.save(plant);
  }

  @Override
  public void updatePlant(Plant plant) {
    plantRepository.save(plant);
  }

}
