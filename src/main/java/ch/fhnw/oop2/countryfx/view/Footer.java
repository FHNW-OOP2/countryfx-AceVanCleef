package ch.fhnw.oop2.countryfx.view;

import ch.fhnw.oop2.countryfx.presentationmodel.CountryPM;
import ch.fhnw.oop2.countryfx.presentationmodel.RootPM;
import ch.fhnw.oop2.countryfx.view.util.ViewMixin;
import javafx.scene.control.TableView;

/**
 * Created by Degonas on 29.04.2017.
 */
public class Footer extends TableView<CountryPM> implements ViewMixin {

    private final RootPM pm;

    public Footer(RootPM pm){
        this.pm = pm;
        init();
    }

    @Override
    public void initializeSelf() {

    }

    @Override
    public void initializeParts() {
    }

    @Override
    public void layoutParts() {

    }

    @Override
    public void setupBindings() {

    }
}
