use [hw2DB]

SELECT F.fname,sell.fid,sell.pid
FROM Farmer F,
	(SELECT P.pid,P.fid,SUM(p.quantity) AS sumsP
	FROM Produces P
	GROUP BY P.fid,P.pid) AS pro,
	(SELECT W.pid,W.fid,SUM(W.quantity) AS sumsW
	FROM Website_List W
	GROUP BY W.fid,W.pid) AS sell
WHERE sell.fid = pro.fid AND sell.pid = pro.fid AND sell.sumsW > pro.sumsP AND F.fid = sell.fid
