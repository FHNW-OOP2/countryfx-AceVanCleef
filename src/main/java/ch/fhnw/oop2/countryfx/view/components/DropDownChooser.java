package ch.fhnw.oop2.countryfx.view.components;

import ch.fhnw.cuie.business_control_populatinperkm2.graded_customControl.NumberRange;
import ch.fhnw.oop2.countryfx.presentationmodel.RootPM;
import ch.fhnw.oop2.countryfx.view.util.ViewMixin;
import javafx.scene.layout.StackPane;

/**
 * Created by degonas on 11.06.2017.
 */
public class DropDownChooser extends StackPane implements ViewMixin {

    private NumberRange populationKm2Calc;

    private RootPM pm;

    public DropDownChooser(RootPM pm){
        this.pm = pm;
        init();
    }

    @Override
    public void initializeParts() {
        populationKm2Calc = new NumberRange();
    }

    @Override
    public void layoutParts() {
        this.getChildren().addAll(populationKm2Calc);
    }

    @Override
    public void setupBindings() {
        //value1 = population
        populationKm2Calc.currentValue1Property().bindBidirectional(pm.getCountryProxy().populationProperty());
        //value2 = area
        populationKm2Calc.currentValue2Property().bindBidirectional(pm.getCountryProxy().areaProperty());
    }
}
