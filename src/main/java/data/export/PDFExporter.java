package data.export;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Une classe qui étend la classe abstraite Exporter pour exporter des données vers un fichier PDF.
 * Cette classe spécifique gère l'exportation des données vers un fichier PDF en utilisant la bibliothèque iText.
 *
 * @version 4.7
 * @author Alonso Cédric
 * @author Hatoum Jade
 */
public class PDFExporter extends Exporter {

    /**
     * Constructeur de la classe PDFExporter.
     *
     * @param data Les données à exporter, sous forme de liste de mappages clé-valeur.
     */
    public PDFExporter(List<Map<String, Object>> data) {
        super(data);
    }

    /**
     * Méthode pour exporter les données vers un fichier PDF.
     *
     * @param fileName Le nom du fichier PDF dans lequel les données doivent être exportées.
     */
    @Override
    public void export(String fileName) {
        String userHome = System.getProperty("user.home");
        String downloadDir = Paths.get(userHome, "Downloads").toString();

        LocalDateTime now = LocalDateTime.now();
        String dateTime = now.getDayOfMonth() + "-" + now.getMonthValue() + "-" + now.getYear() + "_" +
                now.getHour() + "h" + now.getMinute() + "min" + now.getSecond() + "s";

        String pdfFilePath = Paths.get(downloadDir, fileName + "_" + dateTime + ".pdf").toString();

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));
            document.open();

            String text = "Voici la liste de tous les " + fileName + " :";
            PdfPCell textCell = new PdfPCell(new Phrase(text));
            textCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            textCell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
            textCell.setBorder(PdfPCell.NO_BORDER);
            textCell.setPaddingBottom(10f);

            PdfPTable textTable = new PdfPTable(1);
            textTable.addCell(textCell);
            document.add(textTable);

            for (Map<String, Object> map : dataToExport) {
                PdfPTable dataTable = new PdfPTable(2); // 2 colonnes : clé et valeur

                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    PdfPCell keyCell = new PdfPCell(new Phrase(entry.getKey()));
                    keyCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    keyCell.setBorder(PdfPCell.NO_BORDER);

                    PdfPCell valueCell = new PdfPCell(new Phrase(entry.getValue().toString()));
                    valueCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    valueCell.setBorder(PdfPCell.NO_BORDER);

                    dataTable.addCell(keyCell);
                    dataTable.addCell(valueCell);
                }

                // Ajouter une ligne vide pour créer de l'espace
                PdfPCell emptyCell = new PdfPCell(new Phrase(" "));
                emptyCell.setColspan(2);
                emptyCell.setBorder(PdfPCell.NO_BORDER);
                dataTable.addCell(emptyCell);

                PdfPCell separatorCell = new PdfPCell();
                separatorCell.setColspan(2);
                separatorCell.setBorder(PdfPCell.BOTTOM);
                dataTable.addCell(separatorCell);

                dataTable.addCell(emptyCell);

                document.add(dataTable);
            }
        } catch (DocumentException | IOException e) {
            logger.error(e.getMessage());
        } finally {
            document.close();
        }
    }
}
