use [hw2DB]

SELECT Local_Market_ZC.city,L.mname
FROM
	(SELECT Local_Market_ZC.city,MAX(L2.budget) AS maxBudget
	FROM Local_Market L2
	JOIN Local_Market_AZ ON L2.maddress = Local_Market_AZ.maddress
	JOIN Local_Market_ZC ON Local_Market_AZ.zipcode = Local_Market_ZC.zipcode
	GROUP BY Local_Market_ZC.city) AS max_Table,
		Local_Market L
		JOIN Local_Market_AZ ON L.maddress = Local_Market_AZ.maddress
		JOIN Local_Market_ZC ON Local_Market_AZ.zipcode = Local_Market_ZC.zipcode
WHERE L.budget = max_Table.maxBudget AND Local_Market_ZC.city = max_Table.city