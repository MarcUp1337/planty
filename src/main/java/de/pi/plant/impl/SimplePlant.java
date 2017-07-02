package de.pi.plant.impl;

import de.pi.plant.Plant;

public class SimplePlant implements Plant {

	private int waterPumpAddr;
	private int spiChannel;
	private String name;

	public SimplePlant(String name, int waterPumpAddr, int spiChannel) {

		super();

		this.waterPumpAddr = waterPumpAddr;
		this.spiChannel = spiChannel;
		this.name = name;
	}

	@Override
	public int getWaterPumpAddr() {
		return waterPumpAddr;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getSpiChannel() {
		return spiChannel;
	}
}
