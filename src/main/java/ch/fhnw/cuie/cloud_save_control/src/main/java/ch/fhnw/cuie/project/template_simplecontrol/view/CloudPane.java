package ch.fhnw.cuie.cloud_save_control.src.main.java.ch.fhnw.cuie.project.template_simplecontrol.view;

/** Lessons learned:
 *
 *  - Animation Delay: Animation gets put into 2nd Thread and executed parallel.
 */

import ch.fhnw.cuie.cloud_save_control.src.main.java.ch.fhnw.cuie.project.template_simplecontrol.helpers.Orientation;
import ch.fhnw.cuie.cloud_save_control.src.main.java.ch.fhnw.cuie.project.template_simplecontrol.view.impl.HorizontalCloudPane;
import ch.fhnw.cuie.cloud_save_control.src.main.java.ch.fhnw.cuie.project.template_simplecontrol.view.impl.VerticalCloudPane;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.StackPane;

/**
 * Created by Stefan on 02.04.2017.
 */

/** Konzept:
 * statt DragAndDropEventhandlewr von unten selbst einen Mittelpunkt f.
 * Kreis definieren und mit Mittelpunkt vom grünen Kreis vergleichen:
 * "Ist roter Kreis genug nahe am grünen Kreis?"
 */
public abstract class CloudPane extends StackPane{

    public static Orientation SELECTED_ORIENTATION;

    protected static double PREFERRED_HEIGHT;
    protected static double MINIMUM_HEIGHT;
    protected static double MAXIMUM_HEIGHT;

    protected static double PREFERRED_WIDTH;
    protected static double MINIMUM_WIDTH;
    protected static double MAXIMUM_WIDTH;

    private final BooleanProperty saveIt = new SimpleBooleanProperty(false);

    public static CloudPane createWithOrientationOf(Orientation orientation){
        if(orientation == Orientation.HORIZONTAL){
            SELECTED_ORIENTATION = orientation;
            setupHDimensionValues();
            return new HorizontalCloudPane();

        } else if (orientation == Orientation.VERTICAL){
            SELECTED_ORIENTATION = orientation;
            setupVDimensionValues();
            return new VerticalCloudPane();

        } else {
            SELECTED_ORIENTATION = Orientation.HORIZONTAL;
            setupHDimensionValues();
            return new HorizontalCloudPane(); //default
        }
    }

    private static void setupHDimensionValues(){
        PREFERRED_HEIGHT = HorizontalCloudPane.PREFERRED_HEIGHT;
        MINIMUM_HEIGHT = HorizontalCloudPane.MINIMUM_HEIGHT;
        MAXIMUM_HEIGHT = HorizontalCloudPane.MAXIMUM_HEIGHT;

        PREFERRED_WIDTH = HorizontalCloudPane.PREFERRED_WIDTH;
        MINIMUM_WIDTH = HorizontalCloudPane.MINIMUM_WIDTH;
        MAXIMUM_WIDTH = HorizontalCloudPane.MAXIMUM_WIDTH;
    }

    private static void setupVDimensionValues(){
        PREFERRED_HEIGHT = VerticalCloudPane.PREFERRED_HEIGHT;
        MINIMUM_HEIGHT = VerticalCloudPane.MINIMUM_HEIGHT;
        MAXIMUM_HEIGHT = VerticalCloudPane.MAXIMUM_HEIGHT;

        PREFERRED_WIDTH = VerticalCloudPane.PREFERRED_WIDTH;
        MINIMUM_WIDTH = VerticalCloudPane.MINIMUM_WIDTH;
        MAXIMUM_WIDTH = VerticalCloudPane.MAXIMUM_WIDTH;
    }

    public boolean isSaveIt() {
        return saveIt.get();
    }

    public BooleanProperty saveItProperty() {
        return saveIt;
    }

    public void setSaveIt(boolean saveIt) {
        this.saveIt.set(saveIt);
    }
}
