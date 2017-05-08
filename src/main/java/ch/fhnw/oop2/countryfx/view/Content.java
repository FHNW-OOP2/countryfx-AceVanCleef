package ch.fhnw.oop2.countryfx.view;

import ch.fhnw.oop2.countryfx.presentationmodel.RootPM;
import ch.fhnw.oop2.countryfx.view.util.ViewMixin;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;

/**
 * Created by Degonas on 29.04.2017.
 *
 * Reasonable SplitPane tutorial:
 * http://fxexperience.com/2011/06/splitpane-in-javafx-2-0/
 *
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
        init();         //<--- do NOT forget this!
    }

    @Override
    public void initializeSelf() {
        this.setOrientation(Orientation.HORIZONTAL);
        //this.setDividerPositions(0.7, 0.3);
        this.setPrefSize(200, 200);
    }

    @Override
    public void initializeParts() {
        test1 = new HBox(new Button("Left"));
        test2 = new HBox(new Button("Right"));
        test1.setPrefWidth(50);
        test2.setPrefWidth(50);
        test1.setMinWidth(50);
        //Todo: replace HBoxes test1 and test2 with intended Content.

    }

    @Override
    public void layoutParts() {
        this.getItems().addAll(test1, test2);
    }
}
