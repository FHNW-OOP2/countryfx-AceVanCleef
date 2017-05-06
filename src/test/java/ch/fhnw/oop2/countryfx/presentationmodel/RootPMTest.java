package ch.fhnw.oop2.countryfx.presentationmodel;

import ch.fhnw.oop2.countryfx.service.serviceimpl.CountryServiceFileBased;
import org.junit.Test;

import static org.junit.Assert.*;


public class RootPMTest {

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
}