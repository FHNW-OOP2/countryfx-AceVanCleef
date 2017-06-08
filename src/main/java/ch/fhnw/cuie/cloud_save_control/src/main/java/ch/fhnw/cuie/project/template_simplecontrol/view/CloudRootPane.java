package ch.fhnw.cuie.cloud_save_control.src.main.java.ch.fhnw.cuie.project.template_simplecontrol.view;

import ch.fhnw.cuie.cloud_save_control.src.main.java.ch.fhnw.cuie.project.template_simplecontrol.helpers.Orientation;

import javafx.beans.property.BooleanProperty;
import javafx.scene.layout.StackPane;

/**
 * Created by Degonas on 02.06.2017.
 */
public class CloudRootPane extends StackPane implements Mixin {

    private static CloudPane cloudControl;

    /* serves as a bridge to the outer world */
    private BooleanProperty saveIt;

    /**
     * Factory Function: replaces common constructor and offers additional functionality.
     * @param orientation horizontal or vertical. See /helpers/Orientation.java
     * @param pm
     * @return
     */
    public static CloudRootPane createCloudControlWithOrientationOf(Orientation orientation){
        cloudControl = CloudPane.createWithOrientationOf(orientation);
        return new CloudRootPane();
    }

    private CloudRootPane(){
        init();
    }

    @Override
    public void initializeSelf() {
        String stylesheet = getClass().getResource("../style.css").toExternalForm();
        getStylesheets().add(stylesheet);

        this.setId("rootPane");

        //limit shrinking according to...
        this.setMinHeight(CloudPane.MINIMUM_HEIGHT);
        this.setMinWidth(CloudPane.MINIMUM_WIDTH);

        //to make it avaiable for bindings
        saveIt = cloudControl.saveItProperty();
    }

    @Override
    public void initializeParts() {
        //cloudControl = new VerticalCloudPane(pm);
        //cloudControl = new HorizontalCloudPane(pm);
    }

    @Override
    public void layoutParts() {
        this.getChildren().addAll(cloudControl);
        resize();
    }

    /**
     * ideal when all children have to be rescaled proportionally.
     * That means, you draw the elements initially once and let jFX handle the rescaling.
     * You have to make sure that the children elements have to be positioned properly within
     * initializeControls().
     *
     * How to find out, if resize() variant B will work: in Adobe Illustrator: zoom in and out and
     * see wheter your custom control will look the way you want it.
     */
    private void resize() {
	    /* wieviel Platz in Breite und Höhe zur Verfügung? */
        double width  = getWidth() - getInsets().getLeft() - getInsets().getRight();
        double height = getHeight() - getInsets().getTop() - getInsets().getBottom();
        double sizeX = Math.max(Math.min(Math.min(width, height), CloudPane.MAXIMUM_WIDTH), CloudPane.MINIMUM_WIDTH);
        double sizeY = Math.max(Math.min(Math.min(width, height), CloudPane.MAXIMUM_HEIGHT), CloudPane.MINIMUM_HEIGHT);

        double scalingFactorX = sizeX / CloudPane.PREFERRED_WIDTH;
        double scalingFactorY = sizeY / CloudPane.PREFERRED_HEIGHT;

		/* Scalefähigkeit von jFX ausnutzen: */
        if(width > 0 && height > 0){
            cloudControl.relocate((getWidth() - CloudPane.PREFERRED_WIDTH) * 0.5, (getHeight() - CloudPane.PREFERRED_HEIGHT) * 0.5);
            cloudControl.setScaleX(scalingFactorX);
            cloudControl.setScaleY(scalingFactorY);
        }
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
