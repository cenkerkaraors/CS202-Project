use [hw2DB]

CREATE TABLE Product_AM (
  altitude_level INT NOT NULL,
  min_temp INT NOT NULL,
  PRIMARY KEY (altitude_level),
);
CREATE TABLE Product_PH (
  pid INT NOT NULL,
  plant_date DATE NOT NULL,
  harvest_date DATE NOT NULL,
  PRIMARY KEY (pid),
);

CREATE TABLE Product (
  pid INT NOT NULL,
  pname VARCHAR(15) NOT NULL,
  plant_date DATE NOT NULL,
  hardness_level VARCHAR(3) NOT NULL,
  altitude_level INT NOT NULL,
  PRIMARY KEY (pid),
  FOREIGN KEY (pid) REFERENCES Product_PH (pid),
  FOREIGN KEY (altitude_level) REFERENCES Product_AM (altitude_level)
);