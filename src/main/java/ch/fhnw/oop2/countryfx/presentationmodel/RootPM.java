package ch.fhnw.oop2.countryfx.presentationmodel;

import ch.fhnw.oop2.countryfx.service.CountryService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.stream.Collectors;

public class RootPM {
    private final StringProperty applicationTitle = new SimpleStringProperty("CountryFX");


    private final ObservableList<CountryPM> allCountries = FXCollections.observableArrayList(); // Alle PMs in einer Collection/Liste
    private final CountryService service;

    public RootPM(CountryService service){
        this.service = service;

        allCountries.addAll(service.findAll().stream()
                .map(dto -> new CountryPM(dto))         //Mapping von DTOs zu PMs
                .collect(Collectors.toList()));         //Alle PMs zur ObservableList
    }

    /********************* getters and setters ***********************/

    public String getApplicationTitle() {
        return applicationTitle.get();
    }
    public StringProperty applicationTitleProperty() {
        return applicationTitle;
    }
    public void setApplicationTitle(String applicationTitle) {
        this.applicationTitle.set(applicationTitle);
    }

    public ObservableList<CountryPM> getAllCountries() {
        return allCountries;
    }
}
