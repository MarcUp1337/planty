package de.plant.controller;

import de.plant.data.Plant;

/**
 * Created by Marcaroni on 23.07.2017.
 */
public interface PlantObserver {

  void addPlant(Plant plant);

  void updatePlant(Plant plant);
}
