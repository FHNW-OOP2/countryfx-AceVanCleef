package ch.fhnw.oop2.countryfx.view;

import ch.fhnw.oop2.countryfx.presentationmodel.RootPM;
import ch.fhnw.oop2.countryfx.view.util.ViewMixin;
import javafx.scene.layout.GridPane;

/**
 * Created by Degonas on 29.04.2017.
 */
public class CountryEditor extends GridPane implements ViewMixin{

    private RootPM pm;

    public CountryEditor(RootPM pm){
        this.pm = pm;
        init();
    }

    @Override
    public void initializeParts() {

    }

    @Override
    public void layoutParts() {

    }
}
