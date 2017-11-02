package de.plant.controller;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.trigger.GpioTrigger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = {SpiController.class})
public class SpiControllerTest {

  @Autowired
  @InjectMocks
  SpiController spiController;

  @Mock
  GpioController gpioController;

  @Mock
  Pin pin;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    ReflectionTestUtils.setField(spiController, "spiClkAddr", 14);
    ReflectionTestUtils.setField(spiController, "spiMisoAddr", 13);
    ReflectionTestUtils.setField(spiController, "spiMosiAddr", 12);
    ReflectionTestUtils.setField(spiController, "spiCsAddr", 10);
    ReflectionTestUtils.setField(spiController, "sensorPowerAddr", 27);

    GpioPinDigitalOutputMock powerPin = new GpioPinDigitalOutputMock();
    GpioPinDigitalOutputMock mosiOutput = new GpioPinDigitalOutputMock();
    GpioPinDigitalOutputMock clockOutput = new GpioPinDigitalOutputMock();
    GpioPinDigitalOutputMock chipSelectOutput = new GpioPinDigitalOutputMock();
    GpioPinDigitalInputMock misoInput = new GpioPinDigitalInputMock();

    when(gpioController.provisionDigitalOutputPin(pin, "POW", PinState.LOW)).thenReturn(powerPin);
    when(gpioController.provisionDigitalOutputPin(pin, "MOSI", PinState.LOW)).thenReturn(mosiOutput);
    when(gpioController.provisionDigitalOutputPin(pin, "CLK", PinState.LOW)).thenReturn(clockOutput);
    when(gpioController.provisionDigitalOutputPin(pin, "CS", PinState.LOW)).thenReturn(chipSelectOutput);
    when(gpioController.provisionDigitalInputPin(pin, "MISO")).thenReturn(misoInput);

    spiController.init(gpioController);
  }

  @Test
  public void initializePorts() {
    Assert.assertEquals(14, spiController.spiClkAddr);
    Assert.assertEquals(13, spiController.spiMisoAddr);
    Assert.assertEquals(12, spiController.spiMosiAddr);
    Assert.assertEquals(10, spiController.spiCsAddr);
    Assert.assertEquals(27, spiController.sensorPowerAddr);

  }

  @Test
  public void getHumidity() throws Exception {
//    spiController.getHumidity();
//    Assert.assertEquals(new int[]{1, 2}, spiController.getHumidity());
  }

  @Test
  public void getHumidity1() throws Exception {
//    Assert.assertEquals(new int[]{1, 2}, spiController.getHumidity(0));
  }

  @Test
  public void waterPlant() throws Exception {
//    Plant plant = new Plant("Test", 0, 0, 70, 15);
//    Assert.assertTrue(spiController.waterPlant(plant));
  }

  private class GpioPinDigitalOutputMock implements GpioPinDigitalOutput {

    @Override
    public void high() {

    }

    @Override
    public void low() {

    }

    @Override
    public void toggle() {

    }

    @Override
    public Future<?> blink(long delay) {
      return null;
    }

    @Override
    public Future<?> blink(long delay, PinState blinkState) {
      return null;
    }

    @Override
    public Future<?> blink(long delay, long duration) {
      return null;
    }

    @Override
    public Future<?> blink(long delay, long duration, PinState blinkState) {
      return null;
    }

    @Override
    public Future<?> pulse(long duration) {
      return null;
    }

    @Override
    public Future<?> pulse(long duration, Callable<Void> callback) {
      return null;
    }

    @Override
    public Future<?> pulse(long duration, boolean blocking) {
      return null;
    }

    @Override
    public Future<?> pulse(long duration, boolean blocking, Callable<Void> callback) {
      return null;
    }

    @Override
    public Future<?> pulse(long duration, PinState pulseState) {
      return null;
    }

    @Override
    public Future<?> pulse(long duration, PinState pulseState, Callable<Void> callback) {
      return null;
    }

    @Override
    public Future<?> pulse(long duration, PinState pulseState, boolean blocking) {
      return null;
    }

    @Override
    public Future<?> pulse(long duration, PinState pulseState, boolean blocking, Callable<Void> callback) {
      return null;
    }

    @Override
    public void setState(PinState state) {

    }

    @Override
    public void setState(boolean state) {

    }

    @Override
    public boolean isHigh() {
      return false;
    }

    @Override
    public boolean isLow() {
      return false;
    }

    @Override
    public PinState getState() {
      return null;
    }

    @Override
    public boolean isState(PinState state) {
      return false;
    }

    @Override
    public GpioProvider getProvider() {
      return null;
    }

    @Override
    public Pin getPin() {
      return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getName() {
      return null;
    }

    @Override
    public void setTag(Object tag) {

    }

    @Override
    public Object getTag() {
      return null;
    }

    @Override
    public void setProperty(String key, String value) {

    }

    @Override
    public boolean hasProperty(String key) {
      return false;
    }

    @Override
    public String getProperty(String key) {
      return null;
    }

    @Override
    public String getProperty(String key, String defaultValue) {
      return null;
    }

    @Override
    public Map<String, String> getProperties() {
      return null;
    }

    @Override
    public void removeProperty(String key) {

    }

    @Override
    public void clearProperties() {

    }

    @Override
    public void export(PinMode mode) {

    }

    @Override
    public void export(PinMode mode, PinState defaultState) {

    }

    @Override
    public void unexport() {

    }

    @Override
    public boolean isExported() {
      return false;
    }

    @Override
    public void setMode(PinMode mode) {

    }

    @Override
    public PinMode getMode() {
      return null;
    }

    @Override
    public boolean isMode(PinMode mode) {
      return false;
    }

    @Override
    public void setPullResistance(PinPullResistance resistance) {

    }

    @Override
    public PinPullResistance getPullResistance() {
      return null;
    }

    @Override
    public boolean isPullResistance(PinPullResistance resistance) {
      return false;
    }

    @Override
    public Collection<GpioPinListener> getListeners() {
      return null;
    }

    @Override
    public void addListener(GpioPinListener... listener) {

    }

    @Override
    public void addListener(List<? extends GpioPinListener> listeners) {

    }

    @Override
    public boolean hasListener(GpioPinListener... listener) {
      return false;
    }

    @Override
    public void removeListener(GpioPinListener... listener) {

    }

    @Override
    public void removeListener(List<? extends GpioPinListener> listeners) {

    }

    @Override
    public void removeAllListeners() {

    }

    @Override
    public GpioPinShutdown getShutdownOptions() {
      return null;
    }

    @Override
    public void setShutdownOptions(GpioPinShutdown options) {

    }

    @Override
    public void setShutdownOptions(Boolean unexport) {

    }

    @Override
    public void setShutdownOptions(Boolean unexport, PinState state) {

    }

    @Override
    public void setShutdownOptions(Boolean unexport, PinState state, PinPullResistance resistance) {

    }

    @Override
    public void setShutdownOptions(Boolean unexport, PinState state, PinPullResistance resistance, PinMode mode) {

    }
  }

  private class GpioPinDigitalInputMock implements GpioPinDigitalInput {

    @Override
    public boolean hasDebounce(PinState state) {
      return false;
    }

    @Override
    public int getDebounce(PinState state) {
      return 0;
    }

    @Override
    public void setDebounce(int debounce) {

    }

    @Override
    public void setDebounce(int debounce, PinState... state) {

    }

    @Override
    public boolean isHigh() {
      return false;
    }

    @Override
    public boolean isLow() {
      return false;
    }

    @Override
    public PinState getState() {
      return null;
    }

    @Override
    public boolean isState(PinState state) {
      return false;
    }

    @Override
    public Collection<GpioTrigger> getTriggers() {
      return null;
    }

    @Override
    public void addTrigger(GpioTrigger... trigger) {

    }

    @Override
    public void addTrigger(List<? extends GpioTrigger> triggers) {

    }

    @Override
    public void removeTrigger(GpioTrigger... trigger) {

    }

    @Override
    public void removeTrigger(List<? extends GpioTrigger> triggers) {

    }

    @Override
    public void removeAllTriggers() {

    }

    @Override
    public GpioProvider getProvider() {
      return null;
    }

    @Override
    public Pin getPin() {
      return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getName() {
      return null;
    }

    @Override
    public void setTag(Object tag) {

    }

    @Override
    public Object getTag() {
      return null;
    }

    @Override
    public void setProperty(String key, String value) {

    }

    @Override
    public boolean hasProperty(String key) {
      return false;
    }

    @Override
    public String getProperty(String key) {
      return null;
    }

    @Override
    public String getProperty(String key, String defaultValue) {
      return null;
    }

    @Override
    public Map<String, String> getProperties() {
      return null;
    }

    @Override
    public void removeProperty(String key) {

    }

    @Override
    public void clearProperties() {

    }

    @Override
    public void export(PinMode mode) {

    }

    @Override
    public void export(PinMode mode, PinState defaultState) {

    }

    @Override
    public void unexport() {

    }

    @Override
    public boolean isExported() {
      return false;
    }

    @Override
    public void setMode(PinMode mode) {

    }

    @Override
    public PinMode getMode() {
      return null;
    }

    @Override
    public boolean isMode(PinMode mode) {
      return false;
    }

    @Override
    public void setPullResistance(PinPullResistance resistance) {

    }

    @Override
    public PinPullResistance getPullResistance() {
      return null;
    }

    @Override
    public boolean isPullResistance(PinPullResistance resistance) {
      return false;
    }

    @Override
    public Collection<GpioPinListener> getListeners() {
      return null;
    }

    @Override
    public void addListener(GpioPinListener... listener) {

    }

    @Override
    public void addListener(List<? extends GpioPinListener> listeners) {

    }

    @Override
    public boolean hasListener(GpioPinListener... listener) {
      return false;
    }

    @Override
    public void removeListener(GpioPinListener... listener) {

    }

    @Override
    public void removeListener(List<? extends GpioPinListener> listeners) {

    }

    @Override
    public void removeAllListeners() {

    }

    @Override
    public GpioPinShutdown getShutdownOptions() {
      return null;
    }

    @Override
    public void setShutdownOptions(GpioPinShutdown options) {

    }

    @Override
    public void setShutdownOptions(Boolean unexport) {

    }

    @Override
    public void setShutdownOptions(Boolean unexport, PinState state) {

    }

    @Override
    public void setShutdownOptions(Boolean unexport, PinState state, PinPullResistance resistance) {

    }

    @Override
    public void setShutdownOptions(Boolean unexport, PinState state, PinPullResistance resistance, PinMode mode) {

    }
  }
}