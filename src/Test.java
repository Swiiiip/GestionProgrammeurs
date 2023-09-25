import data.ActionsBD;
import data.ManagerBean;
import data.ProgrammeurBean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Test {

    private ProgrammeurBean prog;

    private ManagerBean manager;

    private List<ProgrammeurBean> progs = new ArrayList<>();

    private List<ManagerBean> managers = new ArrayList<>();
    private final ActionsBD actionsBD = new ActionsBD();
    public static void main(String[] args) {

    }

    private void getAllProg(){
        try {
            this.progs = this.actionsBD.getAllProg();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for(ProgrammeurBean p : this.progs)
            System.out.println(p);
    }

    private void getProgById(int Id){
        try {
            this.prog = this.actionsBD.getProgById(Id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(this.prog);
    }

    public void deleteProgById(int Id){
        try {
            actionsBD.deleteProgById(Id);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        this.getAllProg();
    }

    public void addProg(ProgrammeurBean programmeur) {
        try{
            this.actionsBD.addProg(programmeur);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }

        this.getAllProg();
    }

    public void setProgSalaryById(int id, double newSalary){
        try {
            this.actionsBD.setProgSalaryById(id, newSalary);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        this.getProgById(id);
    }

    public void getProgWithMaxSalary() {
        try {
            this.prog = this.actionsBD.getProgWithMaxSalary();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(prog);
    }

    public void getProgWithMinSalary(){
        try {
            this.prog = this.actionsBD.getProgWithMinSalary();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(prog);
    }

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

    public void getNbProg(){
        int nbProg;
        try {
            nbProg = this.actionsBD.getNbProg();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Il y a " + nbProg + "nb progs");
    }

    public void getRankProgBySalary() {
        Map<Integer, ProgrammeurBean> ranking;
        try {
            ranking = this.actionsBD.getRankProgBySalary();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (Map.Entry<Integer, ProgrammeurBean> entry : ranking.entrySet())
            System.out.println("Rang : " + entry.getKey() +
                    ", Programmeur : " + entry.getValue());
    }

    public void getCorrelationBetweenAgeAndSalaryProg() {
        int correlation;
        try {
            correlation = this.actionsBD.getCorrelationBetweenAgeAndSalaryProg();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("La correlation entre l'âge et le salaire (d'un programmeur) " +
                "est : " + correlation);
    }

    /*---------------------------- MANAGER ----------------------------*/


    public void getAllManager() {
        try {
            this.managers = this.actionsBD.getAllManager();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for(ManagerBean m : this.managers)
            System.out.println(m);
    }

    public void getManagerById(int id){
        try {
            this.manager = this.actionsBD.getManagerById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(this.manager);
    }

    public void getManagerByFullName(String lastName, String firstName){
        try {
            this.manager = this.actionsBD.getManagerByFullName(lastName, firstName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(this.manager);
    }

    public void deleteManagerById(int Id){
        try {
            actionsBD.deleteManagerById(Id);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        this.getAllManager();
    }

    public void addManager(ManagerBean manager) {
        try {
            this.actionsBD.addManager(manager);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        this.getAllManager();
    }

    public void setManagerSalaryById(int id, double newSalary) {
        try {
            this.actionsBD.setManagerSalaryById(id, newSalary);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        this.getManagerById(id);
    }

    public void getSalaryHistogramManager() {
        Map<Float, Integer> salaryHisto;

        try {
            salaryHisto = this.actionsBD.getSalaryHistogramManager();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (Map.Entry<Float, Integer> entry : salaryHisto.entrySet())
            System.out.println("Salaire : " + entry.getKey() +
                    ", Age : " + entry.getValue());
    }

    /*---------------------------- EXIT ----------------------------*/

    public void exit(){
        this.actionsBD.exit();
    }

}
