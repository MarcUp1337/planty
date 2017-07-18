package de.pi.plant;

public class Plant {

  private int waterPumpAddr;
  private int spiChannel;
  private String name;

  public Plant() {
  }

  public Plant(String name, int waterPumpAddr, int spiChannel) {

    this.waterPumpAddr = waterPumpAddr;
    this.spiChannel = spiChannel;
    this.name = name;
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

  public void setWaterPumpAddr(int waterPumpAddr) {
    this.waterPumpAddr = waterPumpAddr;
  }

  public void setSpiChannel(int spiChannel) {
    this.spiChannel = spiChannel;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Plant that = (Plant) o;

    if (waterPumpAddr != that.waterPumpAddr) return false;
    if (spiChannel != that.spiChannel) return false;
    return name != null ? name.equals(that.name) : that.name == null;
  }

  @Override
  public int hashCode() {
    int result = waterPumpAddr;
    result = 31 * result + spiChannel;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }
}
