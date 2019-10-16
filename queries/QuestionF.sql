use [hw2DB]

SELECT sum_table.fid, Farmer.fname
FROM 
	(SELECT MAX(temp.sums) AS maxSums
	FROM
		(SELECT R.fid , SUM(B.price*B.amount) AS sums
		FROM Buys_Product B,Registers R
		WHERE R.rid = B.rid AND R.wid = B.wid
		GROUP BY R.fid) AS temp) AS max_table,

		(SELECT R.fid, SUM(B.price*B.amount) AS sums
		FROM Buys_Product B,Registers R
		WHERE R.rid = B.rid AND R.wid = B.wid
		GROUP BY R.fid) AS sum_table
JOIN Farmer ON Farmer.fid = sum_table.fid
WHERE sum_table.sums = max_table.maxSums 