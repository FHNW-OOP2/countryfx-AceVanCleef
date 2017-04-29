package ch.fhnw.oop2.countryfx.view;

import javafx.scene.control.SplitPane;

/**
 * Created by Degonas on 29.04.2017.
 */
public class Content extends SplitPane {

    private CountryList allCountries;
    //todo: intermediatePane.getchildren().addAll(countryOverview, countryEditor) necessary???
    private Overview countryOverview;
    private Editor countryEditor;
}
