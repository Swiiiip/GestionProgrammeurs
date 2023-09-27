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

ALTER TABLE Programmeur AUTO_INCREMENT = 1;
ALTER TABLE Manager AUTO_INCREMENT = 1;

ALTER TABLE Programmeur
ADD CONSTRAINT FK_programmeur FOREIGN KEY(Id_manager) REFERENCES Manager(Id) ON DELETE NO ACTION;

ALTER TABLE Programmeur
ADD CONSTRAINT U_Prog_fullName UNIQUE(LastName, FirstName);

ALTER TABLE Manager
ADD CONSTRAINT U_Manager_fullName UNIQUE(LastName, FirstName);

DELIMITER //

INSERT INTO Manager (LastName, FirstName, Address, Hobby, Department, BirthYear, Salary, Prime)
    VALUES
        ('Achvar', 'Didier', '2 rue de la théorie du signal', 'les signaux', 'Théorie du signal', 1976, 3470, 123),
        ('Prof', 'Com', '294 rue de la communication', 'parler', 'Communication', 1970, 4233, 322);

INSERT INTO Programmeur (LastName, FirstName, Address, Pseudo, Id_manager, Hobby, BirthYear, Salary, Prime)
    VALUES
        ('Torvalds', 'Linus', '2 avenue Linux Git', 'linuxroot', 1, 'Salsa', 1969, 2170, 50),
        ('Strastrup', 'Bjarne', '294 rue C++', 'c++1', 2, 'Voyages', 1950, 2466, 80),
        ('Golsing', 'James', '3 bvd JVM', 'javapapa', 2, 'Peinture', 1955, 1987, 10);

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