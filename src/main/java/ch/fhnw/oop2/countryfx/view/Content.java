package ch.fhnw.oop2.countryfx.view;

import ch.fhnw.oop2.countryfx.presentationmodel.RootPM;
import ch.fhnw.oop2.countryfx.view.util.ViewMixin;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;

/**
 * Created by Degonas on 29.04.2017.
 */
public class Content extends SplitPane implements ViewMixin{

    //private CountryList allCountries;
    //todo: intermediatePane.getchildren().addAll(countryCountryOverview, countryCountryEditor) necessary???
    private CountryOverview countryOverview;
    private CountryEditor countryEditor;

    private RootPM pm;

    private HBox test1;
    private HBox test2;

    public Content(RootPM pm){
        this.pm = pm;
    }

    @Override
    public void initializeSelf() {
        this.setOrientation(Orientation.VERTICAL);
        this.setDividerPositions(0.3, 0.6, 0.9);
    }

    @Override
    public void initializeParts() {
        test1 = new HBox(new Button("Blah"));
        test2 = new HBox(new Button("ta"));
        test1.setTranslateX(20);
        test1.setTranslateY(20);
        test2.setTranslateX(20);
        test2.setTranslateY(20);
    }

    @Override
    public void layoutParts() {
        this.getItems().addAll(test1, test2);
    }
}
