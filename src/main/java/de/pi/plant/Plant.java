package de.pi.plant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Plant {
  private String name;
  private int waterPumpAddr;
  private int spiChannel;
  private int noWaterMark;
  private int waterDuration;

  @JsonCreator
  public Plant(@JsonProperty("name") String name, @JsonProperty("waterPumpAddr") int waterPumpAddr, @JsonProperty("spiChannel") int spiChannel,
               @JsonProperty("noWaterMark") int noWaterMark, @JsonProperty("waterDuration") int waterDuration) {
    this.waterPumpAddr = waterPumpAddr;
    this.spiChannel = spiChannel;
    this.name = name;
    this.noWaterMark = noWaterMark;
    this.waterDuration = waterDuration;
  }

  public int getWaterPumpAddr() {
    return waterPumpAddr;
  }

  public String getName() {
    return name;
  }

  public int getSpiChannel() {
    return spiChannel;
  }

  public int getNoWaterMark() {
    return noWaterMark;
  }

  public int getWaterDuration() {
    return waterDuration;
  }

  public void setWaterPumpAddr(int waterPumpAddr) {
    this.waterPumpAddr = waterPumpAddr;
  }

  public void setSpiChannel(int spiChannel) {
    this.spiChannel = spiChannel;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setNoWaterMark(int noWaterMark) {
    this.noWaterMark = noWaterMark;
  }

  public void setWaterDuration(int waterDuration) {
    this.waterDuration = waterDuration;
  }

  public boolean updete(Plant plant) {
    setWaterPumpAddr(plant.getWaterPumpAddr());
    setSpiChannel(plant.getSpiChannel());
    setName(plant.getName());
    setNoWaterMark(plant.getNoWaterMark());
    setWaterDuration(plant.getWaterDuration());

    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Plant plant = (Plant) o;

    if (waterPumpAddr != plant.waterPumpAddr) return false;
    if (spiChannel != plant.spiChannel) return false;
    if (noWaterMark != plant.noWaterMark) return false;
    if (waterDuration != plant.waterDuration) return false;
    return name != null ? name.equals(plant.name) : plant.name == null;
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + waterPumpAddr;
    result = 31 * result + spiChannel;
    result = 31 * result + noWaterMark;
    result = 31 * result + waterDuration;
    return result;
  }

  @Override
  public String toString() {
    return "Plant{" +
        "name='" + name + '\'' +
        ", waterPumpAddr=" + waterPumpAddr +
        ", spiChannel=" + spiChannel +
        ", noWaterMark=" + noWaterMark +
        ", waterDuration=" + waterDuration +
        '}';
  }
}
