import data.db.Actions;
import data.generator.DataGenerator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import personnes.Manager;
import personnes.Programmeur;
import personnes.utils.Address;
import personnes.utils.Coords;
import personnes.utils.Pictures;
import utils.Gender;
import utils.Hobbies;
import utils.Title;

import java.sql.SQLException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ActionsTest {

    private static Actions<Programmeur> actions;

    private void createFakePersonne() {
        new DataGenerator(2, 1);
    }

    @BeforeEach
    void setUp() {
        actions = new Actions<>();
        this.init();
    }

    private void init() {
        this.delete();
        this.resetIndex();
    }

    /**
     * Supprime toutes les données de Programmeurs et Managers de la base de données.
     */
    private void delete() {
        try {
            actions.deleteAll("Programmeur");
            actions.deleteAll("Manager");
            actions.deleteUtils();
        } catch (SQLException e) {
            System.err.println("La suppression de toutes les données dans la base de données a échoué. " + e.getMessage());
            throw new SecurityException();
        }
    }

    /**
     * Réinitialise les index des données de Programmeurs et Managers dans la base de données.
     */
    private void resetIndex() {
        try {
            actions.resetIndex("Programmeur");
            actions.resetIndex("Manager");
        } catch (SQLException e) {
            System.err.println("La réinitialisation des index dans la base de données a échoué. " + e.getMessage());
            throw new SecurityException();
        }
    }

    @Test
    void testGetAllPersonnesTrouvees() throws SQLException {
        this.createFakePersonne();

        int personnesAttendues = 2;

        int personnesTrouvees = actions.getAll("Programmeur").size();

        assertEquals(personnesAttendues, personnesTrouvees);
    }

    @Test
    void testGetAllAucunePersonneTrouvee() {
       assertThrows(SQLException.class, () -> actions.getAll("Programmeur"));
    }

    @Test
    void testGetByIdPersonneTrouvee() throws SQLException{
        this.createFakePersonne();

        int idAttendu = 1;

        int idTrouvee = actions.getById("Programmeur", 1).getId();

        assertEquals(idAttendu, idTrouvee);
    }

    @Test
    void testGetByIdAucunePersonneTrouvee(){
        assertThrows(SQLException.class, () -> actions.getById("Programmeur", 10));
    }

    @Test
    void testGetCoordsByIdCoordonneTrouvee() throws SQLException{
        this.createFakePersonne();

        int idAttendu = 1;

        int idTrouvee = actions.getCoordsById(1).getId();

        assertEquals(idAttendu, idTrouvee);
    }

    @Test
    void testGetCoordsByIdAucuneCoordonneeTrouvee(){
        assertThrows(SQLException.class, () -> actions.getCoordsById(10));
    }

    @Test
    void testGetPicturesByIdImageTrouvee() throws SQLException{
        this.createFakePersonne();

        int idAttendu = 1;

        int idTrouvee = actions.getPicturesById(1).getId();

        assertEquals(idAttendu, idTrouvee);
    }

    @Test
    void testGetPicturesByIdAucuneImageTrouvee(){
        assertThrows(SQLException.class, () -> actions.getPicturesById(10));
    }

    @Test
    void testGetAddressByIdAdresseTrouvee() throws SQLException{
        this.createFakePersonne();

        int idAttendu = 1;

        int idTrouvee = actions.getAddressById(1).getId();

        assertEquals(idAttendu, idTrouvee);
    }

    @Test
    void testGetAddressByIdAucuneAdresseTrouvee(){
        assertThrows(SQLException.class, () -> actions.getAddressById(10));
    }

    @Test
    void testGetFullCoordsCoordonneeTrouvee() throws SQLException{
        this.createFakePersonne();

        Coords coordsAttendu = actions.getById("Programmeur", 1).getCoords();

        Coords coordsTrouvee = actions.getFullCoords(coordsAttendu);

        assertEquals(coordsAttendu, coordsTrouvee);
    }

    @Test
    void testGetFullCoordsAucuneCoordonneeTrouvee(){
        assertThrows(SQLException.class, () -> actions.getFullCoords(new Coords("Villejuif",
                "France")));
    }

    @Test
    void testGetFullPicturesImageTrouvee() throws SQLException{
        this.createFakePersonne();

        Pictures imageAttendue = actions.getById("Programmeur", 1).getPictures();

        Pictures imageTrouvee = actions.getFullPictures(imageAttendue);

        assertEquals(imageAttendue, imageTrouvee);
    }

    @Test
    void testGetFullPicturesAucuneImageTrouvee(){
        assertThrows(SQLException.class, () -> actions.getFullPictures(new Pictures()));
    }

    @Test
    void testGetFullAddressAdresseTrouvee() throws SQLException{
        this.createFakePersonne();

        Address addressAttendue = actions.getById("Programmeur", 1).getAddress();

        Address addressTrouvee = actions.getFullAddress(addressAttendue);

        assertEquals(addressAttendue, addressTrouvee);
    }

    @Test
    void testGetFullAddressAucuneAdresseTrouvee(){
        assertThrows(SQLException.class, () -> actions.getFullAddress(new Address()));
    }

    @Test
    void testGetByFullNamePersonneTrouvee() throws SQLException{
        this.createFakePersonne();

        Programmeur progAttendu = actions.getById("Programmeur", 1);

        Programmeur progTrouve = actions.getByFullName("Programmeur",
                progAttendu.getLastName(), progAttendu.getFirstName());

        assertEquals(progAttendu, progTrouve);
    }

    @Test
    void testGetByFullNameAucunePersonneTrouvee() {
        assertThrows(SQLException.class, () -> actions.getByFullName("Programmeur",
                "null", "null"));
    }

    @Test
    void testAddPersonne() throws SQLException{
        this.createFakePersonne();
        Actions<Manager> managerActions = new Actions<>();

        Programmeur programmeurAjout = new Programmeur(Title.MR, "Projet", "Java", Gender.MALE,
                actions.getPicturesById(1), actions.getAddressById(1),
                actions.getCoordsById(1), "Swiiiip&MrRed",
                managerActions.getById("Manager", 1), Hobbies.PROGRAMMATION,
                2002, 1000f, 100f);

        actions.addPersonne(programmeurAjout);

        int nbPersonneAttendu = 3;

        int nbPersonneTrouvee = actions.getCount("Programmeur");

        assertEquals(nbPersonneAttendu, nbPersonneTrouvee);

        Programmeur programmeurTrouvee = actions.getByFullName("Programmeur",
                "Projet", "Java");

        assertNotNull(programmeurTrouvee);
    }

    @Test
    void testAddCoords() throws SQLException{
        Coords coordsAjout = new Coords("Villejuif", "France");

        actions.addCoords(coordsAjout);

        Coords coordsTrouve = actions.getCoordsById(1);

        assertNotNull(coordsTrouve);
    }

    @Test
    void testAddPictures() throws SQLException{
        Pictures picturesAjout = new Pictures("large", "medium", "thumbnail");

        actions.addPictures(picturesAjout);

        Pictures pictureTrouvee = actions.getPicturesById(1);

        assertNotNull(pictureTrouvee);
    }

    @Test
    void testAddAddress() throws SQLException{
        Address addressAjout = new Address(1, "Rue de la paix", "Paris",
                "Ile-de-France", "France", "75001");

        actions.addAddress(addressAjout);

        Address addressTrouvee = actions.getAddressById(1);

        assertNotNull(addressTrouvee);
    }

    @Test
    void testSetSalaryByIdPersonneTrouvee() throws SQLException{
        this.createFakePersonne();
        Actions<Manager> managerActions = new Actions<>();

        Programmeur programmeurAjout = new Programmeur(Title.MR, "Projet", "Java", Gender.MALE,
                actions.getPicturesById(1), actions.getAddressById(1),
                actions.getCoordsById(1), "Swiiiip&MrRed",
                managerActions.getById("Manager", 1), Hobbies.PROGRAMMATION,
                2002, 500f, 100f);

        actions.addPersonne(programmeurAjout);

        actions.setSalaryById("Programmeur", 1, 1000f);

        float salaireAttendu = 1000f;

        float salaireTrouve = actions.getById("Programmeur", 1).getSalary();

        assertEquals(salaireAttendu, salaireTrouve);
    }

    @Test
    void testSetSalaryByIdAucunePersonneTrouvee(){
        assertThrows(SQLException.class, () -> actions.setSalaryById("Programmeur",
                10, 1000f));
    }

    @Test
    void testGetCount() throws SQLException {
        this.createFakePersonne();

        int nbPersonneAttendue = 2;

        int nbPersonneTrouvee = actions.getCount("Programmeur");

        assertEquals(nbPersonneAttendue, nbPersonneTrouvee);
    }

    @Test
    void testDeleteById() throws SQLException{
        this.createFakePersonne();

        int nbPersonneAttendueAfterDelete = 1;

        actions.deleteById("Programmeur", 1);

        int nbPersonneTrouvee = actions.getCount("Programmeur");

        assertEquals(nbPersonneAttendueAfterDelete, nbPersonneTrouvee);

        assertThrows(SQLException.class, () -> actions.deleteById("Programmeur", 10));
    }

    @Test
    void testDeleteAll() throws SQLException{
        this.createFakePersonne();

        int nbPersonneAttenduAfterDeleteAll = 0;

        actions.deleteAll("Programmeur");

        int nbPersonneTrouvee = actions.getCount("Programmeur");

        assertEquals(nbPersonneAttenduAfterDeleteAll, nbPersonneTrouvee);
    }

    @Test
    void testDeleteUtils() {
        this.createFakePersonne();

        this.delete();

        assertThrows(SQLException.class, () -> actions.getCoordsById(1));
        assertThrows(SQLException.class, () -> actions.getPicturesById(1));
        assertThrows(SQLException.class, () -> actions.getAddressById(1));
    }

    @Test
    void testGetWithAggregatedSalaryPersonneTrouvee() throws SQLException{
        this.createFakePersonne();

        Programmeur programmeur = actions.getWithAggregatedSalary("Programmeur", "Min");

        assertNotNull(programmeur);
    }

    @Test
    void testGetWithAggregatedSalaryAucunePersonneTrouvee() {
        assertThrows(SQLException.class, () -> actions.getWithAggregatedSalary("Programmeur", "Min"));
    }


    @Test
    void testGetAvgSalaryByAgeDonneesTrouvees() throws SQLException {
        this.createFakePersonne();
        Map<Integer, Float> salaryByAge = actions.getAvgSalaryByAge("Programmeur");

        assertNotNull(salaryByAge);
    }

    @Test
    void testGetAvgSalaryByAgeAucuneDonneesTrouvees(){
        assertThrows(SQLException.class, () -> actions.getAvgSalaryByAge("Programmeur"));
    }

    @Test
    void testGetRankBySalaryDonneesTrouvees() throws SQLException {
        this.createFakePersonne();
        Map<Integer, Programmeur> rankBySalary = actions.getRankBySalary("Programmeur");

        assertNotNull(rankBySalary);
    }

    @Test
    void testGetRankBySalaryAucuneDonneesTrouvees() {
        assertThrows(SQLException.class, () -> actions.getRankBySalary("Programmeur"));
    }

    @Test
    void testGetSalaryHistogramDonneesTrouvees() throws SQLException {
        this.createFakePersonne();
        Map<Float, Integer> salaryHistogram = actions.getSalaryHistogram("Programmeur");

        assertNotNull(salaryHistogram);
    }

    @Test
    void testGetSalaryHistogramAucuneDonneesTrouvees() {
        assertThrows(SQLException.class, () -> actions.getSalaryHistogram("Programmeur"));
    }

    @Test
    void testGetAverageSalaryByGenderDonneesTrouvees() throws SQLException {
        this.createFakePersonne();
        Map<String, Float> averageSalaryByGender = actions.getAverageSalaryByGender("Programmeur");

        assertNotNull(averageSalaryByGender);
    }

    @Test
    void testGetAverageSalaryByGenderAucuneDonneesTrouvees(){
        assertThrows(SQLException.class, () -> actions.getAverageSalaryByGender("Programmeur"));
    }

    @AfterAll
    public static void tearDown() {
        actions.exit();
    }

}