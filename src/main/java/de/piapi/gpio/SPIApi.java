package de.piapi.gpio;

import de.pi.plant.Plant;

public interface SPIApi {

	int getHumidity(int spiChannel);

	boolean waterPlant(Plant plant);
}
