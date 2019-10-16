use [hw2DB]

CREATE TABLE Farmer_ZC (
zipcode INT NOT NULL,
city VARCHAR(15) NOT NULL,
PRIMARY KEY (zipcode)
);

CREATE TABLE Farmer_AZ (
faddress VARCHAR(30) NOT NULL,
zipcode INT NOT NULL,
PRIMARY KEY(faddress),
FOREIGN KEY (zipcode) REFERENCES FARMER_ZC(zipcode)
);

CREATE TABLE Farmer (
  fid INT NOT NULL,
  fname VARCHAR(15) NOT NULL,
  last_name VARCHAR(15) NOT NULL,
  faddress VARCHAR(30) NOT NULL,
  PRIMARY KEY (fid),
  FOREIGN KEY (faddress) REFERENCES Farmer_AZ (faddress),
);

CREATE TABLE Farmer_email (
fid INT NOT NULL,
email VARCHAR(30),
PRIMARY KEY(email),
FOREIGN KEY (fid) REFERENCES FARMER (fid)
);

CREATE TABLE Farmer_phone (
fid INT NOT NULL,
phone_number VARCHAR(20),
PRIMARY KEY(phone_number),
FOREIGN KEY (fid) REFERENCES FARMER (fid),
);