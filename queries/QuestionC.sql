use [hw2DB]

SELECT P1.pname as Product1, P2.pname as Product2
FROM Product P1,Product P2, Product_PH PD1,Product_PH PD2
WHERE P1.pid = PD1.pid AND P2.pid = PD2.pid AND PD2.plant_date >= PD1.harvest_date AND P1.pname <> P2.pname