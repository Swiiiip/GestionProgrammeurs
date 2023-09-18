package utils;

import data.ProgrammeurBean;

import java.util.Arrays;
import java.util.List;

public class Constants {

    public static final List<String> strings = Arrays.asList("Id","Last Name","First Name","Address","Pseudo","Manager","Hobby","Birth Year","Salary","Prime");
    public static final int messageLen = strings.stream().map(String::length).max(Integer::compareTo).get();

    public static final String DB_URL = "jdbc:mysql://localhost:3306/programmeurs";

    public static final String password = "root";

    public static final String id = "root";

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


}
