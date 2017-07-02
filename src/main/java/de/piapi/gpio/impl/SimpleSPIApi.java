package de.piapi.gpio.impl;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

import de.pi.plant.Plant;
import de.piapi.gpio.SPIApi;

public class SimpleSPIApi implements SPIApi {

	GpioPinDigitalOutput mosiOutput;
	GpioPinDigitalOutput clockOutput;
	GpioPinDigitalOutput chipSelectOutput;
	GpioPinDigitalInput misoInput;
	GpioPinDigitalOutput powerPin;
	GpioPinDigitalOutput waterPin;

	public SimpleSPIApi() {

		int spiClkAddr = 14;
		int spiMisoAddr = 13;
		int spiMosiAddr = 12;
		int spiCsAddr = 10;
		int sensorPowerAddr = 27;
		int waterPinAddr = 5;
		int startedPinAddr = 25;

		GpioController gpio = GpioFactory.getInstance();

		mosiOutput = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(spiMosiAddr), "MOSI", PinState.LOW);
		clockOutput = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(spiClkAddr), "CLK", PinState.LOW);
		chipSelectOutput = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(spiCsAddr), "CS", PinState.LOW);
		misoInput = gpio.provisionDigitalInputPin(RaspiPin.getPinByAddress(spiMisoAddr), "MISO");
		powerPin = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(sensorPowerAddr), "POW", PinState.LOW);
		waterPin = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(waterPinAddr), "WTR", PinState.HIGH);

		GpioPinDigitalOutput startedPin = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(startedPinAddr),
				"START", PinState.LOW);

		startedPin.blink(500, 5000);

		gpio.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
	}

	@Override
	public int getHumidity(int spiChannel) {

		powerPin.high();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		chipSelectOutput.high();
		clockOutput.low();
		chipSelectOutput.low();

		int adccommand = 0;
		adccommand |= 0x18; // 0x18: 00011000
		adccommand <<= 3;

		// Send 5 bits: 8 - 3. 8 input channels on the MCP3008.
		for (int i = 0; i < 5; i++) //
		{
			if ((adccommand & 0x80) != 0x0) // 0x80 = 0&10000000
				mosiOutput.high();
			else
				mosiOutput.low();
			adccommand <<= 1;
			clockOutput.high();
			clockOutput.low();
		}

		int adcOut = 0;
		for (int i = 0; i < 12; i++) // Read in one empty bit, one null bit and
										// 10 ADC bits
		{
			clockOutput.high();
			clockOutput.low();
			adcOut <<= 1;

			if (misoInput.isHigh()) {
				adcOut |= 0x1;
			}
		}

		chipSelectOutput.high();
		adcOut >>= 1; // Drop first bit
		int volume = (int) (adcOut / 10.23);

		powerPin.low();

		return volume;
	}

	@Override
	public boolean waterPlant(Plant plant) {

		int humidity = this.getHumidity(plant.getSpiChannel());
		boolean success = false;

		if (humidity > 70) {
			waterPin.low();

			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			waterPin.high();
			success = true;
		}

		return success;
	}

}
