CREATE TABLE Programmeur (
     Id INT PRIMARY KEY AUTO_INCREMENT,
     LastName VARCHAR(255) NOT NULL,
     FirstName VARCHAR(255) NOT NULL,
     Address VARCHAR(255),
     Pseudo VARCHAR(255) NOT NULL,
     Manager VARCHAR(255),
     Hobby VARCHAR(255),
     BirthYear INT,
     Salary FLOAT,
     Prime FLOAT
);


/*Les insert ont été généré automatiquement grâce à ChatGPT*/
INSERT INTO Programmeur (LastName, FirstName, Address, Pseudo, Manager, Hobby, BirthYear, Salary, Prime)
VALUES
    ('Doe', 'John', '123 Main Street', 'johndoe', 'Jane Smith', 'Coding', 1990, 60000.00, 5000.00),
    ('Smith', 'Jane', '456 Elm Street', 'janesmith', 'John Doe', 'Gaming', 1988, 65000.00, 5500.00),
    ('Brown', 'Alice', '789 Oak Avenue', 'alicebrown', 'Bob Johnson', 'Photography', 1995, 55000.00, 4500.00),
    ('Johnson', 'Bob', '101 Pine Road', 'bobjohnson', 'Alice Brown', 'Cooking', 1985, 70000.00, 6000.00),
    ('Wilson', 'David', '222 Cedar Lane', 'davidwilson', 'Emily White', 'Hiking', 1992, 58000.00, 4800.00),
    ('White', 'Emily', '333 Birch Drive', 'emilywhite', 'David Wilson', 'Reading', 1987, 72000.00, 6200.00),
    ('Garcia', 'Maria', '444 Redwood Lane', 'mariagarcia', 'Carlos Rodriguez', 'Painting', 1991, 59000.00, 4900.00),
    ('Rodriguez', 'Carlos', '555 Maple Street', 'carlosrodriguez', 'Maria Garcia', 'Traveling', 1989, 68000.00, 5700.00),
    ('Lee', 'Sung', '666 Willow Avenue', 'sunglee', 'Ji-hyun Park', 'Dancing', 1994, 60000.00, 5000.00),
    ('Park', 'Ji-hyun', '777 Oakwood Drive', 'jihyunpark', 'Sung Lee', 'Swimming', 1986, 74000.00, 6300.00);


