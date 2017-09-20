USE planty;

GRANT ALL PRIVILEGES ON *.* TO 'planty'@'%' IDENTIFIED BY 'planty' REQUIRE SSL;

CREATE TABLE plant
(
  id INT PRIMARY KEY AUTO_INCREMENT,
  name CHAR(255),
  water_pin INT,
  sensor_pin INT,
  water_duration INT,
  no_water_mark INT
);
CREATE UNIQUE INDEX plant_id_uindex ON plant (id);

CREATE TABLE hummidity
(
  plant INT,
  hummidity INT,
  timestamp DATETIME NULL,
  CONSTRAINT plant___fk FOREIGN KEY (plant) REFERENCES plant (id)
);

