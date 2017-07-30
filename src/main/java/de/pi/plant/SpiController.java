package de.pi.plant;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

import org.springframework.stereotype.Component;

@Component
public class SpiController {

  private final static int spiClkAddr = 14;
  private final static int spiMisoAddr = 13;
  private final static int spiMosiAddr = 12;
  private final static int spiCsAddr = 10;
  private final static int sensorPowerAddr = 27;

  private GpioController gpioController;

  private GpioPinDigitalOutput powerPin;
  private GpioPinDigitalOutput mosiOutput;
  private GpioPinDigitalOutput clockOutput;
  private GpioPinDigitalOutput chipSelectOutput;
  private GpioPinDigitalInput misoInput;

  /**
   *
   * @param spiChannel
   * @return
   */
  public int getHumidity(int spiChannel) {
    startController();
    int volume = readHumidity(spiChannel);
    stopController();
    return volume;
  }

  /**
   *
   * @param plant
   * @return
   */
  public boolean waterPlant(Plant plant) {
    startController();

    int humidity = readHumidity(plant.getSpiChannel());
    boolean success = false;
    if (humidity > 70) {
      GpioPinDigitalOutput waterPin = gpioController.provisionDigitalOutputPin(RaspiPin.getPinByAddress(
          plant.getWaterPumpAddr()), "WTR", PinState.HIGH);
      waterPin.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
      waterPin.low();
      try {
        Thread.sleep(4000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      waterPin.high();
      success = true;
    }

    stopController();
    return success;
  }

  private void startController() {
    gpioController = GpioFactory.getInstance();
    gpioController.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
    powerPin = gpioController.provisionDigitalOutputPin(RaspiPin.getPinByAddress(sensorPowerAddr), "POW", PinState.LOW);
    mosiOutput = gpioController.provisionDigitalOutputPin(RaspiPin.getPinByAddress(spiMosiAddr), "MOSI", PinState.LOW);
    clockOutput = gpioController.provisionDigitalOutputPin(RaspiPin.getPinByAddress(spiClkAddr), "CLK", PinState.LOW);
    chipSelectOutput = gpioController.provisionDigitalOutputPin(RaspiPin.getPinByAddress(spiCsAddr), "CS", PinState.LOW);
    misoInput = gpioController.provisionDigitalInputPin(RaspiPin.getPinByAddress(spiMisoAddr), "MISO");
  }

  private void stopController() {
    gpioController.shutdown();
  }

  private void startHumidityPins() {
    powerPin.high();
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    chipSelectOutput.high();
    clockOutput.low();
    chipSelectOutput.low();
  }

  private int readHumidity(int spiChannel) {
    startHumidityPins();

    int adccommand = spiChannel;
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
}
