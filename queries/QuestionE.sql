use [hw2DB]

SELECT sum_table.origin, Farmer.fname
FROM 
  (SELECT temp.origin,MAX(temp.total) as maxTotal
  FROM
    (SELECT W.origin,W.fid,SUM(W.quantity) AS total
    FROM Website_List W, Product P
    WHERE P.pid = W.pid AND P.pname = 'grain'
    GROUP BY W.fid,W.origin) as temp
  GROUP BY temp.origin) as max_table,
  (SELECT W.origin,W.fid,SUM(W.quantity) AS total
  FROM Website_List W, Product P
  WHERE P.pid = W.pid AND P.pname = 'grain'
  GROUP BY W.fid,W.origin) as sum_table
JOIN Farmer ON Farmer.fid = sum_table.fid
WHERE sum_table.origin = max_table.origin AND sum_table.total = max_table.maxTotal

