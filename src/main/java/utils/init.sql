CREATE DATABASE IF NOT EXISTS APTN61_BD;

USE APTN61_BD;

CREATE USER IF NOT EXISTS 'adm'@'localhost' IDENTIFIED BY 'adm';
GRANT ALL ON *.* TO 'adm'@'localhost';
FLUSH PRIVILEGES;

DROP TABLE IF EXISTS Programmeur;
DROP TABLE IF EXISTS Manager;
DROP TABLE IF EXISTS Pictures;
DROP TABLE IF EXISTS Coords;
DROP TABLE IF EXISTS Address;

CREATE TABLE IF NOT EXISTS Programmeur
(
    Id          INT PRIMARY KEY AUTO_INCREMENT,
    Title       VARCHAR(255) NOT NULL,
    LastName    VARCHAR(255) NOT NULL,
    FirstName   VARCHAR(255) NOT NULL,
    Gender      VARCHAR(255) NOT NULL,
    Id_pictures INT          NOT NULL,
    Id_Address  INT NOT NULL,
    Id_coords   INT          NOT NULL,
    Pseudo      VARCHAR(255) NOT NULL,
    Id_manager  INT,
    Hobby       VARCHAR(255),
    BirthYear   INT,
    Salary      FLOAT,
    Prime       FLOAT
);

CREATE TABLE IF NOT EXISTS Pictures
(
    Id        INT PRIMARY KEY AUTO_INCREMENT,
    Large     VARCHAR(255) NOT NULL,
    Medium    VARCHAR(255) NOT NULL,
    Thumbnail VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS Coords
(
    Id        INT PRIMARY KEY AUTO_INCREMENT,
    Latitude  DECIMAL(7,4) NOT NULL,
    Longitude DECIMAL(7,4) NOT NULL
);


CREATE TABLE IF NOT EXISTS Address
(
    Id INT PRIMARY KEY AUTO_INCREMENT,
    StreetNumber INT NOT NULL,
    StreetName VARCHAR(255),
    City VARCHAR(255),
    State VARCHAR(255),
    Country VARCHAR(255),
    Postcode VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Manager
(
    Id          INT PRIMARY KEY AUTO_INCREMENT,
    Title       VARCHAR(255) NOT NULL,
    LastName    VARCHAR(255) NOT NULL,
    FirstName   VARCHAR(255) NOT NULL,
    Gender      VARCHAR(255),
    Id_pictures INT          NOT NULL,
    Id_Address  INT NOT NULL,
    Id_Coords   INT          NOT NULL,
    Hobby       VARCHAR(255),
    Department  VARCHAR(255),
    BirthYear   INT,
    Salary      FLOAT,
    Prime       FLOAT
);

ALTER TABLE Programmeur
    AUTO_INCREMENT = 1;
ALTER TABLE Manager
    AUTO_INCREMENT = 1;

ALTER TABLE Pictures
    AUTO_INCREMENT = 1;
ALTER TABLE Coords
    AUTO_INCREMENT = 1;
ALTER TABLE Address
    AUTO_INCREMENT = 1;

ALTER TABLE Programmeur
    ADD CONSTRAINT FK_Manager FOREIGN KEY (Id_manager) REFERENCES Manager (Id);

ALTER TABLE Programmeur
    ADD CONSTRAINT FK_Picture_Prog FOREIGN KEY (Id_pictures) REFERENCES Pictures (Id);

ALTER TABLE Programmeur
    ADD CONSTRAINT FK_Coords_Prog FOREIGN KEY (Id_coords) REFERENCES Coords (Id);

ALTER TABLE Programmeur
    ADD CONSTRAINT FK_Address_Prog FOREIGN KEY (Id_Address) REFERENCES Address (Id);

ALTER TABLE Manager
    ADD CONSTRAINT FK_Picture_Manager FOREIGN KEY (Id_pictures) REFERENCES Pictures (Id);

ALTER TABLE Manager
    ADD CONSTRAINT FK_Coords_Manager FOREIGN KEY (Id_coords) REFERENCES Coords (Id);

ALTER TABLE Manager
    ADD CONSTRAINT FK_Address_Manager FOREIGN KEY (Id_Address) REFERENCES Pictures (Id);

DROP TRIGGER IF EXISTS SetNewManagerForProgrammeur;

DELIMITER //
CREATE TRIGGER SetNewManagerForProgrammeur
    BEFORE DELETE
    ON Manager
    FOR EACH ROW
BEGIN
    DECLARE deleted_manager_id INT;
    DECLARE new_manager_id INT;
    DECLARE manager_department VARCHAR(255);

    SET deleted_manager_id = OLD.Id;
    SET manager_department = OLD.Department;

    UPDATE Programmeur
    SET Id_manager = NULL
    WHERE Id_manager = deleted_manager_id;

    SET new_manager_id = (SELECT Id
                          FROM Manager
                          WHERE Department = manager_department
                            AND Id != deleted_manager_id
                          ORDER BY (YEAR(NOW()) - BirthYear)
                          LIMIT 1);

    IF new_manager_id IS NULL THEN
        SET new_manager_id = 0;
    END IF;

    UPDATE Programmeur
    SET Id_manager = new_manager_id
    WHERE Id_manager = deleted_manager_id;
END;
//
DELIMITER ;