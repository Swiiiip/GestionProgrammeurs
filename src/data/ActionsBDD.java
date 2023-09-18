package data;

import java.util.List;

public interface ActionsBDD {

    List<ProgrammeurBean> getAllProg() throws Exception;

    ProgrammeurBean getProgById(long Id) throws Exception;

    void deleteProgById(long Id) throws Exception;

    void addProg(ProgrammeurBean programmeur) throws Exception;

    void setSalaryById(long Id, double newSalary) throws Exception;

    void exit();
}
