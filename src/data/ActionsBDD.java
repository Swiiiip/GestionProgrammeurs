package data;

import java.util.List;

public interface ActionsBDD {

    List<ProgrammeurBean> getAllProg();

    ProgrammeurBean getProgById(long Id);

    void deleteProgById(long Id);

    void addProg();

    void setSalaryById(long Id);

    void exit();
}
