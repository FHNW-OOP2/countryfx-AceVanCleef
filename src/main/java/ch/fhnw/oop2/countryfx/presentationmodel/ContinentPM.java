package ch.fhnw.oop2.countryfx.presentationmodel;

import javafx.beans.property.*;

/**
 * Created by Stefan on 20.05.2017.
 */
public class ContinentPM {
    //1 ContinentPM = eine Reihe in der View. Aber nur dessen Daten hier gespeichert zur Laufzeit.

    private final StringProperty continentName = new SimpleStringProperty();
    private final DoubleProperty area = new SimpleDoubleProperty();
    private final IntegerProperty population = new SimpleIntegerProperty();
    private final IntegerProperty amountOfCountries = new SimpleIntegerProperty();

    public ContinentPM(String continentName, double area, int population, int amountOfCountries){
        setContinentName(continentName);
        setArea(area);
        setPopulation(population);
        setAmountOfCountries(amountOfCountries);
    }

    //für proxyContinent
    public ContinentPM() {
    }

    public String getContinentName() {
        return continentName.get();
    }

    public StringProperty continentNameProperty() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName.set(continentName);
    }

    public double getArea() {
        return area.get();
    }

    public DoubleProperty areaProperty() {
        return area;
    }

    public void setArea(double area) {
        this.area.set(area);
    }

    public int getPopulation() {
        return population.get();
    }

    public IntegerProperty populationProperty() {
        return population;
    }

    public void setPopulation(int population) {
        this.population.set(population);
    }

    public int getAmountOfCountries() {
        return amountOfCountries.get();
    }

    public IntegerProperty amountOfCountriesProperty() {
        return amountOfCountries;
    }

    public void setAmountOfCountries(int amountOfCountries) {
        this.amountOfCountries.set(amountOfCountries);
    }
}
