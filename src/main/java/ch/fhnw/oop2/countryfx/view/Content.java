package ch.fhnw.oop2.countryfx.view;

import ch.fhnw.oop2.countryfx.presentationmodel.CountryPM;
import ch.fhnw.oop2.countryfx.presentationmodel.RootPM;
import ch.fhnw.oop2.countryfx.view.util.ViewMixin;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

// Lessons learned:
// intermediatePane.getchildren().addAll(countryOverview, countryEditor) necessary??? Yes


/**
 * Created by Degonas on 29.04.2017.
 *
 * Reasonable SplitPane tutorial:
 * http://fxexperience.com/2011/06/splitpane-in-javafx-2-0/
 *
 */
public class Content extends SplitPane implements ViewMixin{

    private RootPM pm;

    //private CountryList<CountryPM> countryList;
    private CountryOverview countryOverview;
    private CountryEditor countryEditor;

    private VBox infoContainer;

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
//        countryList = new CountryList<>();
//        countryList.setItems(pm.getAllCountries()); //arming the list with data. Bon appetit!
//        //countryList.setCellFactory(v -> new CountryList.CountryListCell());
//        countryList.setCellFactory(new Callback<ListView<CountryPM>, CountryList.CountryListCell<CountryPM>>() {
//            @Override
//            public CountryList.CountryListCell<CountryPM> call(ListView<CountryPM> studentListView) {
//                return new CountryList.CountryListCell();
//            }
//        });

        countryOverview = new CountryOverview(pm);
        countryEditor = new CountryEditor(pm);

        //intermediary pane
        infoContainer = new VBox();
        VBox.setVgrow(countryOverview, Priority.ALWAYS);
        VBox.setVgrow(countryEditor, Priority.ALWAYS);

    }

    @Override
    public void layoutParts() {
        infoContainer.getChildren().addAll(countryOverview, countryEditor);
        //this.getItems().addAll(countryList, infoContainer);
        this.getItems().addAll(new VBox(new Button("Test")), infoContainer);
    }
}
