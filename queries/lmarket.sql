use [hw2DB]

CREATE TABLE Local_Market_ZC (
  zipcode INT NOT NULL,
  city VARCHAR(15) NOT NULL,
  PRIMARY KEY (zipcode)
);

CREATE TABLE Local_Market_AZ (
  maddress VARCHAR(30) NOT NULL,
  zipcode INT NOT NULL,
  PRIMARY KEY (maddress),
  FOREIGN KEY (zipcode) REFERENCES Local_Market_ZC (zipcode)
);


CREATE TABLE Local_Market (
  mid INT NOT NULL,
  mname VARCHAR(15) NOT NULL,
  maddress VARCHAR(30) NOT NULL,
  phone_number VARCHAR(20),
  budget INT,
  PRIMARY KEY (mid),
  FOREIGN KEY (maddress) REFERENCES Local_Market_AZ (maddress),

);