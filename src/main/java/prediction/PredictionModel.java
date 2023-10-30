package prediction;

import dao.PersonneDAO;
import personnes.Personne;
import utils.Gender;
import weka.classifiers.functions.LinearRegression;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Cette classe représente un modèle de prédiction de salaire basé sur des données de personnes.
 *
 * @param <T> Le type de DAO (Data Access Object) utilisé pour accéder aux données de personnes.
 *
 * @version 4.7
 * @author Alonso Cédric
 * @author Hatoum Jade
 */
public class PredictionModel<T extends PersonneDAO<? extends Personne>> {

    /**
     * Représente un ensemble de données (Instances) utilisé pour construire et évaluer le modèle de prédiction.
     */
    private final Instances data;

    /**
     * Le modèle de régression linéaire utilisé pour prédire les salaires en fonction des données.
     */
    private final LinearRegression model;


    /**
     * Construit un modèle de prédiction de salaire à partir des données extraites à l'aide d'un DAO de personnes.
     *
     * @param personneDAO Le DAO de personnes pour accéder aux données.
     * @throws Exception Si le nombre de données est insuffisant (moins de 20) pour construire le modèle.
     */
    public PredictionModel(T personneDAO) throws Exception {
        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("age"));
        Attribute genreAttribute = new Attribute("gender",
                Arrays.asList(Gender.MALE.getGender(), Gender.FEMALE.getGender()));
        attributes.add(genreAttribute);
        attributes.add(new Attribute("salary"));

        data = new Instances("personne_data", attributes, 0);
        data.setClassIndex(data.numAttributes() - 1);

        List<? extends Personne> personnes = personneDAO.getAll();
        for (Personne personne : personnes) {
            double age = personne.getAge();
            String gender = personne.getGender();
            float salary = personne.getSalary();

            DenseInstance instance = new DenseInstance(3);
            instance.setDataset(data);
            instance.setValue(attributes.get(0), age);
            instance.setValue(attributes.get(1), gender);
            instance.setValue(attributes.get(2), salary);

            data.add(instance);
        }

        if (data.size() < 20)
            throw new Exception("un minimum de 20 données est requis");

        model = new LinearRegression();
        model.buildClassifier(data);
    }

    /**
     * Prédit le salaire en fonction de l'âge et du genre fournis.
     *
     * @param predictAge    L'âge pour lequel prédire le salaire.
     * @param predictGender Le genre pour lequel prédire le salaire.
     * @return Le salaire prédit en fonction des données du modèle.
     * @throws Exception Si la prédiction échoue pour une raison quelconque.
     */
    public float predictSalary(double predictAge, String predictGender) throws Exception {
        DenseInstance nouvelleInstance = new DenseInstance(3);
        nouvelleInstance.setDataset(data);

        nouvelleInstance.setValue(0, predictAge);
        nouvelleInstance.setValue(1, predictGender);

        double prediction = model.classifyInstance(nouvelleInstance);

        return (float) prediction;
    }
}
