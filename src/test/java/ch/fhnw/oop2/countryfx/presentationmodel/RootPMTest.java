package ch.fhnw.oop2.countryfx.presentationmodel;

import ch.fhnw.oop2.countryfx.service.CountryDTO;
import ch.fhnw.oop2.countryfx.service.serviceimpl.CountryServiceFileBased;
import ch.fhnw.oop2.countryfx.service.serviceimpl.CountryServiceFileBasedJunit;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.Collectors;

import static org.junit.Assert.*;


public class RootPMTest {

    private static  CountryServiceFileBasedJunit service;

    private final static String TEST_CONTINENT = "Australien/Ozeanien";

    /**
     * will be called each time before a testXY() is invoked (Reduces redundant code).
     */
    @Before
    public void setUp(){
        /* To guarantee always working unit tests (countriesSource.cvs will never be changed by IO save operations) */
        service = new CountryServiceFileBasedJunit();
    }

    @Test
    public void testSetup(){
        //given
        //see @Before setUp()
        System.out.println(service.findAll().get(0).getNAME()); // == Abchasien$

        //when
        RootPM pm = new RootPM(service);

        //then
        assertNotNull(pm.getAllCountries());
        assertEquals(206, pm.getAllCountries().size());
        //Does it get countriesSource.csv?
        assertEquals("Abchasien$", service.findAll().get(0).getNAME());
    }

    @Test
    public void testGetContinentArea(){
        //given
        //see @Before setUp()

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
        //see @Before setUp()

        //when
        RootPM pm = new RootPM(service);

        //then
        int population = 38643729;
        assertEquals(population,(int) pm.getContinentPopulation(TEST_CONTINENT));
    }

    @Test
    public void testGetContinentAmountOfCountries(){
        //given
        //see @Before setUp()

        //when
        RootPM pm = new RootPM(service);

        //then
        int number = 16;
        assertEquals(number,(int) pm.getContinentAmountOfCountries(TEST_CONTINENT));
    }

    @Test
    public void AddValueChangeListenerForContinentInfo(){
        fail();
    }

    @Test
    public void AddContinentSelectionListener(){
        //given
        RootPM pm = new RootPM(service);
        //todo
        //when

        //then

    }

    @Test
    public void testcreateNewCountry(){
        //given
        //see @Before setUp()
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
        //see @Before setUp()
        RootPM pm = new RootPM(service);
        int oldSize = pm.getAllCountries().size();
        assertEquals("Abchasien$", pm.getAllCountries().get(0).getName());

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

    @Test
    public void testAddSelectionChangeListener(){
        /* setup check */
        //given
        //see @Before setUp()
        RootPM pm = new RootPM(service);

        //when
        CountryPM firstCountry = pm.getCurrentCountry();

        //then
        assertEquals(firstCountry.getName(), pm.getCountryProxy().getName());

        /* when changing selection */
        //given
        int newSelectionId = 2;

        //when
        pm.setSelectedCountryId(newSelectionId);

        //then
        assertNotEquals(firstCountry.getName_long(), pm.getCountryProxy().getName_long());
        assertEquals(pm.getCurrentCountry().getName_long(), pm.getCountryProxy().getName_long());
    }

    /**
     * Just trying out how String.contains() works.
     */
    @Test
    public void testStringContians(){
        String test = "Asien, Europa";
        assertTrue(test.contains("Asien"));
        assertTrue(test.contains("Europa"));
    }

    @Test
    public void testGetCurrentFlagURL(){
        //given
        //see @Before setUp()
        RootPM pm = new RootPM(service);
        String flagImgSize = "64/";

        //when
        String imgURL = pm.getCurrentFlagURL(flagImgSize);

        //then
        String desiredURL = "https://dieterholz.github.io/StaticResources/flags_iso/" + flagImgSize +
                pm.getCountryProxy().getIso_2().toLowerCase() + ".png";
        assertEquals(desiredURL, imgURL);


        /*--- Spezialfälle: imgSize == null oder "" ---*/
        //given
        flagImgSize = "";

        //when
        imgURL = pm.getCurrentFlagURL(flagImgSize);

        //then
        String standInSize = "24/";
        desiredURL = "https://dieterholz.github.io/StaticResources/flags_iso/" + standInSize +
                pm.getCountryProxy().getIso_2().toLowerCase() + ".png";
        assertEquals(desiredURL, imgURL);

        //given
        flagImgSize = null;

        //when
        imgURL = pm.getCurrentFlagURL(flagImgSize);

        //then
        standInSize = "24/";
        desiredURL = "https://dieterholz.github.io/StaticResources/flags_iso/" + standInSize +
                pm.getCountryProxy().getIso_2().toLowerCase() + ".png";
        assertEquals(desiredURL, imgURL);


        /*--- Spezialfall: countryProxy hat keinen iso_2 - Wert ---*/
        //given
        flagImgSize = "128/";
        pm.createNewCountry();  //hat noch keinen iso_2 Wert

        //when
        imgURL = pm.getCurrentFlagURL(flagImgSize);

        //then
        desiredURL = "https://dieterholz.github.io/StaticResources/flags_iso/" + flagImgSize +
                "_United-Nations" + ".png";
        assertEquals(desiredURL, imgURL);
    }

    @Test
    public void testSaveToFile(){
        //given
        RootPM pm = new RootPM(service);

        //when
        String query = "Pluto";
        pm.getCurrentCountry().setName(query);
        pm.saveToFile();

        //then
        CountryDTO first = service.findAllFromTarget().get(1);
        assertEquals(query, first);


    }
}