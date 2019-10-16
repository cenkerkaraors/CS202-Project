use [hw2DB]	

SELECT Farmer.fname,temp.pid
FROM  (SELECT temp2.pid, MAX(temp2.sums) AS maxProduce 
	   FROM (SELECT P.pid,F.fid,SUM(P.quantity) AS sums 
			 FROM Farmer F,Produces P 
			 WHERE F.fid = P.fid GROUP BY F.fid,P.pid) AS temp2
	   GROUP BY temp2.pid) AS tempMax,
	  (SELECT P.pid,F.fid,SUM(P.quantity) AS sums
      FROM Farmer F,Produces P
      WHERE F.fid = P.fid
      GROUP BY F.fid,P.pid
      ) AS temp
JOIN Farmer ON Farmer.fid = temp.fid
WHERE temp.sums = tempMax.maxProduce AND temp.pid = tempMax.pid

