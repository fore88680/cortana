drop database if exists HotelBooking;
create database HotelBooking;

use HotelBooking;

CREATE TABLE Billing (
BillingID INT(10) primary key,
ExtraAdult INT(1),
ExtraChild INT(1),
ExtraPeopleCost INT(2),
JacuzziCost INT(2),
RoomCost INT(10),
TotalAfterAddOns INT(10)
);

create table Guest (
GuestID INT(10) primary key,
FirstName varchar(45) not null,
LastName varchar(45) not null,
Phone varchar(10) not null,
Age varchar(10) null,
Address varchar(100)
);


create table Booking (
BookingID INT(10) primary key,
BookingStartDate DATETIME not null,
BookingEndDate DATETIME not null,
BillingID INT(10),
foreign key fk_Client_BillingID (BillingID)
references Billing(BillingID),
GuestID INT (10),
foreign key fk_Client_GuestID (GuestID)
references Guest(GuestID)
);


CREATE TABLE Room (
  RoomID INT(10) primary key,
  RoomNumber INT(3) not null,
  RoomFloor INT(1) not null
  
);

CREATE TABLE BookingRoom (
RoomId INT(10),
BookingID INT (10),
foreign key fk_Client_Room(RoomID) 
references Room(RoomID),
foreign key fk_Client_Booking(BookingID)
references Booking(BookingID)
);


CREATE TABLE RoomType (
  RoomTypeID INT (10) primary key,
  MaxOccupancy INT(1) not null,
  StandardOccupancy INT (1) not null,
  BedType varchar(10) not null,
  RoomID INT(10),
  foreign key fk_Client_Room(RoomID)
  references Room(RoomID)
);

CREATE TABLE Rate (
  RateID INT(10) primary key,
  Rate DECIMAL(6,2) not null,
  RoomTypeID INT(10),
  foreign key fk_Client_RoomType(RoomTypeID)
  references RoomType(RoomTypeID)
);

CREATE TABLE Amenities (
  AmenitiesID INT(10) primary key,
  AmenityType varchar(45) not null,
  ADAAccesible varchar(45)not null
);


CREATE TABLE RoomAmenities(
AmenitiesID INT(10),
RoomID INT(10),
foreign key fk_Client_Amenities(AmenitiesID)
references Amenities(AmenitiesID),
foreign key fk_Client_Room(RoomID)
references Room(RoomID)
);

INSERT INTO Billing(BillingID, ExtraAdult, ExtraChild, ExtraPeopleCost, JacuzziCost, RoomCost, TotalAfterAddOns)
Values(1,0,0,0,0,299.98,299.98),
(2,1,1,10,25,999.95,1034.95),
(3,1,0,0,25,349.98,374.98),
(4,1,2,10,25,199.99,234.99),
(5,0,1,0,25,524.97,549.987),
(6,2,0,20,0,924.95,944.95),
(7,1,2,10,0,349.98,369.98),
(8,1,0,10,0,874.95,884.95),
(9,0,0,0,25,799.96,824.96),
(10,0,0,0,25,174.99,199.99),
(11,1,4,20,0,1199.97,1219.97),
(12,1,0,0,0,559.96,559.96),
(13,0,0,0,0,599.96,599.96),
(14,2,0,20,0,184.99,204.99),
(15,1,0,0,25,699.96,724.96),
(16,2,1,20,0,184.99,204.99),
(17,3,2,60,0,1259.97,1319.97),
(18,1,1,10,25,199.99,234.99),
(19,0,0,0,25,349.98,374.98),
(20,1,0,0,0,149.99,149.99),
(21,1,2,10,25,399.98,434.98),
(22,1,2,20,0,1199.97,1217.97),
(23,1,0,0,0,449.97,449.97),
(24,1,2,10,25,599.97,634.97),
(25,1,0,10,0,699.96,709.96);


INSERT INTO Guest (GuestId, FirstName, LastName, Phone, Age, Address)
Values (1, 'Paul', 'Fore', 123456789, 'Adult', '123 Meow Meow, KittyTown MN'),
(2, 'Mack', 'Simmer', '2915530508', 'Adult', '379 Old Shore Street, Council Bluffs IA'),
(3, 'Bettyann', 'Seery', '4782779632', 'Adult', '750 Wintergreen Dr., Wasilla AK 99654'),
(4,'Duane', 'Cullison', '384940198', 'Adult', '9662 Foxrun Lan, Harlingen TX 78552' ),
(5, 'Karie', 'Yang', '2147300298', 'Adult', '9378 W. Augusta Ave., West Deptford NJ 08096'),
(6, 'Aurore', 'Lipton', '3775070974', 'Adult', '762 Wild Rose Street, Saginaw MI 48601'),
(7, 'Zachery', 'Luechtefeld', '8144852615', 'Adult', '7 Poplar Dr., Arvada CO 80003'),	
(8, 'Jeremiah', 'Pendergrass', '2794910960', 'Adult', '70 Oakwood St., Zion IL 60099'),
(9, 'Walter', 'Holaway', '4463966785', 'Adult', '7556 Arrowhead St., Cumberland RI 02864'),
(10, 'Wilfred', 'Vise', '8347271001', 'Adult', '	77 West Surrey Street, Oswego NY 13126'),
(11, 'Maritza', 'Tilton', '4463516860', 'Adult', '939 Linda Rd. ,Burke VA 22015'),
(12, 'Joleen', 'Tison', '2318932755', 'Adult', '87 Queen St., Drexel Hill	PA 19026');



INSERT INTO Booking (BookingID, BookingStartDate, BookingEndDate, BillingID, GuestId)
Values (1, '2023-02-02', "2023-02-04",1,2),
(2, '2023-02-05', "2023-02-10",2,3),
(3, '2023-02-22', "2023-02-24",3,4),
(4, '2023-03-06', "2023-03-07",4,5),
(5, '2023-03-17', "2023-03-20",5,1),
(6, '2023-03-18', "2023-03-23",6,6),
(7, '2023-03-29', "2023-03-31",7,7),
(8, '2023-03-31', "2023-04-05",8,8),
(9, '2023-04-09', "2023-04-13",9,9),
(10, '2023-04-23', "2023-04-24",10,10),
(11, '2023-05-30', "2023-06-02",11,11),
(12, '2023-06-10', "2023-06-14",12,12),
(13, '2023-06-10', "2023-06-14",13,12),
(14, '2023-06-17', "2023-06-18",14,6),
(15, '2023-06-28', "2023-07-02",15,1),
(16, '2023-07-13', "2023-07-14",16,9),
(17, '2023-07-18', "2023-07-21",17,10),
(18, '2023-07-28', "2023-07-29",18,3),
(19, '2023-08-30', "2023-09-01",19,3),
(20, '2023-09-16', "2023-09-17",20,2),
(21, '2023-09-13', "2023-09-15",21,5),
(22, '2023-11-22', "2023-11-25",22,4),
(23, '2023-11-22', "2023-11-25",23,2),
(24, '2023-11-22', "2023-11-25",24,2),
(25, '2023-12-24', "2023-12-28",25,11);

INSERT INTO Room (RoomId, RoomNumber, RoomFloor)
Values(1, 201, 2),
(2, 202, 2),
(3, 203, 2),
(4, 204, 2),
(5, 205, 2),
(6, 206, 2),
(7,207,2),
(8,208,2),
(9,301,3),
(10, 302,3),
(11, 303,3),
(12, 304, 3),
(13,305,3),
(14, 306, 3),
(15,307, 3),
(16, 308, 3),
(17, 401, 4),
(18,402,4);



INSERT INTO BookingRoom (BookingID, RoomID)
values (1,16),
(2,3),
(3,15),
(4,1),
(5,15),
(6,10),
(7,2),
(8,12),
(9,9),
(10,7),
(11,17),
(12,6),
(13,8),
(14,12),
(15,5),
(16,4),
(17,17),
(18,11),
(19,13),
(20,8),
(21,3),
(22,17),
(23,6),
(24,9),
(25,10);

INSERT INTO RoomType (RoomTypeID, MaxOccupancy, StandardOccupancy, BedType, RoomID)
Values(1, 4, 2, 'Double', 1),
(2,4,2,'Double',2),
(3,4,2,'Double',3),
(4,4,2,'Double',4),
(5,2,2,'Single',5),
(6,2,2,'Single',6),
(7,2,2,'Single',7),
(8,2,2,'Single',8),
(9,4,2,'Double',9),
(10,4,2,'Double',10),
(11,4,2,'Double',11),
(12,4,2,'Double',12),
(13,2,2,'Single',13),
(14,2,2,'Single',14),
(15,2,2,'Single',15),
(16,2,2,'Single',16),
(17,8,3,'Suite',17),
(18,8,3,'Suite',18);


INSERT INTO Rate (RateID, Rate, RoomTypeID)
Values(1,199.99,1),
(2,174.99,2),
(3,199.99,3),
(4,174.99,4),
(5,174.99,4),
(6,149.99,6),
(7,174.99,7),
(8,149.99,8),
(9,199.99,9),
(10,174.99,10),
(11,199.99,11),
(12,174.99,12),
(13,174.99,13),
(14,149.99,14),
(15,174.99,15),
(16,149.99,16),
(17,399.99,17),
(18,399.99,18);


Insert INTO Amenities (  AmenitiesID, AmenityType,ADAAccesible)
Values (1, 'Microwave, Jacuzzi', 'No'),
(2, 'Refrigerator', 'Yes'),
(3, 'Microwave, Jacuzzi',  'No'),
(4, 'Refrigerator', 'Yes'),
(5, 'Microwave, Refrigerator, Jacuzzi', 'No'),
(6, 'Microwave, Refrigerator', 'Yes'),
(7, 'Microwave, Refrigerator, Jacuzzi', 'No'),
(8, 'Microwave, Refrigerator', 'Yes'),
(9, 'Microwave, Jacuzzi', 'No'),
(10, 'Refrigerator', 'Yes'),
(11, 'Microwave, Jacuzzi', 'No'),
(12, 'Refrigerator', 'Yes'),
(13, 'Microwave, Refrigerator, Jacuzzi', 'No'),
(14, 'Microwave, Refrigerator', 'Yes'),
(15, 'Microwave, Refrigerator, Jacuzzi', 'No'),
(16, 'Microwave, Refrigerator', 'Yes'),
(17, 'Microwave, Refrigerator, Oven', 'Yes'),
(18, 'Microwave, Refrigerator, Oven', 'Yes');


INSERT INTO RoomAmenities (AmenitiesID, RoomID)
Values(1,1),
(2,2),
(3,3),
(4,4),
(5,5),
(6,6),
(7,7),
(8,8),
(9,9),
(10,10),
(11,11),
(12,12),
(13,13),
(14,14),
(15,15),
(16,16),
(17,17),
(18,18);
