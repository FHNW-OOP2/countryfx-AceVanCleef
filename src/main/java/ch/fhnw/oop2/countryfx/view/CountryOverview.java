package ch.fhnw.oop2.countryfx.view;

import ch.fhnw.oop2.countryfx.presentationmodel.CountryPM;
import ch.fhnw.oop2.countryfx.presentationmodel.RootPM;
import ch.fhnw.oop2.countryfx.view.util.ViewMixin;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

/**
 * Created by Degonas on 29.04.2017.
 */
public class CountryOverview extends GridPane implements ViewMixin{

    private RootPM pm;

    private static String imageSize = "128/";

    private Label country;
    private Label continent;
    private Label capital;
    private Label area;
    private ImageView flag;
    private Image image;


    public CountryOverview(RootPM pm){
        this.pm = pm;
        init(); //Bravo Stefan

        //for debuggin
        this.gridLinesVisibleProperty().set(true);
    }

    @Override
    public void initializeParts() {
        country = new Label();
        continent = new Label();
        capital = new Label();
        area = new Label();
        image = new Image(pm.getCurrentFlagURL(imageSize), true);
        flag = new ImageView();

    }

    @Override
    public void layoutParts() {
        flag.setImage(image);


        // Growth vertically (Höhe)
        RowConstraints rc = new RowConstraints();
        rc.setVgrow(Priority.ALWAYS);
        //rc.setMaxHeight(Double.MAX_VALUE);
        getRowConstraints().addAll(rc, rc, rc, rc, rc);

        // Growth horizontally (Breite)
        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow(Priority.ALWAYS);
        getColumnConstraints().addAll(cc, cc, cc);

        //adding labels
        add(country, 0,0);
        add(continent, 0,1);
        add(capital, 0,2);
        add(area, 0,3);

        //adding image view
        add(flag, 2,0, 1, 4);
        GridPane.setHalignment(flag, HPos.RIGHT);

    }

    @Override
    public void setupBindings() {
        //old selection handling
        /*
        CountryPM currentCountry = pm.getCurrentCountry();
        //initial binding
        country.textProperty().bind(currentCountry.nameProperty());
        continent.textProperty().bind(currentCountry.continentProperty());
        capital.textProperty().bind(currentCountry.capitalProperty());
        area.textProperty().bind(currentCountry.areaProperty().asString());
        //todo: flag
        */

        //#stableSelection (Advanced Selection Handling)
        country.textProperty().bind(pm.getCountryProxy().nameProperty());
        continent.textProperty().bind(pm.getCountryProxy().continentProperty());
        capital.textProperty().bind(pm.getCountryProxy().capitalProperty());
        area.textProperty().bind(pm.getCountryProxy().areaProperty().asString());
        //todo: flag
    }

    @Override
    public void addValueChangedListeners() {
        //old selection handling
        /*
        pm.selectedCountryIdProperty().addListener((observable, oldValue, newValue) -> {
            //unbding old CountryPM
            CountryPM previousCountryPM = pm.getCountry(oldValue.intValue());
            country.textProperty().unbindBidirectional(previousCountryPM.nameProperty());
            continent.textProperty().unbindBidirectional(previousCountryPM.continentProperty());
            capital.textProperty().unbindBidirectional(previousCountryPM.capitalProperty());
            area.textProperty().unbindBidirectional(previousCountryPM.areaProperty());

            //rebinding new CountryPM
            CountryPM currentCountryPM = pm.getCurrentCountry();
            country.textProperty().bind(currentCountryPM.nameProperty());
            continent.textProperty().bind(currentCountryPM.continentProperty());
            capital.textProperty().bind(currentCountryPM.capitalProperty());
            area.textProperty().bind(currentCountryPM.areaProperty().asString());
        });
        */

        //#Flagge
        //todo: Flagge anzeigen lassen zum laufen bringen.
        //Auf neues selected Country reagieren:
//        pm.selectedCountryIdProperty().addListener((observable, oldValue, newValue) -> {
//            //update flag image:
//            image = new Image(pm.getCurrentFlagURL(imageSize));
//            flag.setImage(image);   //todo: nowändig?
//        });
        //Auf Wertänderung der iso_2Property im proxyCountry reagieren:
        pm.getCountryProxy().iso_2Property().addListener((observable, oldValue, newValue) -> {
            //update flag image:
            image = new Image(pm.getCurrentFlagURL(imageSize), true);
            flag.setImage(image);
        });
    }
}
