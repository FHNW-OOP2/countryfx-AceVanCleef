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

    @Test
    public void testcreateNewCountry(){
        //given
        CountryServiceFileBased service =  new CountryServiceFileBased();
        RootPM pm = new RootPM(service);
        int oldSize = pm.getAllCountries().size();

        //when
        pm.createNewCountry();

        //then
        assertEquals(oldSize + 1 ,pm.getAllCountries().size());

    }

    @Test
    public void testRemoveSelectedCountry(){
        /*** removeFirstElement ***/
        //given
        CountryServiceFileBased service =  new CountryServiceFileBased();
        RootPM pm = new RootPM(service);
        int oldSize = pm.getAllCountries().size();
        assertEquals("Abchasien", pm.getAllCountries().get(0).getName());

        //when
        pm.removeSelectedCountry(); //selectedCountryId == 1 (default)

        //then
        assertEquals(oldSize - 1 ,pm.getAllCountries().size());
        assertEquals("Afghanistan", pm.getAllCountries().get(0).getName());


        /*** removeLastElement ***/
        //given
        pm = new RootPM(service);
        oldSize = pm.getAllCountries().size();
        assertEquals("Zypern mit Nordzypern", pm.getAllCountries().get(oldSize - 1).getName());

        //when
        pm.setSelectedCountryId(oldSize);
        pm.removeSelectedCountry(); //Zypern mit Nordzypern

        //then
        assertEquals(oldSize - 1 ,pm.getAllCountries().size());
        int newSize = oldSize - 1;
        assertEquals("Zentralafrikanische Republik", pm.getAllCountries().get(newSize - 1).getName());


        /*** removeElementInBetween ***/
        //given
        pm = new RootPM(service);
        int deleteIndex = 200;
        assertEquals("Vereinigte Staaten ohne Außengebiete", pm.getAllCountries().get(deleteIndex - 1).getName());

        //when
        pm.setSelectedCountryId(deleteIndex);
        pm.removeSelectedCountry(); //Vereinigte Staaten ohne Außengebiete

        //then
        assertEquals(oldSize - 1 ,pm.getAllCountries().size());
        int newIndex = deleteIndex - 1;
        assertEquals("Vereinigte Arabische Emirate", pm.getAllCountries().get(newIndex - 1).getName());

        //delete a second time:

        //when
        pm.setSelectedCountryId(newIndex);
        pm.removeSelectedCountry(); //Vereinigte Arabische Emirate

        //then
        assertEquals(oldSize - 2 ,pm.getAllCountries().size());
        assertEquals("Vereinigtes Königreich ohne Überseegebiete und Kronbesitzungen",
                pm.getAllCountries().get(newIndex - 1).getName());
    }
}