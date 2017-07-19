package de.pi.plant;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import de.piapi.gpio.SPIApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin
@Controller
@RequestMapping(value = "/plant")
public class PlantAPI {

	private List<Plant> plants = new ArrayList<>();

	@Autowired
	private SPIApi spiApi;

	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody Plant getPlant(@PathVariable(value = "id") final int id) {

		return plants.get(id);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}/humidity", method = RequestMethod.GET)
	public ResponseEntity<Integer> getHumidity(@PathVariable(value = "id") final int id) {

		ResponseEntity<Integer> response;

		Plant plant = plants.get(0);

		if (plant != null) {

			int humidity = spiApi.getHumidity(plant.getSpiChannel());
			response = ResponseEntity.ok(humidity);

		} else {

			response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

		}

		return response;
	}

	/**
	 * 
	 * @param plant
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody boolean addPlant(@RequestBody Plant plant) {

		return plants.add(plant);
	}

	/**
	 * 
	 * @param id
	 */
	@RequestMapping(value = "/{id}/water", method = RequestMethod.GET)
	public @ResponseBody void waterPlant(@PathVariable(value = "id") final int id) {

		if (this.plants.size() > id) {

			System.out.println("start");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("water");

			this.spiApi.waterPlant(plants.get(id));
		}

	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public @ResponseBody List<Plant> getPlants() {

		return this.plants;
	}

	/**
	 * 
	 */
	@PostConstruct
	private void initPlants() {

		this.plants = new ArrayList<Plant>();

		int spiChannel = 0;
		int waterPumpAddr = 15;
		String name = "Astronaut";

		Plant plant0 = new Plant(name, waterPumpAddr, spiChannel);

		this.plants.add(plant0);

	}

	public void setSpiApi(SPIApi spiApi) {
		this.spiApi = spiApi;
	}
}
