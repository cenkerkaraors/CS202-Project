
CREATE FUNCTION dbo.check_b2 (
    @rid INT,
    @wid INT,
    @price INT
)
RETURNS INT
AS 
BEGIN
    DECLARE @val INT
    IF @rid IN (SELECT W.rid 
				FROM Website_List W 
				WHERE W.price = @price AND W.rid = @rid AND W.wid = @wid)
	SET @val = 1
	ELSE
	SET @val = 0
	RETURN @val
END;