import data.ActionsBD;
import data.ProgrammeurBean;
import java.sql.SQLException;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        ActionsBD actionsBD = new ActionsBD();
        List<ProgrammeurBean> progs;

        try {
            progs = actionsBD.getAllProg();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        for(ProgrammeurBean prog : progs)
            System.out.println(prog);

    }
}
