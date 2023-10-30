package data.export;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Map;

/**
 * Classe abstraite qui définit un modèle générique pour l'export de données.
 * Cette classe sert de base pour les différentes implémentations d'exporteurs de données.
 *
 * @version 4.7
 * @author Alonso Cédric
 * @author Hatoum Jade
 */
public abstract class Exporter {

    /**
     * Le logger utilisé pour enregistrer les messages relatifs à cette classe.
     */
    protected static final Logger logger = LoggerFactory.getLogger(Exporter.class);

    /**
     * Les données à exporter, sous forme de liste de mappages clé-valeur.
     */
    protected List<Map<String, Object>> dataToExport;

    /**
     * Constructeur de la classe Exporter.
     *
     * @param data Les données à exporter, sous forme de liste de mappages clé-valeur.
     */
    public Exporter(List<Map<String, Object>> data) {
        this.dataToExport = data;
    }

    /**
     * Méthode abstraite qui doit être implémentée par les sous-classes pour effectuer
     * l'export des données vers un fichier spécifié.
     *
     * @param fileName Le nom du fichier dans lequel les données doivent être exportées.
     */
    public abstract void export(String fileName);
}
