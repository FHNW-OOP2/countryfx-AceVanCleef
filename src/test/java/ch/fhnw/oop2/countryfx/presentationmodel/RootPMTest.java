package ch.fhnw.oop2.countryfx.presentationmodel;

import ch.fhnw.oop2.countryfx.service.serviceimpl.CountryServiceFileBased;
import org.junit.Before;
import org.junit.Test;

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

}