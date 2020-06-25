DROP DATABASE IF EXISTS brookeb_hotel_database;

CREATE DATABASE brookeb_hotel_database;

USE brookeb_hotel_database;

DELETE FROM RoomBridgeReservation
WHERE ReservationId = 8;

DELETE FROM Reservation
WHERE ReservationId = 8;

DELETE FROM Guest
WHERE GuestId = 8;

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