use [hw2DB]

/*SELECT B.mid,W.pid,SUM(B.amount) AS sums
FROM Buys_Product B,Website_List W
WHERE B.rid = W.rid AND B.wid = W.wid
GROUP BY W.pid,B.mid

SELECT temp.pid, MAX(temp.sums) AS maxSums
FROM
	(SELECT B.mid,W.pid,SUM(B.amount) AS sums
	FROM Buys_Product B,Website_List W
	WHERE B.rid = W.rid AND B.wid = W.wid
	GROUP BY W.pid,B.mid) AS temp
GROUP BY temp.pid*/

SELECT L.mname,sum_table.mid
FROM Local_Market L,
	(SELECT temp.pid, MAX(temp.sums) AS maxSums
	FROM
		(SELECT B.mid,W.pid,SUM(B.amount) AS sums
		FROM Buys_Product B,Website_List W
		WHERE B.rid = W.rid AND B.wid = W.wid
		GROUP BY W.pid,B.mid) AS temp
	GROUP BY temp.pid) as max_table,

	(SELECT B.mid,W.pid,SUM(B.amount) AS sums
	FROM Buys_Product B,Website_List W
	WHERE B.rid = W.rid AND B.wid = W.wid
	GROUP BY W.pid,B.mid) AS sum_table
WHERE sum_table.pid = max_table.pid AND sum_table.sums = max_table.maxSums AND L.mid = sum_table.mid