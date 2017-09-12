package de.plant.controller;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

import de.plant.data.Plant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SpiController {
  final static Logger LOG = Logger.getLogger(SpiController.class);

  // @Values are not ready in constructor!
  @Value("${de.plant.controller.spiClkAddr}")
  int spiClkAddr;

  @Value("${de.plant.controller.spiMisoAddr}")
  int spiMisoAddr;

  @Value("${de.plant.controller.spiMosiAddr}")
  int spiMosiAddr;

  @Value("${de.plant.controller.spiCsAddr}")
  int spiCsAddr;

  @Value("${de.plant.controller.sensorPowerAddr}")
  int sensorPowerAddr;

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
  public synchronized int getHumidity(int spiChannel) {
    powerPin.high();
    int volume = readHumidity(spiChannel);
    powerPin.low();
    return volume;
  }

  /**
   * @return
   */
  public synchronized int[] getHumidity() {
    powerPin.high();
    int[] volumes = readHumidity();
    powerPin.low();
    return volumes;
  }

  /**
   *
   * @param plant
   * @return
   */
  public synchronized boolean waterPlant(Plant plant) {
    boolean success = false;
    if (readHumidity(plant.getSpiChannel()) < plant.getNoWaterMark()) {
      LOG.info("create new waterPin [" + plant.getWaterPumpAddr() + "]");
      GpioPinDigitalOutput waterPin = gpioController.provisionDigitalOutputPin(RaspiPin.getPinByAddress(
          plant.getWaterPumpAddr()), "WTR", PinState.LOW);
      if (waterPin != null) {
        waterPin.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);

        waterPin.high();
        try {
          Thread.sleep(plant.getWaterDuration() * 1000);
        } catch (InterruptedException e) {
          LOG.info(e.getMessage());
        }
        waterPin.low();
      } else {
        LOG.warn("waterPin is null");
      }
      gpioController.unprovisionPin(waterPin);
      success = true;
    }
    return success;
  }

  private int readHumidity(int spiChannel) {
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    chipSelectOutput.high();
    clockOutput.low();
    chipSelectOutput.low();

    int adccommand = spiChannel;
    adccommand |= 0x18; // 0x18: 00011000
    adccommand <<= 3;

    // Send 5 bits: 8 - 3. 8 input channels on the MCP3008.
    for (int i = 0; i < 5; i++) //
    {
      if ((adccommand & 0x80) != 0x0) { // 0x80 = 0&10000000
        mosiOutput.high();
      } else {
        mosiOutput.low();
      }
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

    return volume;
  }

  private int[] readHumidity() {
    int[] humidities = new int[8];
    for (int i = 0; i < 8; i++) {
      humidities[i] = readHumidity(i);
    }
    return humidities;
  }

  public void init(GpioController gpioController){
    this.gpioController = gpioController;
    gpioController.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);

    LOG.info("create powerPin [" + sensorPowerAddr + "]");
    powerPin = gpioController.provisionDigitalOutputPin(RaspiPin.getPinByAddress(new Integer(sensorPowerAddr)), "POW", PinState.LOW);
    if (powerPin == null) {
      LOG.warn("powerPin is null");
    }

    LOG.info("create mosiOutput [" + spiMosiAddr + "]");
    mosiOutput = gpioController.provisionDigitalOutputPin(RaspiPin.getPinByAddress(new Integer(spiMosiAddr)), "MOSI", PinState.LOW);
    if (mosiOutput == null) {
      LOG.warn("mosiOutput is null");
    }

    LOG.info("create clockOutput [" + spiClkAddr + "]");
    clockOutput = gpioController.provisionDigitalOutputPin(RaspiPin.getPinByAddress(new Integer(spiClkAddr)), "CLK", PinState.LOW);
    if (clockOutput == null) {
      LOG.warn("clockOutput is null");
    }

    LOG.info("create chipSelectOutput [" + spiCsAddr + "]");
    chipSelectOutput = gpioController.provisionDigitalOutputPin(RaspiPin.getPinByAddress(new Integer(spiCsAddr)), "CS", PinState.LOW);
    if (chipSelectOutput == null) {
      LOG.warn("chipSelectOutput is null");
    }

    LOG.info("create misoInput [" + spiMisoAddr + "]");
    misoInput = gpioController.provisionDigitalInputPin(RaspiPin.getPinByAddress(new Integer(spiMisoAddr)), "MISO");
    if (misoInput == null) {
      LOG.warn("misoInput is null");
    }
  }
}
