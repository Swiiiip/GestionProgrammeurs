package exec;

import data.ActionsBD;
import personnes.ManagerBean;
import personnes.ProgrammeurBean;
import utils.Departments;
import utils.Hobbies;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Test {

    private List<ProgrammeurBean> progs;

    private List<ManagerBean> managers;
    private final ActionsBD actionsBD = new ActionsBD();

    public Test(){
        this.progs = new ArrayList<>();
        this.managers = new ArrayList<>();
    }

    public void getAllProg(){
        try {
            this.progs = this.actionsBD.getAllProg();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for(ProgrammeurBean p : this.progs)
            System.out.println(p);
    }

    public void getProgById(){
        ProgrammeurBean prog;
        int id = 1;
        try {
            prog = this.actionsBD.getProgById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(prog);
    }

    public void deleteProgById(){
        int id = 1;
        try {
            actionsBD.deleteProgById(id);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        this.getAllProg();
    }

    public void addProg() {
        ProgrammeurBean programmeur = new ProgrammeurBean();
        try{
            this.actionsBD.addProg(programmeur);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }

        this.getAllProg();
    }

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

    public void getProgWithMaxSalary() {
        ProgrammeurBean prog;
        try {
            prog = this.actionsBD.getProgWithMaxSalary();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(prog);
    }

    public void getProgWithMinSalary(){
        ProgrammeurBean prog;
        try {
            prog = this.actionsBD.getProgWithMinSalary();
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

        System.out.print("Il y a " + nbProg);
        System.out.println(nbProg > 1 ? " progs." : "prog.");
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


    public void getAllManager() {
        try {
            this.managers = this.actionsBD.getAllManager();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for(ManagerBean m : this.managers)
            System.out.println(m);
    }

    public void getManagerById(){
        ManagerBean manager;
        int id = 1;
        try {
            manager = this.actionsBD.getManagerById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(manager);
    }

    public void getManagerByFullName(){
        String lastName = "Alonso";
        String firstName = "Cédric";
        ManagerBean manager;
        try {
            manager = this.actionsBD.getManagerByFullName(lastName, firstName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(manager);
    }

    public void deleteManagerById(){
        int id=1;
        try {
            actionsBD.deleteManagerById(id);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        this.getAllManager();
    }

    public void addManager() {
        ManagerBean manager = new ManagerBean("Alonso", "Cédric", "10 Rue Json", "Java", 2002, 2129.3f, 220, "JavaDoc");
        try {
            this.actionsBD.addManager(manager);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        this.getAllManager();
    }

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

    public void exit(){
        this.actionsBD.exit();
    }



    /*---------------------------- MAIN ----------------------------*/
    public static void main(String[] args) {
        Test test = new Test();
        test.getAllProg();

        System.out.println(Hobbies.generateRandomHobby() + " " + Departments.generateRandomDepartment());
    }



}