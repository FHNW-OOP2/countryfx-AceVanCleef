package ch.fhnw.oop2.countryfx.view;

import ch.fhnw.oop2.countryfx.presentationmodel.CountryPM;
import ch.fhnw.oop2.countryfx.presentationmodel.RootPM;
import ch.fhnw.oop2.countryfx.view.util.ViewMixin;
import javafx.beans.binding.Bindings;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Degonas on 29.04.2017.
 */
public class CountryOverview extends GridPane implements ViewMixin{

    private RootPM pm;

    private static String imagePath = "https://dieterholz.github.io/StaticResources/flags_iso/128/";

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
        country = new Label("title");
        continent = new Label("continent");
        capital = new Label("capital");
        area = new Label("km2");
        image = new Image(imagePath+"ch.png");
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


    //Todo: Bindings
//
//    @Override
//    public void setupBindings() {
//        PagingList mountains = rootPM.getMountains();
//
//        count.textProperty().bind(Bindings.size(mountains).asString());
//        instances.textProperty().bind(mountains.cacheLevelProperty().asString());
//        serviceCalls.textProperty().bind(mountains.serviceCallsProperty().asString());
//        sleep.valueProperty().bindBidirectional(mountains.sleepProperty());
//        pageSizeFactor.valueProperty().bindBidirectional(mountains.pageSizeFactorProperty());
//    }

    @Override
    public void setupBindings() {
        //CountryPM theCountry = ;

        //country.textProperty().bind(pm.getAllCountries().);
    }
}
