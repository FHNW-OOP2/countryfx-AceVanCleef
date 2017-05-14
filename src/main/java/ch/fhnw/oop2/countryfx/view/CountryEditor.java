package ch.fhnw.oop2.countryfx.view;

import ch.fhnw.oop2.countryfx.presentationmodel.RootPM;
import ch.fhnw.oop2.countryfx.view.util.ViewMixin;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

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

    private TextField nameField;
    private TextField nameLongField;
    private TextField continentField;
    private TextField areaField;
    private TextField populationPerAreaField;
    private TextField capitalField;
    private TextField populationField;
    private TextField topLevelDomainField;



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


        nameField               = new TextField();
        nameLongField           = new TextField();
        areaField               = new TextField();
        capitalField            = new TextField();
        populationField         = new TextField();
        topLevelDomainField     = new TextField();
        continentField          = new TextField();
        populationPerAreaField  = new TextField();
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

        //adding TextFields
        add(nameField, 1,0, 3,1);
        add(nameLongField, 1,1, 3,1);
        add(continentField, 1,2);
        add(areaField, 1,3);
        add(populationPerAreaField, 1,4);
        add(capitalField, 3,2);
        add(populationField, 3,3);
        add(topLevelDomainField, 3,4);

    }
}
