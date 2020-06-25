DROP DATABASE IF EXISTS brookeb_hotel_database;

CREATE DATABASE brookeb_hotel_database;

USE brookeb_hotel_database;

CREATE TABLE RoomType(
	RoomTypeName VARCHAR(6) PRIMARY KEY,
    BaseCost DECIMAL(6,2) NOT NULL, 
    StandardOcc INT NOT NULL,
    MaxOcc INT NOT NULL,
    CostPerExtra DECIMAL (4,2) NOT NULL
);

CREATE TABLE Room (
    RoomNum INT PRIMARY KEY,
    RoomType VARCHAR(6) NOT NULL,
    ADAAccess VARCHAR(3) NOT NULL,
    FOREIGN KEY (RoomType)
		REFERENCES RoomType(RoomTypeName)
);

CREATE TABLE AmenityType (
	AmenityId INT PRIMARY KEY,
    AmenityType VARCHAR(50) NOT NULL,
    ExtraCost DECIMAL(2) NOT NULL
);

CREATE TABLE RoomBridgeAmenity(
	RoomNum INT NOT NULL,
    AmenityId INT NOT NULL,
    PRIMARY KEY (RoomNum, AmenityId),
    FOREIGN KEY (RoomNum)
		REFERENCES Room(RoomNum),
	FOREIGN KEY (AmenityId)
		REFERENCES AmenityType(AmenityId)
);

CREATE TABLE Guest(
    GuestId INT PRIMARY KEY,
    GuestFirstNm VARCHAR(20) NOT NULL,
    GuestLastNm VARCHAR(20) NOT NULL,
    GuestAddress VARCHAR(30) NOT NULL,
    GuestCity VARCHAR(20) NOT NULL,
    GuestState CHAR(2) NOT NULL,
    GuestZip INT NOT NULL,
    GuestPhone VARCHAR(15) NOT NULL
);

CREATE TABLE Reservation(
	ReservationId INT PRIMARY KEY,
    GuestId INT NOT NULL,
    StartDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    FOREIGN KEY (GuestId)
		REFERENCES Guest(GuestId)
);

CREATE TABLE RoomBridgeReservation(
	RoomNum INT NOT NULL,
    ReservationId INT NOT NULL,
    Adults INT NOT NULL,
    Children INT NOT NULL,
	TotalRoomCost DECIMAL(7,2) NOT NULL,
    PRIMARY KEY (RoomNum, ReservationId),
    FOREIGN KEY (RoomNum)
		REFERENCES Room(RoomNum),
	FOREIGN KEY (ReservationId)
		REFERENCES Reservation(ReservationId)
);

INSERT INTO RoomType (RoomTypeName, BaseCost, StandardOcc, MaxOcc, CostPerExtra) VALUES
	('Single','149.99', 2, 2, '0'),('Double','174.99',2,4,'10'),('Suite','399.99',3,8,'20');

INSERT INTO Room (RoomNum, RoomType, ADAAccess) VALUES
	(201, 'Double', 'No'),(202, 'Double', 'Yes'),(203, 'Double', 'No'),(204, 'Double', 'Yes'),
    (205, 'Single', 'No'),(206, 'Single', 'Yes'),(207, 'Single', 'No'),(208, 'Single', 'Yes'),
    (301, 'Double', 'No'),(302, 'Double', 'Yes'),(303, 'Double', 'No'),(304, 'Double', 'Yes'),
    (305, 'Single', 'No'),(306, 'Single', 'Yes'),(307, 'Single', 'No'),(308, 'Single', 'Yes'),
    (401, 'Suite', 'Yes'),(402, 'Suite', 'Yes');

INSERT INTO AmenityType (AmenityId, AmenityType, ExtraCost) VALUES
	(1, 'Microwave','0'),(2, 'Refrigerator', '0'),(3, 'Jacuzzi','25'),
    (4, 'Oven','0');

INSERT INTO RoomBridgeAmenity(RoomNum, AmenityId) VALUES
    (201, 1), (201,3),
    (202, 2), 
    (203, 1), (203,3),
    (204, 2),
    (205, 1), (205,2), (205,3),
	(206,1), (206,2),
	(207,1),(207,2),(207,3),
	(208,1),(208,2),
	(301,1),(301,3),
    (302, 2),
    (303,1),(303,3),
    (304, 2),
    (305,1),(305,2),(305,3),
    (306,1),(306,2),
    (307,1),(307,2),(307,3),
    (308,1),(308,2),
    (401,1),(401,2),(401,4),
    (402,1),(402,2),(402,4);

INSERT INTO Guest(GuestId, GuestFirstNm, GuestLastNm, GuestAddress, GuestCity, 
GuestState, GuestZip, GuestPhone) VALUES 
	(1,'Brooke','Benkovich','123 Old Town Road','Hopkins','MN',55343,'(123)-456-7899'),
    (2,'Mack','Simmer','379 Old Shore Street','Council Bluffs', 'IA', 51501, '(291)-553-0508'),
    (3,'Bettyann','Seery','750 Wintergreen Dr.','Wasilla','AK', 99654,'(478)-277-9632'),
    (4,'Duane','Cullison','9662 Foxrun Lane','Harligen','TX',78552,'(308)-494-0198'),
    (5,'Karie','Yang','9378 W. Augusta Ave','West Deptford','NJ',08096,'(214)-730-0298'),
    (6,'Aurore','Lipton','762 Wild Rose Street','Saginaw','MI',4860,'(377)-507-0974'),
    (7,'Zachary','Luechtefeld','7 Poplar Dr.','Arvada','CO',80003,'(814)-485-2615'),
    (8,'Jeremiah','Pendergrass','70 Oakwood St','Zion','IL',60099,'(279)-491-0960'),
    (9,'Walter','Holaway','7556 Arrowhead St','Cumberland','RI',02864,'(446)-396-6785'),
    (10,'Wilfred','Vise','77 West Surrey Street','Oswego','NY',13126,'(834)-727-1001'),
    (11,'Maritza','Tilton','939 Linda Rd.','Burke','VA', 22015,'(446)-351-6860'),
    (12,'Joleen','Tison','87 Queen St.','Drexel Hill','PA',19026,'(231)-893-2755');

INSERT INTO Reservation(ReservationId, GuestId, StartDate, EndDate) VALUES
	(1, 2, '2023/02/02','2023/02/04'),
    (2,3, '2023/02/05', '2023/02/10'),
    (3,4, '2023/02/22','2023/02/24'),
    (4,5, '2023/03/06','2023/03/07'),
    (5,1, '2023/03/17','2023/03/20'),
    (6,6, '2023/03/18','2023/03/23'),
    (7,7, '2023/03/29','2023/03/31'),
    (8,8, '2023/03/31','2023/04/05'),
    (9,9, '2023/04/09','2023/04/13'),
    (10,10,'2023/04/23','2023/04/24'),
    (11,11,'2023/05/30','2023/06/02'),
    (12,12,'2023/06/10','2023/06/14'),
    (13,6,'2023/06/17','2023/06/18'),
    (14,1,'2023/06/28','2023/07/02'),
    (15,9,'2023/07/13','2023/07/14'),
    (16,10,'2023/07/18','2023/07/21'),
    (17,3,'2023/07/28','2023/07/29'),
    (18,3,'2023/08/30','2023/09/01'),
    (19,2,'2023/09/16','2023/09/17'),
    (20,5,'2023/09/13','2023/09/15'),
    (21,4,'2023/11/22','2023/11/25'),
    (22,2,'2023/11/22','2023/11/25'),
    (23,11,'2023/12/24','2023/12/28');

INSERT INTO RoomBridgeReservation(RoomNum, ReservationId, Adults, Children, TotalRoomCost) VALUES
	(308,1,1,0,'299.98'),
    (203,2,2,1,'999.95'),
    (305,3,2,0,'349.98'),
    (201,4,2,2,'199.99'),
    (307,5,1,1,'524.97'),
    (302,6,3,0,'924.95'),
    (202,7,2,2,'349.98'),
    (304,8,2,0,'874.95'),
    (301,9,1,0,'799.96'),
    (207,10,1,1,'174.99'),
    (401,11,2,4,'1199.97'),
    (206,12,2,0,'599.96'),
    (208,12,1,0,'599.96'),
    (304,13,3,0,'184.99'),
    (205,14,2,0,'699.96'),
    (204,15,3,1,'184.99'),
    (401,16,4,2,'1259.97'),
    (303,17,2,1,'199.99'),
    (305,18,1,0,'349.98'),
    (208,19,2,0,'149.99'),
    (203,20,2,2,'399.98'),
    (401,21,2,2,'1199.97'),
    (206,22,2,0,'449.97'),
    (301,22,2,2,'599.97'),
    (302,23,2,0,'699.96');
