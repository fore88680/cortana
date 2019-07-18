drop database if exists SuperPersonTest;
create database SuperPersonTest;
use SuperPersonTest;



create table SuperPerson (
SuperPersonId INT(10) AUTO_INCREMENT,
Name varchar(45) not null,
Description VARCHAR(300),
SuperPower VARCHAR(200),
Primary key(SuperPersonId)
);


CREATE TABLE Location (
LocationId INT(10) NOT NULL AUTO_INCREMENT,
LocationName VARCHAR(30) NOT NULL,
Description VARCHAR(300),
State VARCHAR(50) NOT NULL,
City VARCHAR(50) NOT NULL,
Street VARCHAR(50) NOT NULL,
Zip VARCHAR(10) NOT NULL,
Latitude DOUBLE(9, 6) NOT NULL,
Longitude DOUBLE(9, 6) NOT NULL,
Primary Key(LocationId)
);


Create TABLE Organization (
OrganizationId INT(10) NOT NULL AUTO_INCREMENT,
LocationId INT(10),
orgName VARCHAR(50) NOT NULL,
Description VARCHAR(300),
ContactInfo VARCHAR(300),
foreign key fk_Client_Location(LocationId)
references Location(LocationId),
primary key(OrganizationId)
);

CREATE TABLE SuperPersonOrganization (
SuperPersonId INT(10),
OrganizationId INT(10),
foreign key fk_Client_Organization(OrganizationId)
references Organization(OrganizationId),
foreign key fk_Client_SuperPerson(SuperPersonId)
references SuperPerson(SuperPersonId)
);

CREATE TABLE Sighting (
SightingId INT(10) primary key auto_increment,
LocationId INT(10),
Date DATE NOT NUll,
foreign key fk_Client_Location(LocationId)
references Location(LocationId)
);

CREATE TABLE SuperPersonSighting (
SuperPersonId INT(10),
SightingId INT(10),
foreign key fk_Client_Sighting(SightingId)
references Sighting(SightingId),
foreign key fk_Client_(SuperPersonId)
references SuperPerson(SuperPersonId)
);

