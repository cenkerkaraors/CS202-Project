use [hw2DB]

SELECT sum_table.city , sum_table.fname
FROM
  (Select temp.city,MAX(temp.total) AS maxTotal
  FROM
    (SELECT F.fid,F.fname,Farmer_ZC.city,SUM(P.quantity) AS total
    FROM Farmer F
    RIGHT JOIN Farmer_AZ ON Farmer_AZ.faddress = F.faddress
    RIGHT JOIN Farmer_ZC ON Farmer_ZC.zipcode = Farmer_AZ.zipcode,Produces P,Product Pro
    WHERE P.fid = F.fid AND P.pid = Pro.pid AND Pro.pname = 'grain'
    GROUP BY F.fid,F.fname,Farmer_ZC.city) AS temp
  GROUP BY temp.city) as max_table,
  (SELECT F.fid,F.fname,Farmer_ZC.city,SUM(P.quantity) AS total
  FROM Farmer F
  RIGHT JOIN Farmer_AZ ON Farmer_AZ.faddress = F.faddress
  RIGHT JOIN Farmer_ZC ON Farmer_ZC.zipcode = Farmer_AZ.zipcode,Produces P,Product Pro
  WHERE P.fid = F.fid AND P.pid = Pro.pid AND Pro.pname = 'grain'
  GROUP BY F.fid,F.fname,Farmer_ZC.city) as sum_table
WHERE sum_table.city = max_table.city AND sum_table.total = max_table.maxTotal