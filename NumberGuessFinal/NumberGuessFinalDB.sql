drop database if exists NumberGuessFinal;
create database NumberGuessFinal;
use NumberGuessFinal;

create table Game (
GameId INT(10) primary key AUTO_INCREMENT ,
AnswerNumber VARCHAR(4),
Finished BIT
);



create table Round (
RoundId INT(100) primary key AUTO_INCREMENT,
Date DATETIME,
UserGuess VARCHAR(4),
ExactMatches INT(4),
PartialMatches INT(4),
GameId INT(10),
foreign key fk_Client_Game(GameID)
references Game(GameID)
);

Insert Into Game(GameID, AnswerNumber, Finished)
Values(1,2419,0),
(2,4523,0),
(3,8596,0);
 

Insert Into Round(RoundID, Date, UserGuess,ExactMatches,PartialMatches,GameID)
Values(1, '2019-07-02', 2158, 1, 1,1),
(2, '2019-07-02', 2031, 1, 1,1);
