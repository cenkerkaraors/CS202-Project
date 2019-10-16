use [hw2DB]

SELECT TOP 3 P.pname,W.pid,SUM(B.amount*B.price) AS rev
FROM Buys_Product B,Website_List W,Product P
WHERE B.rid = W.rid AND B.wid = W.wid AND W.pid = P.pid
GROUP BY W.pid,P.pname
ORDER BY rev DESC
