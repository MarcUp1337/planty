package de.plant.api;

import java.util.List;

import javax.annotation.PostConstruct;

import de.plant.data.Plant;
import de.plant.controller.PlantController;
import de.plant.controller.PlantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Controller
@RequestMapping(value = "/plant")
public class PlantAPI {

  @Autowired
  PlantController plantController;

  /**
   * @return
   */
  @GetMapping(value = "/all")
  public @ResponseBody
  List<Plant> getPlants() {
    return plantController.getPlants();
  }

  /**
   * @param id
   * @return
   */
  @GetMapping(value = "/{id}")
  public @ResponseBody
  ResponseEntity<Plant> getPlant(@PathVariable(value = "id") final int id) {
    ResponseEntity responseEntity;
    try {
      responseEntity = ResponseEntity.ok(plantController.getPlant(id));
    } catch (PlantNotFoundException e) {
      responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return responseEntity;
  }

  /**
   * @param plant
   * @return
   */
  @PutMapping
  public @ResponseBody
  ResponseEntity addPlant(@RequestBody Plant plant) {
    ResponseEntity responseEntity;
    if (plantController.addPlant(plant)) {
      responseEntity = ResponseEntity.ok("Plant added.");
    } else {
      responseEntity = new ResponseEntity<>("Plant not added.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return responseEntity;
  }

  /**
   * @param id
   * @return
   */
  @PostMapping(value = "/{id}")
  public @ResponseBody
  ResponseEntity<Plant> updatePlant(@PathVariable(value = "id") final int id, @RequestBody Plant plant) {
    ResponseEntity responseEntity;
    try {
      responseEntity = ResponseEntity.ok(plantController.updetePlant(id, plant));
    } catch (PlantNotFoundException e) {
      responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return responseEntity;
  }

  /**
   * @param id
   * @return
   */
  @GetMapping(value = "/{id}/humidity")
  public ResponseEntity<Integer> getHumidity(@PathVariable(value = "id") final int id) {
    ResponseEntity responseEntity;
    try {
      responseEntity = ResponseEntity.ok(plantController.getHumidity(id));
    } catch (PlantNotFoundException e) {
      responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return responseEntity;
  }

  /**
   * @param id
   * @return
   */
  @GetMapping(value = "/{id}/humidity/{interval}")
  public ResponseEntity<Integer> getHumidity(@PathVariable(value = "id") final int id, @PathVariable(value = "id") final int interval) {
    ResponseEntity responseEntity;
    try {
      responseEntity = ResponseEntity.ok(plantController.getHumidity(id, interval));
    } catch (PlantNotFoundException e) {
      responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return responseEntity;
  }

  /**
   * @param id
   */
  @GetMapping(value = "/{id}/water")
  public ResponseEntity<Boolean> waterPlant(@PathVariable(value = "id") final int id) {
    ResponseEntity responseEntity;
    try {
      responseEntity = ResponseEntity.ok(plantController.waterPlant(id));
    } catch (PlantNotFoundException e) {
      responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return responseEntity;
  }

  /**
   *
   */
  @PostConstruct
  private void initPlants() {

    int spiChannel = 0;
    int waterPumpAddr = 4;
    int noWaterMark = 70;
    int waterDuration = 15;
    String name = "Astronaut";

    Plant plant0 = new Plant(name, waterPumpAddr, spiChannel, noWaterMark, waterDuration);

    plantController.addPlant(plant0);
  }
}
