package ch.fhnw.oop2.countryfx.view;

import ch.fhnw.oop2.countryfx.presentationmodel.CountryPM;
import ch.fhnw.oop2.countryfx.presentationmodel.RootPM;
import ch.fhnw.oop2.countryfx.view.util.ViewMixin;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.util.converter.NumberStringConverter;

/**
 * Created by Degonas on 29.04.2017.
 */
public class CountryEditor extends GridPane implements ViewMixin{

    private RootPM pm;

    private Label nameLabel;
    private Label nameLongLabel;
    private Label continentLabel;
    private Label areaLabel;
    private Label populationPerAreaLabel;
    private Label capitalLabel;
    private Label populationLabel;
    private Label topLevelDomainLabel;

    private Label    iso_3Label;
    private Label    iso_2Label;
    private Label    tldLabel;
    private Label    name_englishLabel;
    private Label    name_localLabel;

    private TextField nameField;
    private TextField nameLongField;
    private TextField continentField;
    private TextField areaField;
    private TextField populationPerAreaField;
    private TextField capitalField;
    private TextField populationField;
    private TextField topLevelDomainField;

    private TextField    iso_3Field;
    private TextField    iso_2Field;
    private TextField    tldField;
    private TextField    name_englishField;
    private TextField    name_localField;


    public CountryEditor(RootPM pm){
        this.pm = pm;
        init();

        //for debuggin
        this.gridLinesVisibleProperty().set(true);
    }

    @Override
    public void initializeParts() {
        nameLabel               = new Label("Staat");
        nameLongLabel           = new Label("Langform des Staatsnamens");
        continentLabel          = new Label("Kontinent");
        areaLabel               = new Label("Fläche (km2)");
        populationPerAreaLabel  = new Label("Einwohner pro km2");
        capitalLabel            = new Label("Hauptstadt");
        populationLabel         = new Label("Einwohner");
        topLevelDomainLabel     = new Label("Top Level Domain");

        iso_3Label         = new Label("ISO 3");
        iso_2Label         = new Label("ISO 2");
        tldLabel           = new Label("TLD");
        name_englishLabel  = new Label("English Name");
        name_localLabel    = new Label("Local Name");

        nameField               = new TextField();
        nameLongField           = new TextField();
        areaField               = new TextField();
        capitalField            = new TextField();
        populationField         = new TextField();
        topLevelDomainField     = new TextField();
        continentField          = new TextField();
        populationPerAreaField  = new TextField();

        iso_3Field          = new TextField();
        iso_2Field          = new TextField();
        tldField            = new TextField();
        name_englishField   = new TextField();
        name_localField     = new TextField();
    }

    @Override
    public void layoutParts() {
        // Growth vertically (Höhe)
        RowConstraints rc = new RowConstraints();
        rc.setVgrow(Priority.ALWAYS);
        getRowConstraints().addAll(rc, rc, rc, rc, rc, rc, rc, rc);

        // Growth horizontally (Breite)
        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow(Priority.ALWAYS);
        getColumnConstraints().addAll(cc, cc, cc, cc);

        //adding labels
        add(nameLabel, 0,0);
        add(nameLongLabel, 0,1);
        add(continentLabel, 0,2);
        add(areaLabel, 0,3);
        add(populationPerAreaLabel, 0,4);
        add(capitalLabel, 2,2);
        add(populationLabel, 2,3);
        add(topLevelDomainLabel, 2,4);

        add(iso_3Label, 0,5);
        add(iso_2Label, 2,5);
        add(tldLabel, 0,6);
        add(name_englishLabel, 2,6);
        add(name_localLabel, 0,7);

        //adding TextFields
        add(nameField, 1,0, 3,1);
        add(nameLongField, 1,1, 3,1);
        add(continentField, 1,2);
        add(areaField, 1,3);
        add(populationPerAreaField, 1,4);
        add(capitalField, 3,2);
        add(populationField, 3,3);
        add(topLevelDomainField, 3,4);

        add(iso_3Field, 1,5);
        add(iso_2Field, 3,5);
        add(tldField, 1,6);
        add(name_englishField, 3,6);
        add(name_localField, 1,7, 3,1);

    }

    @Override
    public void setupBindings() {
        bind();
    }

    @Override
    public void addValueChangedListeners() {
        pm.selectedCountryIdProperty().addListener((observable, oldValue, newValue) -> {
            //unbind
            CountryPM previousCountry = pm.getCountry(oldValue.intValue());
            unbind(previousCountry);
            //rebinding
            bind();
        });

    }

    private void unbind(CountryPM previousCountry){
        nameField.textProperty().unbindBidirectional(previousCountry.nameProperty());
        nameLongField.textProperty().unbindBidirectional(previousCountry.name_longProperty());
        continentField.textProperty().unbindBidirectional(previousCountry.continentProperty());
        areaField.textProperty().unbindBidirectional(previousCountry.areaProperty().asString());
        populationPerAreaField.textProperty().unbindBidirectional(previousCountry.population_km2Property().asString());
        capitalField.textProperty().unbindBidirectional(previousCountry.capitalProperty());
        populationField.textProperty().unbindBidirectional(previousCountry.populationProperty().asString());
        //topLevelDomainField.textProperty().unbindBidirectional(previousCountry...);  //todo

        iso_3Field.textProperty().unbindBidirectional(previousCountry.iso_3Property());
        iso_2Field.textProperty().unbindBidirectional(previousCountry.iso_2Property());
        tldField.textProperty().unbindBidirectional(previousCountry.tldProperty());
        name_englishField.textProperty().unbindBidirectional(previousCountry.name_englishProperty());
        name_localField.textProperty().unbindBidirectional(previousCountry.name_localProperty());
    }

    private void bind(){
        CountryPM currentCountry = pm.getCurrentCountry();
        //initiale bindings
        nameField.textProperty().bindBidirectional(currentCountry.nameProperty());
        nameLongField.textProperty().bindBidirectional(currentCountry.name_longProperty());
        continentField.textProperty().bindBidirectional(currentCountry.continentProperty());
        areaField.textProperty().bindBidirectional(currentCountry.areaProperty(), new NumberStringConverter());
        populationPerAreaField.textProperty().bindBidirectional(currentCountry.population_km2Property(), new NumberStringConverter());
        capitalField.textProperty().bindBidirectional(currentCountry.capitalProperty());
        populationField.textProperty().bindBidirectional(currentCountry.populationProperty(), new NumberStringConverter());
        //topLevelDomainField.textProperty().bindBidirectional(currentCountry...);  //todo

        iso_3Field.textProperty().bindBidirectional(currentCountry.iso_3Property());
        iso_2Field.textProperty().bindBidirectional(currentCountry.iso_2Property());
        tldField.textProperty().bindBidirectional(currentCountry.tldProperty());
        name_englishField.textProperty().bindBidirectional(currentCountry.name_englishProperty());
        name_localField.textProperty().bindBidirectional(currentCountry.name_localProperty());
    }
}
