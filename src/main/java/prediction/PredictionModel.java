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

public class PredictionModel<T extends PersonneDAO<? extends Personne>> {

    private final Instances data;
    private final LinearRegression model;

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
            String gender = personne.getGender().getGender();
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

    public float predictSalary(double predictAge, String predictGender) throws Exception {
        DenseInstance nouvelleInstance = new DenseInstance(3);
        nouvelleInstance.setDataset(data);

        nouvelleInstance.setValue(0, predictAge);
        nouvelleInstance.setValue(1, predictGender);

        double prediction = model.classifyInstance(nouvelleInstance);

        return (float) prediction;
    }
}
