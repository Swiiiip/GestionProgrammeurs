package dao;

import personnes.Personne;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface PersonneDAO<T extends Personne> {
    List<T> getAll() throws SQLException;

    T getById(int id) throws SQLException;

    T getByFullName(String lastName, String firstName) throws SQLException;

    void add(T personne) throws SQLException;

    void setSalaryById(int id, float newSalary) throws SQLException;

    int getCount() throws SQLException;

    T getWithMaxSalary() throws SQLException;

    T getWithMinSalary() throws SQLException;

    Map<Integer, Float> getAvgSalaryByAge() throws SQLException;

    Map<Integer, T> getRankBySalary() throws SQLException;

    Map<Float, Integer> getSalaryHistogram() throws SQLException;

    double getCorrelationBetweenAgeAndSalary() throws SQLException;

    void deleteById(int id) throws SQLException;

    void deleteAll() throws SQLException;

    void resetIndex() throws SQLException;

    void exit();
}
