use [hw2DB]

/*SELECT w.fid,Farmer.fname,w.pid,SUM(w.quantity) AS sums
FROM Website_List W
JOIN Farmer ON Farmer.fid = W.fid
GROUP BY w.fid,w.pid,Farmer.fname

SELECT temp.pid,MAX(temp.sums) AS maxSum
FROM (SELECT w.fid,w.pid,SUM(w.quantity) AS sums
	  FROM Website_List W
	  GROUP BY w.fid,w.pid) AS temp
GROUP BY temp.pid*/

SELECT Farmer.fname,F.pid
FROM (SELECT temp.pid,MAX(temp.sums) AS maxSum
	  FROM (SELECT w.fid,w.pid,SUM(w.quantity) AS sums
			FROM Website_List W
			GROUP BY w.fid,w.pid) AS temp
	  GROUP BY temp.pid) AS M,

	 (SELECT w.fid, w.pid, SUM(w.quantity) AS sums
	  FROM Website_List W
	  GROUP BY w.fid,w.pid) AS F
JOIN Farmer ON Farmer.fid = F.fid
WHERE F.pid = M.pid AND F.sums = M.maxSum