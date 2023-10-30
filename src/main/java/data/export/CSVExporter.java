package data.export;

import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Une classe qui étend la classe abstraite Exporter pour exporter des données vers un fichier CSV.
 * Cette classe spécifique gère l'exportation des données vers un fichier CSV en utilisant la bibliothèque opencsv.
 *
 * @version 4.7
 * @author Alonso Cédric
 * @author Hatoum Jade
 */
public class CSVExporter extends Exporter {

    /**
     * Constructeur de la classe CSVExporter.
     *
     * @param data Les données à exporter, sous forme de liste de mappages clé-valeur.
     */
    public CSVExporter(List<Map<String, Object>> data) {
        super(data);
    }

    /**
     * Méthode pour exporter les données vers un fichier CSV.
     *
     * @param fileName Le nom du fichier CSV dans lequel les données doivent être exportées.
     */
    @Override
    public void export(String fileName) {
        String userHome = System.getProperty("user.home");
        String downloadDir = Paths.get(userHome, "Downloads").toString();

        LocalDateTime now = LocalDateTime.now();
        String dateTime = now.getDayOfMonth() + "-" + now.getMonthValue() + "-" + now.getYear() + "_" +
                now.getHour() + "h" + now.getMinute() + "min" + now.getSecond() + "s";

        String csvFilePath = Paths.get(downloadDir, fileName + "_" + dateTime + ".csv").toString();

        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))) {
            if (!dataToExport.isEmpty()) {
                Map<String, Object> firstRow = dataToExport.get(0);
                String[] header = firstRow.keySet().toArray(new String[0]);
                writer.writeNext(header);

                for (Map<String, Object> map : dataToExport) {
                    String[] row = new String[header.length];
                    for (int i = 0; i < header.length; i++) {
                        row[i] = map.get(header[i]).toString();
                    }
                    writer.writeNext(row);
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
