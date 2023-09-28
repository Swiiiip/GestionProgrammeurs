CREATE DATABASE IF NOT EXISTS APTN61_BD;

USE APTN61_BD;

CREATE USER IF NOT EXISTS 'adm'@'localhost' IDENTIFIED BY 'adm';
GRANT ALL ON *.* TO 'adm'@'localhost';
FLUSH PRIVILEGES;

DROP TABLE IF EXISTS Programmeur;
DROP TABLE IF EXISTS Manager;

CREATE TABLE IF NOT EXISTS Programmeur (
     Id INT PRIMARY KEY AUTO_INCREMENT,
     LastName VARCHAR(255) NOT NULL,
     FirstName VARCHAR(255) NOT NULL,
     Gender VARCHAR(255) NOT NULL,
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
    Gender VARCHAR(255),
    Address VARCHAR(255),
    Hobby VARCHAR(255),
    Department VARCHAR(255),
    BirthYear INT,
    Salary FLOAT,
    Prime FLOAT
);

ALTER TABLE Programmeur AUTO_INCREMENT = 1;
ALTER TABLE Manager AUTO_INCREMENT = 1;

ALTER TABLE Programmeur
ADD CONSTRAINT FK_programmeur FOREIGN KEY(Id_manager) REFERENCES Manager(Id) ON DELETE NO ACTION;

CREATE TRIGGER CheckManagerIDExists
    BEFORE INSERT ON Programmeur FOR EACH ROW
BEGIN
    DECLARE manager_count INT;
    SELECT COUNT(*) INTO manager_count FROM Manager WHERE Id = NEW.Id;

    IF manager_count = 0 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'L\' Id du manager spécifié n\'existe pas.';
    END IF;
END;