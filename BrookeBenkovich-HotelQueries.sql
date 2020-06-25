USE brookeb_hotel_database;

-- Write a query that returns a list of reservations that end
--  in July 2023, including the name of the guest,
-- the Room number(s) and the reservation dates

	SELECT 
		g.GuestFirstNm,
        g.GuestLastNm,
        rmres.RoomNum,
        res.StartDate,
        res.EndDate
    FROM Guest g
		INNER JOIN Reservation res ON res.GuestId = g.GuestId
        INNER JOIN RoomBridgeReservation rmres ON rmres.ReservationId = res.ReservationId
    WHERE
		res.StartDate LIKE '%2023-07%'
        OR 
        res.EndDate LIKE '%2023-07%';

-- RESULTS: 4 rows returned

-- **********************************************************
-- Write a query that returns a list of all reservations for 
-- rooms with a jacuzzi, displaying the guest's name, the room
-- number, and the dates of the reservation.

SELECT 
	g.GuestFirstNm,
    g.GuestLastNm,
    r.RoomNum,
    res.StartDate,
    res.EndDate
FROM Guest g
	INNER JOIN Reservation res ON res.GuestId = g.GuestId
	INNER JOIN RoomBridgeReservation rmres ON rmres.ReservationId = res.ReservationId
    INNER JOIN Room r ON r.RoomNum = rmres.RoomNum
    INNER JOIN RoomBridgeAmenity rba ON rba.RoomNum = r.RoomNum
    INNER JOIN AmenityType a ON a.AmenityId = rba.AmenityId
WHERE 
	a.AmenityType Like '%Jacuzzi%';


-- RESULTS: 11 rows returned

-- ********************************************************** 
-- Write a query that returns all the rooms reserved for a 
-- specific guest, including the guest's name, the room(s) 
-- reserved, the starting date of the reservation, and how 
-- many people were included in the reservation. 
-- (Choose a guest's name from the existing data.)

SELECT
	g.GuestFirstNm,
    g.GuestLastNm,
    r.RoomNum,
    res.StartDate,
    res.EndDate,
    rmres.Adults,
    rmres.Children
FROM Guest g
	INNER JOIN Reservation res ON res.GuestId = g.GuestId
    INNER JOIN RoomBridgeReservation rmres ON rmres.ReservationId = res.ReservationId
    INNER join Room r ON r.RoomNum = rmres.RoomNum
WHERE
	g.GuestFirstNm = 'Mack' AND g.GuestLastNm = 'Simmer';

-- RESULTS: For Mack Simmer, 4 rows returned

-- **********************************************************
-- Write a query that returns a list of rooms, 
-- reservation ID, and per-room cost for each reservation. 
-- The results should include all rooms, whether or not 
-- there is a reservation associated with the room.

SELECT 
	r.RoomNum,
    res.ReservationId,
	rmty.BaseCost
FROM Room r
	LEFT OUTER JOIN RoomBridgeReservation rmres ON r.RoomNum = rmres.RoomNum
    LEFT OUTER JOIN Reservation res ON res.ReservationId = rmres.ReservationId
    INNER JOIN RoomType rmty ON rmty.RoomTypeName = r.RoomType;

-- RESULTS: 27 rows (2 rooms were not reserved).

-- **********************************************************
-- Write a query that returns all the rooms accommodating
-- at least three guests and that are reserved on any date
-- in April 2023.

SELECT
	r.RoomNum
FROM Room r
	INNER JOIN RoomBridgeReservation rmres ON r.RoomNUm = rmres.RoomNum
    INNER JOIN Reservation res ON res.ReservationId = rmres.ReservationId
    INNER JOIN Guest g ON g.GuestId = res.GuestId
    INNER JOIN RoomType rt ON r.RoomType = rt.RoomTypeName
WHERE
	(rt.MaxOcc >=3)
     AND 
     res.StartDate <= '2023/04/30'
     AND  
     res.EndDate >= '2023/04/1';

-- RESULTS: 0 rows. 


-- **********************************************************
-- Write a query that displays the name, address, and phone
-- number of a guest based on their phone number. (Choose a 
-- phone number from the existing data.)

SELECT 
	g.GuestFirstNm,
    g.GuestLastNm,
    g.GuestAddress,
    g.GuestCity,
    g.GuestState,
    g.GuestZip
FROM Guest g
WHERE g.GuestPhone = ('(291)-553-0508');

-- Result: 1 row

-- **********************************************************
-- Write a query that returns a list of all guest names and 
-- the number of reservations per guest, sorted starting with
--  the guest with the most reservations and then by the 
--  guest's last name.
 
SELECT
	g.GuestFirstNm,
    g.GuestLastNm,
    NumResTable.NumberReservations
FROM  
	(SELECT
		res.GuestId,
		COUNT(res.GuestId) AS NumberReservations
	FROM Reservation res
	GROUP BY res.GuestId) AS NumResTable
INNER JOIN Guest g ON g.GuestId = NumResTable.GuestId
ORDER BY NumResTable.NumberReservations DESC, g.GuestLastNm ASC;

 
-- RESULTS: 12 rows returned
