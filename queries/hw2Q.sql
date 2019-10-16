
use [hw2DB]
CREATE TABLE Farmer_ZC (
zipcode INT NOT NULL PRIMARY KEY WITH (IGNORE_DUP_KEY = ON),--implement later
city VARCHAR(15) NOT NULL,
);

CREATE TABLE Farmer_AZ (
faddress VARCHAR(30) NOT NULL PRIMARY KEY WITH (IGNORE_DUP_KEY = ON),
zipcode INT NOT NULL,
FOREIGN KEY (zipcode) REFERENCES FARMER_ZC(zipcode)
);

CREATE TABLE Farmer (
  fid INT IDENTITY(1,1) NOT NULL,
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
CREATE TABLE Product_AM (
  altitude_level INT NOT NULL PRIMARY KEY WITH (IGNORE_DUP_KEY = ON),
  min_temp INT NOT NULL,
);
CREATE TABLE Product_PH (
  plant_date INT NOT NULL PRIMARY KEY WITH (IGNORE_DUP_KEY = ON),
  harvest_date INT NOT NULL,
);

CREATE TABLE Product (
  pid INT IDENTITY(1,1) NOT NULL,
  pname VARCHAR(15) NOT NULL,
  plant_date INT NOT NULL,
  hardness_level VARCHAR(3) NOT NULL,
  altitude_level INT NOT NULL,
  PRIMARY KEY (pid),
  FOREIGN KEY (plant_date) REFERENCES Product_PH (plant_date),
  FOREIGN KEY (altitude_level) REFERENCES Product_AM (altitude_level)
);
CREATE TABLE Local_Market_ZC (
  zipcode INT NOT NULL PRIMARY KEY WITH (IGNORE_DUP_KEY = ON),
  city VARCHAR(15) NOT NULL,
);

CREATE TABLE Local_Market_AZ (
  maddress VARCHAR(30) NOT NULL PRIMARY KEY WITH (IGNORE_DUP_KEY = ON),
  zipcode INT NOT NULL,
  FOREIGN KEY (zipcode) REFERENCES Local_Market_ZC (zipcode)
);


CREATE TABLE Local_Market (
  mid INT IDENTITY(1,1) NOT NULL,
  mname VARCHAR(15) NOT NULL,
  maddress VARCHAR(30) NOT NULL,
  phone_number VARCHAR(20) UNIQUE,
  budget INT,
  PRIMARY KEY (mid),
  FOREIGN KEY (maddress) REFERENCES Local_Market_AZ (maddress),

);
CREATE TABLE Website (
  wid INT NOT NULL PRIMARY KEY WITH (IGNORE_DUP_KEY = ON),
);

CREATE TABLE Produces (
  pro_id INT IDENTITY(1,1) NOT NULL,
  year_pro INT NOT NULL,
  quantity INT,
  fid INT NOT NULL,
  pid INT NOT NULL,
  PRIMARY KEY (pro_id),
  FOREIGN KEY (fid) REFERENCES Farmer(fid),
  FOREIGN KEY (pid) REFERENCES Product(pid)
);

CREATE TABLE Registers (
  IBAN VARCHAR(30) NOT NULL,
  fid INT NOT NULL,
  wid INT NOT NULL,
  rid INT IDENTITY(1,1) NOT NULL,
  FOREIGN KEY (fid) REFERENCES Farmer(fid),
  FOREIGN KEY (wid) REFERENCES Website(wid),
  PRIMARY KEY (rid),
);

  CREATE TABLE Website_List (
  origin VARCHAR(15) NOT NULL,
  price FLOAT NOT NULL,
  quantity INT,
  fid INT NOT NULL,
  pid INT NOT NULL,
  wid INT NOT NULL,
  rid INT NOT NULL,
  PRIMARY KEY (rid),
  FOREIGN KEY (pid) REFERENCES Product(pid),
  FOREIGN KEY (wid) REFERENCES Website(wid),
);
CREATE TABLE Buys_Product_CN (
creditcard VARCHAR(20) NOT NULL PRIMARY KEY WITH (IGNORE_DUP_KEY = ON),
mname VARCHAR(15),
);
CREATE TABLE Buys_Product (
  buy_id INT IDENTITY(1,1) NOT NULL,
  amount INT NOT NULL,
  price INT NOT NULL,
  creditcard VARCHAR(20) NOT NULL,
  mid INT NOT NULL,
  wid INT NOT NULL,
  rid INT NOT NULL,
  PRIMARY KEY(buy_id),
  FOREIGN KEY (mid) REFERENCES Local_Market(mid),
  FOREIGN KEY (wid) REFERENCES Website(wid),
  FOREIGN KEY (creditcard) REFERENCES Buys_Product_CN (creditcard),
);
ALTER TABLE Buys_Product
ADD CONSTRAINT check_qa
CHECK (1 = dbo.check_b(rid,wid,amount))


ALTER TABLE Product_PH
ADD CONSTRAINT check_ph
CHECK (plant_date < harvest_date)
