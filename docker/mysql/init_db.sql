USE planty;

CREATE TABLE plants
(
  id INT PRIMARY KEY AUTO_INCREMENT,
  name CHAR(255),
  water_pin INT,
  sensor_pin INT,
  water_duration INT,
  no_water_mark INT
);
CREATE UNIQUE INDEX plants_id_uindex ON plants (id);

CREATE TABLE hummidity
(
  plant INT,
  hummidity INT,
  timestamp DATETIME NULL,
  CONSTRAINT plant___fk FOREIGN KEY (plant) REFERENCES plants (id)
);

