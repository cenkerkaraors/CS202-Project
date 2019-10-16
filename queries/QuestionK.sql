use [hw2DB]

SELECT DISTINCT L.mid,L.mname
FROM Local_Market L,
	(SELECT B.mid,SUM(B.amount*B.price) AS cost
	FROM Buys_Product B
	GROUP BY B.mid) AS temp
WHERE L.budget < temp.cost AND L.mid = temp.mid