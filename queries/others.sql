use [hw2DB]

CREATE TABLE Website (
  wid INT NOT NULL,
  PRIMARY KEY (wid)
);

CREATE TABLE Produces (
  pro_id INT NOT NULL,
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
  rid INT NOT NULL,
  FOREIGN KEY (fid) REFERENCES Farmer(fid),
  FOREIGN KEY (wid) REFERENCES Website(wid),
  PRIMARY KEY (rid),
);

  CREATE TABLE Website_List (
  origin VARCHAR(15) NOT NULL,
  price INT NOT NULL,
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
creditcard VARCHAR(20) NOT NULL,
mname VARCHAR(15),
PRIMARY KEY (creditcard)
);
CREATE TABLE Buys_Product (
  buy_id INT NOT NULL,
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
