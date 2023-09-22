CREATE DATABASE IF NOT EXISTS APTN61_BD;

USE APTN61_BD;

CREATE USER IF NOT EXISTS 'adm'@'localhost' IDENTIFIED BY 'adm';
GRANT ALL ON *.* TO 'adm'@'localhost'; /* Nous donnons tous les droits à l'utilisateur 'adm' pour la simplicité, malgré les dangers que cela pourrait présenter. */
FLUSH PRIVILEGES;

CREATE TABLE IF NOT EXISTS Programmeur (
     Id INT PRIMARY KEY AUTO_INCREMENT,
     LastName VARCHAR(255) NOT NULL,
     FirstName VARCHAR(255) NOT NULL,
     Address VARCHAR(255),
     Pseudo VARCHAR(255) NOT NULL,
     Id_manager INT,
     Hobby VARCHAR(255),
     BirthYear INT,
     Salary FLOAT,
     Prime FLOAT
);

CREATE TABLE IF NOT EXISTS Manager(
    Id INT PRIMARY KEY AUTO_INCREMENT,
    LastName VARCHAR(255) NOT NULL,
    FirstName VARCHAR(255) NOT NULL,
    Address VARCHAR(255),
    Hobby VARCHAR(255),
    Department VARCHAR(255),
    BirthYear INT,
    Salary FLOAT,
    Prime FLOAT
);

ALTER TABLE Programmeur
ADD CONSTRAINT FK_programmeur FOREIGN KEY(Id_manager) REFERENCES Manager(Id) ON DELETE NO ACTION;

ALTER TABLE Programmeur
ADD CONSTRAINT U_Prog_fullName UNIQUE(LastName, FirstName);

ALTER TABLE Manager
ADD CONSTRAINT U_Manager_fullName UNIQUE(LastName, FirstName);