import data.ActionsBD;
import org.junit.jupiter.api.Test;
import personnes.Manager;
import personnes.Programmeur;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ActionsTests {

    private List<Programmeur> progs;
    private List<Manager> managers;
    private final ActionsBD actionsBD = new ActionsBD();

    @Test
    public void getAllProg(){
        try {
            this.progs = this.actionsBD.getAllProg();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for(Programmeur p : this.progs)
            System.out.println(p);
    }

    @Test
    public void getProgById(){
        Programmeur prog;
        int id = 1;
        try {
            prog = this.actionsBD.getProgById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(prog);
    }

    @Test
    public void deleteProgById(){
        int id = 1;
        try {
            actionsBD.deleteProgById(id);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        this.getAllProg();
    }

    @Test
    public void addProg() {
        Programmeur programmeur = new Programmeur();
        programmeur.setManager(new Manager());
        try{
            this.actionsBD.addProg(programmeur);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }

        this.getAllProg();
    }
    @Test
    public void setProgSalaryById(){
        int id = 1;
        float newSalary = 1001f;
        try {
            this.actionsBD.setProgSalaryById(id, newSalary);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        this.getProgById();
    }
    @Test
    public void getProgWithMaxSalary() {
        Programmeur prog;
        try {
            prog = this.actionsBD.getProgWithMaxSalary();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(prog);
    }
    @Test
    public void getProgWithMinSalary(){
        Programmeur prog;
        try {
            prog = this.actionsBD.getProgWithMinSalary();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(prog);
    }
    @Test
    public void getAvgSalaryByAgeProg(){
        Map<Integer, Float> avgSalaryByAge;

        try {
            avgSalaryByAge = this.actionsBD.getAvgSalaryByAgeProg();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (Map.Entry<Integer, Float> entry : avgSalaryByAge.entrySet())
            System.out.println("Âge : " + entry.getKey() +
                    ", Salaire moyen : " + entry.getValue());

    }
    @Test
    public void getNbProg(){
        int nbProg;
        try {
            nbProg = this.actionsBD.getNbProg();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.print("Il y a " + nbProg);
        System.out.println(nbProg > 1 ? " progs." : "prog.");
    }
    @Test
    public void getRankProgBySalary() {
        Map<Integer, Programmeur> ranking;
        try {
            ranking = this.actionsBD.getRankProgBySalary();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (Map.Entry<Integer, Programmeur> entry : ranking.entrySet())
            System.out.println("Rang : " + entry.getKey() +
                    ", Programmeur : " + entry.getValue());
    }
    @Test
    public void getCorrelationBetweenAgeAndSalaryProg() {
        double correlation;
        try {
            correlation = this.actionsBD.getCorrelationBetweenAgeAndSalaryProg();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("La correlation entre l'âge et le salaire (d'un programmeur) " +
                "est : " + correlation);
    }

    /*---------------------------- MANAGER ----------------------------*/

    @Test
    public void getAllManager() {
        try {
            this.managers = this.actionsBD.getAllManager();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for(Manager m : this.managers)
            System.out.println(m);
    }
    @Test
    public void getManagerById(){
        Manager manager;
        int id = 1;
        try {
            manager = this.actionsBD.getManagerById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(manager);
    }
    @Test
    public void getManagerByFullName(){
        String lastName = "Alonso";
        String firstName = "Cédric";
        Manager manager;
        try {
            manager = this.actionsBD.getManagerByFullName(lastName, firstName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(manager);
    }
    @Test
    public void deleteManagerById(){
        int id=1;
        try {
            actionsBD.deleteManagerById(id);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        this.getAllManager();
    }
    @Test
    public void addManager() {
        Manager manager = new Manager("Alonso", "Cédric", "10 Rue Json", "Java", 2002, 2129.3f, 220, "JavaDoc");
        try {
            this.actionsBD.addManager(manager);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        this.getAllManager();
    }
    @Test
    public void setManagerSalaryById() {
        int id = 1;
        float newSalary = 1000;
        try {
            this.actionsBD.setManagerSalaryById(id, newSalary);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        this.getManagerById();
    }
    @Test
    public void getSalaryHistogramManager() {
        Map<Float, Integer> salaryHisto;

        try {
            salaryHisto = this.actionsBD.getSalaryHistogramManager();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (Map.Entry<Float, Integer> entry : salaryHisto.entrySet())
            System.out.println("Salaire : " + entry.getKey() +
                    ", Nb programmeur : " + entry.getValue());
    }

    /*---------------------------- EXIT ----------------------------*/
    @Test
    public void exit(){
        this.actionsBD.exit();
    }

    /*---------------------------- RESET ----------------------------*/

    @Test
    public void deleteALLProgs(){
        try {
            this.actionsBD.deleteALLProgs();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void deleteALLManagers(){
        try {
            this.actionsBD.deleteALLManagers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void resetALLIndex(){
        try {
            this.actionsBD.resetIndexProg();
            this.actionsBD.resetIndexManager();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
