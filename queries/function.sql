
CREATE FUNCTION dbo.check_b (
    @rid INT,
    @wid INT,
    @amount INT
)
RETURNS INT
AS 
BEGIN
    DECLARE @val INT
    IF @rid IN (SELECT  temp.rid
				FROM Website_List W,
					(SELECT B.rid, SUM(B.amount) AS sums
					FROM Buys_Product B
					GROUP BY B.rid) AS temp
				WHERE temp.rid = @rid AND @rid = W.rid AND W.quantity >= temp.sums)
	SET @val = 1
	ELSE
	SET @val = 0
	RETURN @val
END;




