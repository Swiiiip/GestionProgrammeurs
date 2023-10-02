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
ADD CONSTRAINT FK_programmeur FOREIGN KEY(Id_manager) REFERENCES Manager(Id);

DROP TRIGGER IF EXISTS SetNewManagerForProgrammeur;

DELIMITER //
CREATE TRIGGER SetNewManagerForProgrammeur
    BEFORE DELETE ON Manager FOR EACH ROW
BEGIN
    DECLARE deleted_manager_id INT;
    DECLARE new_manager_id INT;
    DECLARE manager_department VARCHAR(255);

    SET deleted_manager_id = OLD.Id;
    SET manager_department = OLD.Department;

    UPDATE Programmeur
    SET Id_manager = NULL
    WHERE Id_manager = deleted_manager_id;

    SET new_manager_id = (
        SELECT Id
        FROM Manager
        WHERE Department = manager_department
          AND Id != deleted_manager_id
        ORDER BY (YEAR(NOW()) - BirthYear)
        LIMIT 1
    );

    IF new_manager_id IS NULL THEN
        SET new_manager_id = 0;
    END IF;

    UPDATE Programmeur
    SET Id_manager = new_manager_id
    WHERE Id_manager = deleted_manager_id;
END;
//
DELIMITER ;