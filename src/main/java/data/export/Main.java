package data.export;

import dao.ManagerDAO;
import dao.ProgrammeurDAO;
import data.generator.DataGenerator;
import personnes.Manager;
import personnes.Programmeur;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws SQLException {
        ManagerDAO managerDAO = new ManagerDAO();
        ProgrammeurDAO programmeurDAO = new ProgrammeurDAO();

        new DataGenerator(5,2);

        List<Manager> managers = managerDAO.getAll();
        List<Programmeur> programmeurs = programmeurDAO.getAll();

        List<Map<String, Object>> data = new ArrayList<>();

        for (Manager manager : managers)
            data.add(manager.getColumns());

        new CSVExporter(data).export(managerDAO.getTypeLabel() + 's');

        new PDFExporter(data).export(managerDAO.getTypeLabel() + 's');

        data.clear();

        for (Programmeur programmeur : programmeurs)
            data.add(programmeur.getColumns());

        new CSVExporter(data).export(programmeurDAO.getTypeLabel() + 's');

        new PDFExporter(data).export(programmeurDAO.getTypeLabel() + 's');
    }
}
