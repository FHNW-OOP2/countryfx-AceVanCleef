package ch.fhnw.oop2.countryfx.presentationmodel;

import ch.fhnw.oop2.countryfx.service.serviceimpl.CountryServiceFileBased;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.Collectors;

import static org.junit.Assert.*;


public class RootPMTest {

    private final static String TEST_CONTINENT = "Australien/Ozeanien";

    @Test
    public void testSetup(){
        //given
        CountryServiceFileBased service =  new CountryServiceFileBased();

        //when
        RootPM pm = new RootPM(service);

        //then
        assertNotNull(pm.getAllCountries());
        assertEquals(206, pm.getAllCountries().size());
    }

    @Test
    public void testGetContinentArea(){
        //given
        CountryServiceFileBased service =  new CountryServiceFileBased();

        //when
        RootPM pm = new RootPM(service);

        //then
        double area = 8537403;
        double abweichung = 0.5;
        assertEquals(area, pm.getContinentArea(TEST_CONTINENT), abweichung);
    }

    @Test
    public void testGetContinentPopulation(){
        //given
        CountryServiceFileBased service =  new CountryServiceFileBased();

        //when
        RootPM pm = new RootPM(service);

        //then
        int population = 38643729;
        assertEquals(population,(int) pm.getContinentPopulation(TEST_CONTINENT));
    }

    @Test
    public void testGetContinentAmountOfCountries(){
        //given
        CountryServiceFileBased service =  new CountryServiceFileBased();

        //when
        RootPM pm = new RootPM(service);

        //then
        int number = 16;
        assertEquals(number,(int) pm.getContinentAmountOfCountries(TEST_CONTINENT));
    }

    @Test
    public void testAddValueChangeListener(){
        //given

        CountryServiceFileBased service =  new CountryServiceFileBased();
        RootPM pm = new RootPM(service);
        //the country which will be changed
        CountryPM currentCountry = pm.getCurrentCountry();
        //get the affected continent
        ContinentPM currentContinent = pm.getAllContinents()
                .stream()
                .filter(continentPM ->  continentPM.getContinentName().equals(currentCountry.getContinent()))
                .distinct()
                .findFirst()
                .get();

        //when
        int oldPopulation = currentCountry.getPopulation();
        int oldContinentPopulation = currentContinent.getPopulation();

        //then
        assertEquals(oldPopulation, currentCountry.getPopulation());
        assertEquals(oldContinentPopulation, currentContinent.getPopulation());

        //given (ValueChange)
        int newPopulation = oldPopulation + 1;

        //when
        currentCountry.setPopulation(newPopulation);    //should trigger value change listener of allCountries

        //then
        assertEquals(newPopulation, currentCountry.getPopulation());
        assertEquals(oldContinentPopulation + 1 ,currentContinent.getPopulation());


        //todo: reagiert auf .remove()? (=löschen)
    }
}