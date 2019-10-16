USE [hw2DB]
ALTER TABLE Buys_Product
ADD CONSTRAINT check_qa
CHECK (1 = dbo.check_b(rid,wid,amount))

ALTER TABLE Buys_Product
ADD CONSTRAINT check_pp
CHECK (1 = dbo.check_b2(rid,wid,price))

ALTER TABLE Product_PH
ADD CONSTRAINT check_ph
CHECK (plant_date < harvest_date)