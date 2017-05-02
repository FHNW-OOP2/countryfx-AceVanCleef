package ch.fhnw.oop2.countryfx.presentationmodel;

import ch.fhnw.oop2.countryfx.service.CountryDTO;
import javafx.beans.property.*;

/**
 * Created by Degonas on 29.04.2017.
 */
public class CountryPM {

    private final IntegerProperty   id              = new SimpleIntegerProperty();
    private final StringProperty    name            = new SimpleStringProperty();
    private final StringProperty    name_long       = new SimpleStringProperty();
    private final StringProperty    capital         = new SimpleStringProperty();
    private final IntegerProperty   population      = new SimpleIntegerProperty();
    private final DoubleProperty    area            = new SimpleDoubleProperty();
    private final DoubleProperty    population_km2  = new SimpleDoubleProperty();
    private final StringProperty    flag            = new SimpleStringProperty();
    private final StringProperty    iso_3           = new SimpleStringProperty();
    private final StringProperty    iso_2           = new SimpleStringProperty();
    private final StringProperty    tld             = new SimpleStringProperty();
    private final StringProperty    name_english    = new SimpleStringProperty();
    private final StringProperty    name_local      = new SimpleStringProperty();
    private final StringProperty    continent       = new SimpleStringProperty();


    public CountryPM(CountryDTO dto){
        setId(dto.getID());
        setName(dto.getNAME());
        setName_long(dto.getNAME_LONG());
        setCapital(dto.getCAPITAL());
        setPopulation(dto.getPOPULATION());
        setArea(dto.getAREA());
        setPopulation_km2(dto.getPOPULATION_KM2());
        setFlag(dto.getFLAG());
        setIso_3(dto.getISO_3());
        setIso_2(dto.getISO_2());
        setTld(dto.getTLD());
        setName_english(dto.getNAME_ENGLISH());
        setName_local(dto.getNAME_LOCAL());
        setContinent(dto.getCONTINENT());
    }


    /************************* setters and getters ****************************/

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getName_long() {
        return name_long.get();
    }

    public StringProperty name_longProperty() {
        return name_long;
    }

    public void setName_long(String name_long) {
        this.name_long.set(name_long);
    }

    public String getCapital() {
        return capital.get();
    }

    public StringProperty capitalProperty() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital.set(capital);
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

    public double getArea() {
        return area.get();
    }

    public DoubleProperty areaProperty() {
        return area;
    }

    public void setArea(double area) {
        this.area.set(area);
    }

    public double getPopulation_km2() {
        return population_km2.get();
    }

    public DoubleProperty population_km2Property() {
        return population_km2;
    }

    public void setPopulation_km2(double population_km2) {
        this.population_km2.set(population_km2);
    }

    public String getFlag() {
        return flag.get();
    }

    public StringProperty flagProperty() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag.set(flag);
    }

    public String getIso_3() {
        return iso_3.get();
    }

    public StringProperty iso_3Property() {
        return iso_3;
    }

    public void setIso_3(String iso_3) {
        this.iso_3.set(iso_3);
    }

    public String getIso_2() {
        return iso_2.get();
    }

    public StringProperty iso_2Property() {
        return iso_2;
    }

    public void setIso_2(String iso_2) {
        this.iso_2.set(iso_2);
    }

    public String getTld() {
        return tld.get();
    }

    public StringProperty tldProperty() {
        return tld;
    }

    public void setTld(String tld) {
        this.tld.set(tld);
    }

    public String getName_english() {
        return name_english.get();
    }

    public StringProperty name_englishProperty() {
        return name_english;
    }

    public void setName_english(String name_english) {
        this.name_english.set(name_english);
    }

    public String getName_local() {
        return name_local.get();
    }

    public StringProperty name_localProperty() {
        return name_local;
    }

    public void setName_local(String name_local) {
        this.name_local.set(name_local);
    }

    public String getContinent() {
        return continent.get();
    }

    public StringProperty continentProperty() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent.set(continent);
    }
}
