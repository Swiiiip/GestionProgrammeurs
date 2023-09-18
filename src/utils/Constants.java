package utils;

import data.ProgrammeurBean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class Constants {

    public static final List<String> ATTRIBUTES = Arrays.asList("Id","Last Name","First Name","Address","Pseudo","Manager","Hobby","Birth Year","Salary","Prime");
    public static final int MSGLEN = ATTRIBUTES.stream().map(String::length).max(Integer::compareTo).get();

    public static final String DB_URL = "jdbc:mysql://localhost:3306/APTN61_BD";

    public static final String DB_ID = "adm";

    public static final String DB_PASSWORD = "adm";

    public static final List<String> ListAttributs(ProgrammeurBean prog){
        return Arrays.asList(
                String.valueOf(prog.getId()),
                prog.getLastName(),
                prog.getFirstName(),
                prog.getAddress(),
                prog.getPseudo(),
                prog.getManager(),
                prog.getHobby(),
                String.valueOf(prog.getBirthYear()),
                String.valueOf(prog.getSalary()),
                String.valueOf(prog.getPrime())
        );
    }

    public static final Connection CONNECTION;

    static {
        try {
            CONNECTION = DriverManager.getConnection(DB_URL, DB_ID, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String GETALLPROG = "SELECT * FROM Programmeur";
    public static String GETPROGBYID = "SELECT * FROM Programmeur WHERE Id = ?";
    public static final String DELETEPROGBYID = "Delete FROM Programmeur WHERE Id = ?";
    public static final String ADDPROG = "INSERT INTO Programmeur (LastName, FirstName, Address, Pseudo, Manager, Hobby, BirthYear, Salary, Prime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static String SETSALARYBYID = "UPDATE Programmeur SET salary = ? WHERE Id = ?";



}
