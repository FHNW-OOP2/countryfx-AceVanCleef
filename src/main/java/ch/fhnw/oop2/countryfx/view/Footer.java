package ch.fhnw.oop2.countryfx.view;

import ch.fhnw.oop2.countryfx.presentationmodel.ContinentPM;
import ch.fhnw.oop2.countryfx.presentationmodel.CountryPM;
import ch.fhnw.oop2.countryfx.presentationmodel.RootPM;
import ch.fhnw.oop2.countryfx.view.util.ViewMixin;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Created by Degonas on 29.04.2017.
 */
public class Footer extends TableView<ContinentPM> implements ViewMixin {

    private final RootPM pm;

    public Footer(RootPM pm){
        this.pm = pm;
        init();
    }

    @Override
    public void initializeSelf() {
        this.setItems(pm.getAllContinents());                               // armed with data
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);    // Verteile die Spalten regelmässig in der TabelView
        this.setPrefHeight(300);                                            // Maximale Höhe der TableView
    }

    @Override
    public void initializeParts() {
        //TableColumn<DATENTYP DER ZELLE, ANZEIGETYP DER ZELLE>
        TableColumn<ContinentPM, String> continentCol         = new TableColumn<>("Kontinent");
        TableColumn<ContinentPM, Number> areaCol              = new TableColumn<>("Fläche (km2)");
        TableColumn<ContinentPM, Number> populationCol        = new TableColumn<>("Einwohner");
        TableColumn<ContinentPM, Number> amountOfCountriesCol = new TableColumn<>("#");

        continentCol.setCellValueFactory(cell -> cell.getValue().continentNameProperty());
        areaCol.setCellValueFactory(cell -> cell.getValue().areaProperty());
        populationCol.setCellValueFactory(cell -> cell.getValue().populationProperty());
        amountOfCountriesCol.setCellValueFactory(cell -> cell.getValue().populationProperty());

/*
        continentCol.setCellValueFactory(cell -> cell.getValue().continentProperty());
        areaCol.setCellValueFactory(cell -> cell.getValue().areaProperty());
        populationCol.setCellValueFactory(cell -> cell.getValue().populationProperty());
        amountOfCountriesCol.setCellValueFactory(cell -> cell.getValue().populationProperty());
*/
        /* how it works
        *  [cell -> cell]
        *  Cell is ofType CellDataFeatures: A support class used in TableColumn as a wrapper class to
        *  provide all necessary information for a particular Cell. Once instantiated, this class is immutable.
        *
        *  Note: seems to know that it works on CountryPM (cell.getValue() returns CountryPM)
        *
        * */

        this.getColumns().addAll(continentCol, areaCol, populationCol, amountOfCountriesCol);
        //this.setFixedCellSize(25);
    }

    @Override
    public void layoutParts() {

    }

    @Override
    public void setupBindings() {
        //Todo: Wie auf eine Zelle binden?
        //pm.getProxyContinent().continentNameProperty().bind();
    }
}
