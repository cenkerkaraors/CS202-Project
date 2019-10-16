use [hw2DB]

SELECT (frm.sumf + lm.summ) AS totalUser
FROM (SELECT COUNT(DISTINCT W.fid) AS sumf FROM Website_List W) AS frm,
      (SELECT COUNT(DISTINCT B.mid) AS summ FROM Buys_Product B) AS lm