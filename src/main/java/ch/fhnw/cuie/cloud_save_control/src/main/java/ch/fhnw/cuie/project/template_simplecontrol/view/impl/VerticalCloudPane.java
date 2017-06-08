package ch.fhnw.cuie.cloud_save_control.src.main.java.ch.fhnw.cuie.project.template_simplecontrol.view.impl;

/** Lessons learned:
 *
 *  - Animation Delay: Animation gets put into 2nd Thread and executed parallel.
 */

import ch.fhnw.cuie.cloud_save_control.src.main.java.ch.fhnw.cuie.project.template_simplecontrol.view.CloudPane;
import ch.fhnw.cuie.cloud_save_control.src.main.java.ch.fhnw.cuie.project.template_simplecontrol.view.Mixin;
import ch.fhnw.cuie.cloud_save_control.src.main.java.ch.fhnw.cuie.project.template_simplecontrol.helpers.SvgLoaderLib;

import javafx.animation.*;
import javafx.beans.property.*;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * Created by Stefan on 02.04.2017.
 */

/** Konzept:
 * statt DragAndDropEventhandlewr von unten selbst einen Mittelpunkt f.
 * Kreis definieren und mit Mittelpunkt vom grünen Kreis vergleichen:
 * "Ist roter Kreis genug nahe am grünen Kreis?"
 */
public class VerticalCloudPane extends CloudPane implements Mixin{


    /* UI Elements */
    private Circle skybackground;   // BLUE CIRCLE
    //remembers original position of mouse pointer when source is dragged.
    private double orgSceneX, orgSceneY;
    //remembers original position of source when mouse is dragged
    private double orgTranslateX, orgTranslateY;
    // UI - elements as SVG-holding groups. Note: the source has to be dragged to the target.
    private Group source;   //the dragable cloud
    private Group target;   //the target cloud shape
    // Additional UI - Elements
    private Button reset;
    private Label status;

    /* Animations */
    private FadeTransition textFader;


    /* WEB COLORS */
    private static final String SKY_BLUE_SAVED = "#6192ad";
    private static final String SKY_BLUE_UNSAVED = "#77B3D4";
    private static final String SKY_BLUE_HOVER = "#85a0af";

    private BooleanProperty hasReachedTarget = new SimpleBooleanProperty(false);

    /* #Resizing */
    public static final double PREFERRED_WIDTH = 100;
    public static final double MINIMUM_WIDTH   = 25;
    public static final double MAXIMUM_WIDTH   = 200;

    public static final double PREFERRED_HEIGHT = 400;
    public static final double MINIMUM_HEIGHT   = 100;
    public static final double MAXIMUM_HEIGHT   = 800;

    //#responsiveFontSize. Source (jalopezsaurez): https://stackoverflow.com/questions/23705654/bind-font-size-in-javafx
    private ObjectProperty<Font> fontTracking = new SimpleObjectProperty<Font>(Font.getDefault());


    /*************** Properties ***************/

    /** Distance from origin point (0|0) to center points of source, target and skybackground.
     *  offsetFromOrigin x 2 = distance between [source] vs. [target, skybox] centerpoints */
    private final DoubleProperty offsetFromOrigin = new SimpleDoubleProperty();

    /** How large source, target and skybackground are */
    private final DoubleProperty baseRadii = new SimpleDoubleProperty();
    private final DoubleProperty skyRadius = new SimpleDoubleProperty();

    /** Detection range: RADIUS_SOURCE + RADIUS_TARGET */
    private final DoubleProperty detectionOffset = new SimpleDoubleProperty();

    public VerticalCloudPane() {
        init();
    }

    @Override
    public void initializeSelf() {
        this.setMaxSize(MAXIMUM_WIDTH, MAXIMUM_HEIGHT);
        this.setMinSize(MINIMUM_WIDTH, MINIMUM_HEIGHT);
        this.setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);

        //start position
        setOffsetFromOrigin(getHeight() * 0.25);
        //start size of source, target and skybackground
        setBaseRadii(getPrefHeight() * 0.125);            //todo: for horizontal, use getPrefWidth()
        setSkyRadius(getBaseRadii() * 1.5);
        //Detection range: RADIUS_SOURCE + RADIUS_TARGET
        setDetectionOffset(getBaseRadii() * 2.0);


        //for debugging
        getStyleClass().addAll("cloud-control");
    }



    @Override
    public void initializeParts(){
        SvgLoaderLib svgLoader = new SvgLoaderLib();

        reset = new Button("  Reset");
        Group resetIcon = svgLoader.getSVGAsGroupFrom("../sketch/reset.svg");
        resetIcon.setScaleX(2);
        resetIcon.setScaleY(2);
        reset.setGraphic(resetIcon);
        reset.setTranslateY(5);

        status = new Label("");
        //status.setFont(new Font(48.0f));
        adjustFontSizeTo(getPrefWidth() / 2);
        status.setTranslateX(0);
        status.setTranslateY(0);

        /* Working with Group*/
        //dragable source
        source = svgLoader.getSVGAsGroupFrom("../sketch/cloud_source.svg");
        //target waiting for source
        target = svgLoader.getSVGAsGroupFrom("../sketch/cloud_target_emptyshape.svg");

        source.setTranslateX(0);
        source.setTranslateY(0 + getOffsetFromOrigin());

        target.setTranslateX(0);
        target.setTranslateY(0 - getOffsetFromOrigin());

        source.setScaleX(getBaseRadii() / 100);
        source.setScaleY(getBaseRadii() / 100);

        target.setScaleX(getBaseRadii() / 100);
        target.setScaleY(getBaseRadii() / 100);

        /* END of Working with Group */

        //skybox as background
        skybackground = new Circle(getSkyRadius(), Color.web(SKY_BLUE_UNSAVED));
        skybackground.setTranslateX(0);
        skybackground.setTranslateY(0 - getOffsetFromOrigin() + 10);
        skybackground.setRadius(getSkyRadius());
        skybackground.getStyleClass().addAll("skybackground");
    }

    /**
     *  define where objects are place
     */
    @Override
    public void layoutParts(){
        this.getChildren().addAll(status, skybackground, target, source);
        setAlignment(status, Pos.CENTER);

        //for debugging
        //this.getChildren().add(reset);
        setAlignment(reset, Pos.TOP_CENTER);
    }

    /**
     * reacts to user input and actions.
     */
    @Override
    public void addEventHandlers(){
        // resets source to starting position
        reset.setOnAction(event -> {
            System.out.println("Resetting...");
            //resetSourcePos();
            resetSourceCloudPos();
            status.setText("");
        });


        /////////////////clouds//////////////////

        source.setOnMousePressed(t -> {
            //remembers the initial mouse pointer position before the drag motion.
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();
            //remembers the starting coordinates of source before the drag motion.
            orgTranslateX = ((Group)(t.getSource())).getTranslateX();
            orgTranslateY = ((Group)(t.getSource())).getTranslateY();
        });

        source.setOnMouseDragged(t -> {
            //calculate how far the mouse has been moved
            double offsetX = t.getSceneX() - orgSceneX; //movement distance = new position - original position
            double offsetY = t.getSceneY() - orgSceneY;
            //calculate new position of source
            double newTranslateX = orgTranslateX + offsetX; // new source position = original position + mouse movement distance
            double newTranslateY = orgTranslateY + offsetY;

            //draw source on its new destined position
            ((Group)(t.getSource())).setTranslateX(newTranslateX);
            ((Group)(t.getSource())).setTranslateY(newTranslateY);

            status.setText("");

            if(hasSourceReachedSky()){
                skybackground.setRadius(getSkyRadius() + 20);
                skybackground.setFill(Color.web(SKY_BLUE_HOVER));
            } else {
                skybackground.setFill(Color.web(SKY_BLUE_UNSAVED));
                skybackground.setRadius(getSkyRadius());
            }

        });


        source.setOnMouseReleased(event -> {
            if (!hasSourceReachedSky()){
                resetSourceCloudPos();
            } else {
                setSourceCloudPos(0, 0 - getOffsetFromOrigin());
                skybackground.setFill(Color.web(SKY_BLUE_SAVED));
                status.setText("Saved");

                //ordering the RootPM to save all current changes of your data
                setSaveIt(true);

                //text animation (blinking)
                textFader = new FadeTransition(Duration.millis(100), status);
                textFader.setFromValue(1.0);
                textFader.setToValue(0.1);
                textFader.setCycleCount(4);
                textFader.setAutoReverse(true);
                textFader.play();

                //moving back to start location
                setHasReachedTarget(true);
            }
        });
    }


    /**
     * reacts to system internal value changes.
     * (No direct influence from user)
     */
    @Override
    public void addValueChangedListeners(){
        hasReachedTargetProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                disableSourceFor(3);
                this.resetCloudAnimation();
                this.setHasReachedTarget(false);
                resetLabelTextAfter(6, status);
                resetSaveIt(3);
                skybackground.setRadius(getSkyRadius());
            }
        });

        //repositioning of center points
        offsetFromOriginProperty().addListener((observable, oldValue, newValue) -> {
            double newGap = newValue.doubleValue();
            source.setTranslateY(newGap);
            target.setTranslateY(-newGap);
            skybackground.setTranslateY(-newGap + 10);
        });

        //recalculates baseRadii and adjusts detectionOffset (indirectly) through binding...
        heightProperty().addListener((observable, oldValue, newValue) -> {
            double newRadii = newValue.doubleValue() * 0.125;
            //Wenn der Radius von Source und Target (und Skybackground) nicht über die aktuelle Pane-Breite hinaus gehen...
            if(newRadii <= getWidth() / 3){
                //...dann passe die Grösse von Source und Target (und Skybackground (indirekt)) an. Relevant wenn widthProperty == minWidthProperty
                setBaseRadii(newRadii);
                //@responsiveFontSize
                adjustFontSizeTo(newValue.doubleValue());
            }

        });
        widthProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.doubleValue() < getHeight() * 0.5) {
                setBaseRadii(newValue.doubleValue() * 0.25);
                //@responsiveFontSize
                adjustFontSizeTo(newValue.doubleValue());
            }
        });
        //...and updates the size of source, target and skybackground:
        baseRadiiProperty().addListener((observable, oldValue, newValue) -> {
                double newBaseRadii = newValue.doubleValue();

                //scale 1.0 = 100%
                source.setScaleX(newBaseRadii / 100);
                source.setScaleY(newBaseRadii / 100);
                target.setScaleX(newBaseRadii / 100);
                target.setScaleY(newBaseRadii / 100);
                setSkyRadius(newBaseRadii * 1.5);
                skybackground.setRadius(getSkyRadius());
        });

        //debugging
        saveItProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("saveItProperty() = " + isSaveIt());
        });

    }

    /**
     * recalculates the font size accourding according to widhtOrHeight
     * @param widthOrHeight the width or height of CloudPane
     */
    private void adjustFontSizeTo(Double widthOrHeight) {
        fontTracking.set(Font.font(widthOrHeight / 4));
    }


    @Override
    public void setupBindings() {
        //relocating source, target and sky
        offsetFromOriginProperty().bind(this.heightProperty().multiply(0.25));
        //adjusts the detection range for "hasSourceReachedSky()"
        detectionOffsetProperty().bind(baseRadiiProperty().multiply(2));

        //#responsiveFontSize
        status.fontProperty().bind(fontTracking);
    }


    /************************** Helper methods ***********************/

    /**
     * Bugfix: Cloud on target not drageable until animation finished
     * @param seconds
     */
    private void disableSourceFor(int seconds){
        //while source is on target, make it NOT clickable/dragable
        source.setDisable(true);

        //enable source after x seconds (make it click- and dragable again)
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(seconds),
                new KeyValue(source.disableProperty(), false)
        );
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    /**
     * resets the Label.textProperty() to an empty string ("").
     *
     * source: see cuie document "05_Animationen_2.pdf" on page 26.
     * @param label target label
     * @param seconds amount of delay / waiting time
     */
    private void resetLabelTextAfter(int seconds, Label label){
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(seconds),
                new KeyValue(label.textProperty(), "")
        );
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    /**
     * sets saveItProperty() to false after x seconds.
     * @param seconds
     */
    private void resetSaveIt(int seconds){
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(seconds),
                new KeyValue(saveItProperty(), false)
        );
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    private boolean hasSourceReachedSky(){
        double[] posTarget = {target.getTranslateX(), target.getTranslateY()};
        double[] posSource = {source.getTranslateX(), source.getTranslateY()};


        double distanceX = Math.abs(posTarget[0] - posSource[0]);
        double distanceY = Math.abs(posTarget[1] - posSource[1]);

        //since a cloud isn't cirular, the distanceY must be adjusted:
        double correctionY = 25;

        //responsive version
        if (distanceX <= getDetectionOffset() && distanceY <= getDetectionOffset() - correctionY){
            return true;
        }

        return false;
    }

    private void resetCloudAnimation(){
        TranslateTransition animation = new TranslateTransition(Duration.millis(900), source);
        animation.setToX(0);
        animation.setToY(getOffsetFromOrigin());
        animation.setDelay(Duration.seconds(3));
        animation.play();
    }

    private void resetSourceCloudPos() {
        source.setTranslateX(0);
        source.setTranslateY(0 + getOffsetFromOrigin());
        skybackground.setFill(Color.web(SKY_BLUE_UNSAVED));
        skybackground.setRadius(getSkyRadius());
    }

    /**
     * resets the baseRadii and skyRadius when called.
     */
    private void resetDimensions(){
        //start size of source, target and skybackground
        setBaseRadii(getPrefHeight() * 0.125);            //todo: for horizontal, use getPrefWidth()
        setSkyRadius(getBaseRadii() * 1.5);
    }

    private void setSourceCloudPos(double translateX, double translateY){
        source.setTranslateX(translateX);
        source.setTranslateY(translateY);
    }



    /**
     * used within event listeners
     */
    public static void resetStatusText(Label status){
        status.setText("");
    }

    //Setter and Getter

    public boolean isHasReachedTarget() {
        return hasReachedTarget.get();
    }

    public BooleanProperty hasReachedTargetProperty() {
        return hasReachedTarget;
    }

    public void setHasReachedTarget(boolean hasReachedTarget) {
        this.hasReachedTarget.set(hasReachedTarget);
    }

    public double getOffsetFromOrigin() {
        return offsetFromOrigin.get();
    }

    public DoubleProperty offsetFromOriginProperty() {
        return offsetFromOrigin;
    }

    public void setOffsetFromOrigin(double offsetFromOrigin) {
        this.offsetFromOrigin.set(offsetFromOrigin);
    }

    public double getDetectionOffset() {
        return detectionOffset.get();
    }

    public DoubleProperty detectionOffsetProperty() {
        return detectionOffset;
    }

    public void setDetectionOffset(double detectionOffset) {
        this.detectionOffset.set(detectionOffset);
    }

    public double getBaseRadii() {
        return baseRadii.get();
    }

    public DoubleProperty baseRadiiProperty() {
        return baseRadii;
    }

    public void setBaseRadii(double baseRadii) {
        this.baseRadii.set(baseRadii);
    }

    public double getSkyRadius() {
        return skyRadius.get();
    }

    public DoubleProperty skyRadiusProperty() {
        return skyRadius;
    }

    public void setSkyRadius(double skyRadius) {
        this.skyRadius.set(skyRadius);
    }

    //save mechanic
    @Override
    public boolean isSaveIt() {
        return super.isSaveIt();
    }

    public BooleanProperty saveItProperty() {
        return super.saveItProperty();
    }

    public void setSaveIt(boolean saveIt) {
        super.setSaveIt(saveIt);
    }
}
