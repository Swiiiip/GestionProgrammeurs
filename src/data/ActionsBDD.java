package data;

import java.sql.SQLException;
import java.util.List;

public interface ActionsBDD {

    List<ProgrammeurBean> getAllProg() throws SQLException;

    ProgrammeurBean getProgById(long Id) throws SQLException;

    void deleteProgById(long Id) throws SQLException;

    void addProg(ProgrammeurBean programmeur) throws SQLException;

    void setSalaryById(long Id, double newSalary) throws SQLException;

    void exit();
}
