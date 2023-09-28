package data;

import connexion.Connexion;
import personnes.ManagerBean;
import personnes.ProgrammeurBean;
import utils.RequetesSQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Cette classe implémente l'interface ActionsBDD et fournit des méthodes pour effectuer
 * des opérations liées à une base de données de programmeurs.
 * Elle utilise la connexion définie dans la classe Constants pour interagir avec la base de données.
 *
 * @author Alonso Cédric
 * @author Hatoum Jade
 */
public class ActionsBD implements IActions {

    /**
     * Session de connexion unique.
     */
    private final Connexion connexion;

    /**
     * Constructeur par défaut qui initialise la connexion avec les informations propres à BDTPJAVA.
     */
    public ActionsBD() {
        this.connexion = new Connexion();
    }

    /**
     * Constructeur naturel permettant de personnaliser les connexions
     *
     * @param db_url L'url de la base de données.
     * @param db_user L'utilisateur pour accéder à la base de données.
     * @param db_pwd Le mot de passe pour accéder à la base de données.
     */
    public ActionsBD(String db_url, String db_user, String db_pwd) {
        this.connexion = new Connexion(db_url, db_user, db_pwd);
    }

    public Connexion getConnexion(){
        return this.connexion;
    }

    /*---------------------------- PROGRAMMEUR ----------------------------*/

    /**
     * Mappe les données récupérées de la requête SQL dans l'objet ProgrammeurBean.
     *
     * @param res Le ResultSet contenant les données de la requête SQL.
     * @return une instance de ProgrammeurBean contenant les données mappées.
     * @throws SQLException Si une erreur SQL survient lors de l'accès à la base de données.
     */
    private ProgrammeurBean mapProgrammeur(ResultSet res) throws SQLException {
        ProgrammeurBean prog = new ProgrammeurBean();

        prog.setId(res.getInt("Id"));
        prog.setFirstName(res.getString("FirstName"));
        prog.setLastName(res.getString("LastName"));
        prog.setAddress(res.getString("Address"));
        prog.setPseudo(res.getString("Pseudo"));

        ManagerBean manager = getManagerById(res.getInt("Id_manager"));
        prog.setManager(manager);

        prog.setHobby(res.getString("Hobby"));
        prog.setBirthYear(res.getInt("BirthYear"));
        prog.setSalary(res.getFloat("Salary"));
        prog.setPrime(res.getFloat("Prime"));

        return prog;
    }

    @Override
    public List<ProgrammeurBean> getAllProg() throws SQLException {
        List<ProgrammeurBean> programmeurs = new ArrayList<>();

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(RequetesSQL.GETALLPROG);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            ProgrammeurBean prog = mapProgrammeur(resultSet);
            programmeurs.add(prog);
        }

        if(programmeurs.isEmpty())
            throw new SQLException("Il n'y a aucun programmeurs dans notre base de données...");

        statement.close();
        resultSet.close();

        return programmeurs;
    }

    @Override
    public ProgrammeurBean getProgById(int Id) throws SQLException { //TODO : Vérifier si le programmeur existe
        ProgrammeurBean prog = null;

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(RequetesSQL.GETPROGBYID);

        statement.setLong(1, Id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next())
            prog = mapProgrammeur(resultSet);

        resultSet.close();
        statement.close();

        if(prog == null)
            throw new SQLException();

        return prog;
    }

    @Override
    public void deleteProgById(int id) throws SQLException { //TODO : Vérifier si le programmeur existe
        this.getProgById(id);

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(RequetesSQL.DELETEPROGBYID);

        statement.setLong(1, id);

        statement.executeUpdate();

        statement.close();
    }

    @Override
    public void addProg(ProgrammeurBean prog) throws SQLException {
        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(RequetesSQL.ADDPROG);

        statement.setString(1, prog.getLastName());
        statement.setString(2, prog.getFirstName());
        statement.setString(3, prog.getAddress());
        statement.setString(4, prog.getPseudo());
        statement.setInt(5, prog.getManager().getId());
        statement.setString(6, prog.getHobby());
        statement.setInt(7, prog.getBirthYear());
        statement.setFloat(8, prog.getSalary());
        statement.setFloat(9, prog.getPrime());

        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void setProgSalaryById(int id, float newSalary) throws SQLException {
        this.getProgById(id);

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(RequetesSQL.SETPROGSALARYBYID);

        statement.setDouble(1, newSalary);
        statement.setLong(2, id);

        statement.executeUpdate();

        statement.close();
    }

    @Override
    public ProgrammeurBean getProgWithMaxSalary() throws SQLException{
        ProgrammeurBean prog = null;

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(RequetesSQL.GETPROGWITHMAXSALARY);

        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next())
            prog = mapProgrammeur(resultSet);

        resultSet.close();
        statement.close();

        return prog;
    }

    @Override
    public ProgrammeurBean getProgWithMinSalary() throws SQLException{
        ProgrammeurBean prog = null;

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(RequetesSQL.GETPROGWITHMINSALARY);

        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next())
            prog = mapProgrammeur(resultSet);

        resultSet.close();
        statement.close();

        return prog;
    }

    @Override
    public Map<Integer, Float> getAvgSalaryByAgeProg() throws SQLException {
        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(RequetesSQL.GETAVGSALARYBYAGEPROG);
        ResultSet resultSet = statement.executeQuery();

        Map<Integer, Float> salaryByAge = new HashMap<>();
        while (resultSet.next()) {
            int age = resultSet.getInt("Age");
            float averageSalary = resultSet.getFloat("AverageSalary");

            salaryByAge.put(age, averageSalary);
        }

        resultSet.close();
        statement.close();

        return salaryByAge;
    }

    @Override
    public int getNbProg() throws SQLException{
        int nbProg = 0;
        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(RequetesSQL.GETNBPROG);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next())
            nbProg = resultSet.getInt("nbProgrammeur");

        resultSet.close();
        statement.close();

        return nbProg;
    }

    @Override
    public Map<Integer, ProgrammeurBean> getRankProgBySalary() throws SQLException{
        Map<Integer, ProgrammeurBean> rankProgBySalary = new HashMap<>();

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(RequetesSQL.GETRANKPROGBYSALARY);

        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            ProgrammeurBean prog = mapProgrammeur(resultSet);
            int ranking = resultSet.getInt("ClassementSalaire");

            rankProgBySalary.put(ranking, prog);
        }
        resultSet.close();
        statement.close();
        
        return rankProgBySalary;
    }

    private static double calculateMean(List<Double> data) {
        double sum = 0.0;
        for (double value : data) {
            sum += value;
        }
        return sum / data.size();
    }

    @Override
    public double getCorrelationBetweenAgeAndSalaryProg() throws SQLException{
        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(RequetesSQL.GETCORRELATIONBETWEENAGEANDSALARYPROG);

        ResultSet resultSet = statement.executeQuery();

        List<Double> birthYears = new ArrayList<>();
        List<Double> salaries = new ArrayList<>();

        while (resultSet.next()) {
            birthYears.add(resultSet.getDouble("BirthYear"));
            salaries.add(resultSet.getDouble("Salary"));
        }

        double meanBirthYear = calculateMean(birthYears);
        double meanSalary = calculateMean(salaries);

        double numerator = 0.0;
        double denominatorX = 0.0;
        double denominatorY = 0.0;

        for (int i = 0; i < birthYears.size(); i++) {
            double diffX = birthYears.get(i) - meanBirthYear;
            double diffY = salaries.get(i) - meanSalary;

            numerator += (diffX * diffY);
            denominatorX += (diffX * diffX);
            denominatorY += (diffY * diffY);
        }

        resultSet.close();
        statement.close();

        return numerator / (Math.sqrt(denominatorX) * Math.sqrt(denominatorY));
    }

    /*---------------------------- MANAGER ----------------------------*/

    private ManagerBean mapManager(ResultSet res) throws SQLException {
        ManagerBean manager = new ManagerBean();

        manager.setId(res.getInt("Id"));
        manager.setLastName(res.getString("LastName"));
        manager.setFirstName(res.getString("FirstName"));
        manager.setAddress(res.getString("Address"));
        manager.setHobby(res.getString("Hobby"));
        manager.setDepartment(res.getString("Department"));
        manager.setBirthYear(res.getInt("BirthYear"));
        manager.setSalary(res.getFloat("Salary"));
        manager.setPrime(res.getFloat("Prime"));
        return manager;
    }

    @Override
    public List<ManagerBean> getAllManager() throws SQLException {
        List<ManagerBean> managers = new ArrayList<>();

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(RequetesSQL.GETALLMANAGER);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            ManagerBean manager = mapManager(resultSet);
            managers.add(manager);
        }

        resultSet.close();
        statement.close();

        return managers;
    }

    @Override
    public ManagerBean getManagerById(int id) throws SQLException {
        ManagerBean manager = null;

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(RequetesSQL.GETMANAGERBYID);

        statement.setLong(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next())
            manager = mapManager(resultSet);

        resultSet.close();
        statement.close();

        if (manager == null)
            throw new SQLException();

        return manager;
    }

    @Override
    public ManagerBean getManagerByFullName(String lastName, String firstName) throws SQLException {
        ManagerBean manager = null;

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(RequetesSQL.GETMANAGERBYFULLNAME);

        statement.setString(1, lastName);
        statement.setString(2, firstName);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next())
            manager = mapManager(resultSet);

        resultSet.close();
        statement.close();

        if (manager == null)
            throw new SQLException();

        return manager;
    }

   @Override
    public void deleteManagerById(int id) throws SQLException {
        this.getManagerById(id);

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(RequetesSQL.DELETEMANAGERBYID);

        statement.setLong(1, id);

        statement.executeUpdate();

        statement.close();
    }

    @Override
    public void addManager(ManagerBean manager) throws SQLException {
        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(RequetesSQL.ADDMANAGER);

        statement.setString(1, manager.getLastName());
        statement.setString(2, manager.getFirstName());
        statement.setString(3, manager.getAddress());
        statement.setString(4, manager.getHobby());
        statement.setString(5, manager.getDepartment());
        statement.setInt(6, manager.getBirthYear());
        statement.setFloat(7, manager.getSalary());
        statement.setFloat(8, manager.getPrime());

        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void setManagerSalaryById(int id, float newSalary) throws SQLException {
        this.getManagerById(id);

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(RequetesSQL.SETMANAGERSALARYBYID);

        statement.setDouble(1, newSalary);
        statement.setLong(2, id);

        statement.executeUpdate();

        statement.close();
    }

    @Override
    public Map<Float, Integer> getSalaryHistogramManager() throws SQLException{
        Map<Float, Integer> salaryHistogramManager = new HashMap<>();

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(RequetesSQL.GETHISTOSALARYMANAGER);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            float salaryRange = resultSet.getFloat("PlageSalaire");
            int managerCount = resultSet.getInt("nbManager");

            salaryHistogramManager.put(salaryRange, managerCount);
        }

        resultSet.close();
        statement.close();

        return salaryHistogramManager;
    }


    /*---------------------------- EXIT ----------------------------*/

    @Override
    public void exit() {
        try {
            if (this.getConnexion().getConnexion() != null) {
                this.getConnexion().getConnexion().close();
            }
        } catch (SQLException e) {
            System.err.print("Erreur lors de la fermeture de la connexion à la base de données");
        }
    }

    /*---------------------------- RESET ----------------------------*/

    @Override
    public void deleteALLProgs() throws SQLException{
        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(RequetesSQL.DELETEALLPROGS);

        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void deleteALLManagers() throws SQLException{
        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(RequetesSQL.DELETEALLMANAGERS);

        statement.executeUpdate();
        statement.close();
    }
}