package ch.fhnw.cuie.business_control_populatinperkm2.graded_customControl;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;

import java.util.ArrayList;
import java.util.List;

public class NumberRange extends Region {
    private static final double ARTBOARD_SIZE = 400;
    private static final double MINIMUM_SIZE  = 75;
    private static final double MAXIMUM_SIZE  = 800;

    //declare all parts
    private Circle backgroundCircle1;
    private Arc bar1;
    private Circle thumb1;
    private Text valueDisplay1;
    private Group ticks1;
    private List<Text> tickLabels1;

    private Circle backgroundCircle2;
    private Arc bar2;
    private Circle thumb2;
    private Group ticks2;
    private List<Text> tickLabels2;


    //declare all Properties
    private final DoubleProperty minValue1 = new SimpleDoubleProperty(0);
    private final DoubleProperty maxValue1 = new SimpleDoubleProperty(Integer.MAX_VALUE);
    private final DoubleProperty currentValue1 = new SimpleDoubleProperty();

    private final DoubleProperty minValue2 = new SimpleDoubleProperty(0);
    private final DoubleProperty maxValue2 = new SimpleDoubleProperty(Integer.MAX_VALUE);
    private final DoubleProperty currentValue2 = new SimpleDoubleProperty();

    // needed for resizing
    private Pane drawingPane;

    public NumberRange() {
        initializeSelf();
        initializeParts();
        layoutParts();
        setupEventHandlers();
        setupValueChangeListeners();
        setupBinding();
        setCurrentValue1(50);
    }

    private void initializeSelf() {
        // load stylesheets
        String fonts = getClass().getResource("fonts.css").toExternalForm();
        getStylesheets().add(fonts);

        String stylesheet = getClass().getResource("style.css").toExternalForm();
        getStylesheets().add(stylesheet);

        // initialize resizing constraints
        Insets padding           = getPadding();
        double verticalPadding   = padding.getTop() + padding.getBottom();
        double horizontalPadding = padding.getLeft() + padding.getRight();
        setMinSize(MINIMUM_SIZE + horizontalPadding, MINIMUM_SIZE + verticalPadding);
        setPrefSize(ARTBOARD_SIZE + horizontalPadding, ARTBOARD_SIZE + verticalPadding);
        setMaxSize(MAXIMUM_SIZE + horizontalPadding, MAXIMUM_SIZE + verticalPadding);
    }

    private void initializeParts() {
        double center = ARTBOARD_SIZE * 0.5;

        int    width1  = 15;
        double radius1 = center - width1;
        backgroundCircle1 = new Circle(center, center, radius1);
        backgroundCircle1.getStyleClass().add("backgroundCircle1");

        bar1 = new Arc(center, center, radius1, radius1, 90.0, 0.0);
        bar1.getStyleClass().add("bar1");
        bar1.setType(ArcType.OPEN);

        thumb1 = new Circle(center, center + center - width1, 13);
        //thumb1 = new Circle(center, center, 13);
        thumb1.getStyleClass().add("thumb1");



        int    width2  = 80;
        double radius2 = center - width2;
        backgroundCircle2 = new Circle(center, center, radius2);
        backgroundCircle2.getStyleClass().add("backgroundCircle1");

        bar2 = new Arc(center, center, radius2, radius2, 90.0, 0.0);
        bar2.getStyleClass().add("bar1");
        bar2.setType(ArcType.OPEN);

        thumb2 = new Circle(center, center + center - width2, 13);
        thumb2.getStyleClass().add("thumb1");



        valueDisplay1 = createCenteredText(center, center, "valueDisplay1");



        ticks1 = createTicks(center, center, 60, 360.0, 6, 33, 0, "tick1");
        tickLabels1 = new ArrayList<>();
        int labelCount1 = 8;
        for (int i = 0; i < labelCount1; i++) {
            double radius     = 150;
            double nextAngle = i * 360.0 / labelCount1;

            Point2D p         = pointOnCircle(center, center, radius, nextAngle);
            Text    tickLabel = createCenteredText(p.getX(), p.getY(), "tickLabel1");

            tickLabels1.add(tickLabel);
        }
        updateTickLabels1();


        ticks2 = createTicks(center, center, 60, 360.0, 6, 100, 0, "tick1");
        tickLabels2 = new ArrayList<>();
        int labelCount2 = 8;
        for (int i = 0; i < labelCount2; i++) {
            double radius     = 80;
            double nextAngle = i * 360.0 / labelCount2;

            Point2D p         = pointOnCircle(center, center, radius, nextAngle);
            Text    tickLabel = createCenteredText(p.getX(), p.getY(), "tickLabel1");

            tickLabels2.add(tickLabel);
        }
        updateTickLabels2();




        drawingPane = new Pane();
        drawingPane.getStyleClass().add("numberRange");
        drawingPane.setMaxSize(ARTBOARD_SIZE, ARTBOARD_SIZE);
        drawingPane.setMinSize(ARTBOARD_SIZE, ARTBOARD_SIZE);
        drawingPane.setPrefSize(ARTBOARD_SIZE, ARTBOARD_SIZE);
    }



    private void layoutParts() {
        // add all parts to drawingPane
        drawingPane.getChildren().addAll(backgroundCircle1, bar1, thumb1, valueDisplay1, ticks1, backgroundCircle2, bar2, thumb2, ticks2);
        drawingPane.getChildren().addAll(tickLabels1);
        drawingPane.getChildren().addAll(tickLabels2);

        getChildren().add(drawingPane);
    }

    private void setupEventHandlers() {
        thumb1.setOnMouseDragged(event -> {
            double center     = ARTBOARD_SIZE * 0.5;
            double percentage = angleToPercentage(angle(center, center, event.getX(), event.getY()));

            setCurrentValue1(percentageToValue(percentage, getMinValue1(), getMaxValue1()));
        });

        thumb2.setOnMouseDragged(event -> {
            double center     = ARTBOARD_SIZE * 0.5;
            double percentage = angleToPercentage(angle(center, center, event.getX(), event.getY()));

            setCurrentValue2(percentageToValue(percentage, getMinValue2(), getMaxValue2()));
        });
    }

    private void setupValueChangeListeners() {
        currentValue1.addListener((observable, oldValue, newValue) -> {
            updateThumbAndBar(1);
            //uppdateSum();
            updateDivision();
        });

        minValue1.addListener((observable, oldValue, newValue) -> {
            updateTickLabels1();
            updateThumbAndBar(1);
        });

        maxValue1.addListener((observable, oldValue, newValue) -> {
            updateTickLabels1();
            updateThumbAndBar(1);
        });

        currentValue2.addListener((observable, oldValue, newValue) -> {
            updateThumbAndBar(2);
            //uppdateSum();
            updateDivision();
        });

        minValue2.addListener((observable, oldValue, newValue) -> {
            updateTickLabels2();
            updateThumbAndBar(2);
        });

        maxValue2.addListener((observable, oldValue, newValue) -> {
            updateTickLabels2();
            updateThumbAndBar(2);
        });
    }

    private void uppdateSum(){
        valueDisplay1.textProperty().setValue(String.valueOf(currentValue1.intValue()+currentValue2.intValue()));
    }

    //Aded by Stefan Wohlgensinger
    private void updateDivision(){
        double population   = getCurrentValue1(); //Population : Area
        double area         = getCurrentValue2();
        if(population < 0){
            population = 0;
        }
        if(area == 0){
            area = 1;
        } else if(area < 0){
            area = Math.abs(area);
        }

        valueDisplay1.textProperty().setValue(String.valueOf( (int)(population / area) ));
    }


    private void updateTickLabels1() {
            int labelCount = tickLabels1.size();
            int step       = (int) ((getMaxValue1() - getMinValue1()) / labelCount);
            for (int i = 0; i < labelCount; i++) {
                Text tickLabel1 = tickLabels1.get(i);
                tickLabel1.setText(String.format("%.0f", getMinValue1() + (i * +step)));
            }
    }

    private void updateTickLabels2() {
        int labelCount = tickLabels2.size();
        int step       = (int) ((getMaxValue2() - getMinValue2()) / labelCount);
        for (int i = 0; i < labelCount; i++) {
            Text tickLabel2 = tickLabels2.get(i);
            tickLabel2.setText(String.format("%.0f", getMinValue1() + (i * +step)));
        }
    }

    private void updateThumbAndBar(int pos){

        double  center      = ARTBOARD_SIZE * 0.5;
        if(pos == 1){
            double percentage = valueToPercentage(getCurrentValue1(), getMinValue1(), getMaxValue1());
            double angle      = percentageToAngle(percentage);
            bar1.setLength(Math.min(-0.05, -angle));
            Point2D thumbCenter = pointOnCircle(center, center, center - 15, angle);
            thumb1.setCenterX(thumbCenter.getX());
            thumb1.setCenterY(thumbCenter.getY());
        }
        if(pos == 2){
            double percentage = valueToPercentage(getCurrentValue2(), getMinValue2(), getMaxValue2());
            double angle      = percentageToAngle(percentage);
            bar2.setLength(Math.min(-0.05, -angle));
            Point2D thumbCenter2 = pointOnCircle(center, center, center - 80, angle);
            thumb2.setCenterX(thumbCenter2.getX());
            thumb2.setCenterY(thumbCenter2.getY());
        }

    }

    private void setupBinding() {
        //valueDisplay1.textProperty().bind(currentValue1Property().asString("%.1f"));
    }

    //resize by scaling
    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        resize();
    }

    private void resize() {
        double width  = getWidth() - getInsets().getLeft() - getInsets().getRight();
        double height = getHeight() - getInsets().getTop() - getInsets().getBottom();
        double size   = Math.max(Math.min(Math.min(width, height), MAXIMUM_SIZE), MINIMUM_SIZE);

        double scalingFactor = size / ARTBOARD_SIZE;

        if (width > 0 && height > 0) {
            drawingPane.relocate((getWidth() - ARTBOARD_SIZE) * 0.5, (getHeight() - ARTBOARD_SIZE) * 0.5);
            drawingPane.setScaleX(scalingFactor);
            drawingPane.setScaleY(scalingFactor);
        }
    }

    // some handy functions

    private double percentageToValue(double percentage, double minValue, double maxValue) {
        return ((maxValue - minValue) * percentage) + minValue;
    }

    private double valueToPercentage(double value, double minValue, double maxValue) {
        return (value - minValue) / (maxValue - minValue);
    }

    private double angleToPercentage(double angle) {
        return angle / 360.0;
    }

    private double percentageToAngle(double percentage) {
        return 360.0 * percentage;
    }

    /*
     * angle in degrees, 0 is north
     */
    private double angle(double cx, double cy, double x, double y) {
        double deltaX = x - cx;
        double deltaY = y - cy;
        double radius = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
        double nx     = deltaX / radius;
        double ny     = deltaY / radius;
        double theta  = Math.toRadians(90) + Math.atan2(ny, nx);

        return Double.compare(theta, 0.0) >= 0 ? Math.toDegrees(theta) : Math.toDegrees((theta)) + 360.0;
    }

    private Point2D pointOnCircle(double cX, double cY, double radius, double angle) {
        return new Point2D(cX - (radius * Math.sin(Math.toRadians(angle - 180))),
                cY + (radius * Math.cos(Math.toRadians(angle - 180))));
    }

    private Text createCenteredText(String styleClass) {
        return createCenteredText(ARTBOARD_SIZE * 0.5, ARTBOARD_SIZE * 0.5, styleClass);
    }

    private Text createCenteredText(double cx, double cy, String styleClass) {
        Text text = new Text();
        text.getStyleClass().add(styleClass);
        text.setTextOrigin(VPos.CENTER);
        text.setTextAlignment(TextAlignment.CENTER);
        double width = cx > ARTBOARD_SIZE * 0.5 ? ((ARTBOARD_SIZE - cx) * 2.0) : cx * 2.0;
        text.setWrappingWidth(width);
        text.setBoundsType(TextBoundsType.VISUAL);
        text.setY(cy);
        text.setX(cx - (width / 2.0));

        return text;
    }

    private Group createTicks(double cx, double cy, int numberOfTicks, double overallAngle, double tickLength, double indent, double startingAngle, String styleClass) {
        Group group = new Group();

        double degreesBetweenTicks = overallAngle == 360 ?
                overallAngle / numberOfTicks :
                overallAngle / (numberOfTicks - 1);
        double outerRadius = Math.min(cx, cy) - indent;
        double innerRadius = Math.min(cx, cy) - indent - tickLength;

        for (int i = 0; i < numberOfTicks; i++) {
            double angle = 180 + startingAngle + i * degreesBetweenTicks;

            Point2D startPoint = pointOnCircle(cx, cy, outerRadius, angle);
            Point2D endPoint   = pointOnCircle(cx, cy, innerRadius, angle);

            Line tick = new Line(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
            tick.getStyleClass().add(styleClass);
            group.getChildren().add(tick);
        }

        return group;
    }

    // all getters and setters

    public double getMinValue1() {
        return minValue1.get();
    }

    public DoubleProperty minValue1Property() {
        return minValue1;
    }

    public void setMinValue1(double minValue1) {
        this.minValue1.set(minValue1);
    }

    public double getMaxValue1() {
        return maxValue1.get();
    }

    public DoubleProperty maxValue1Property() {
        return maxValue1;
    }

    public void setMaxValue1(double maxValue1) {
        this.maxValue1.set(maxValue1);
    }

    public double getCurrentValue1() {
        return currentValue1.get();
    }

    public DoubleProperty currentValue1Property() {
        return currentValue1;
    }

    public void setCurrentValue1(double currentValue1) {
        this.currentValue1.set(currentValue1);
    }

    public double getMinValue2() {
        return minValue2.get();
    }

    public DoubleProperty minValue2Property() {
        return minValue2;
    }

    public void setMinValue2(double minValue2) {
        this.minValue2.set(minValue2);
    }

    public double getMaxValue2() {
        return maxValue2.get();
    }

    public DoubleProperty maxValue2Property() {
        return maxValue2;
    }

    public void setMaxValue2(double maxValue2) {
        this.maxValue2.set(maxValue2);
    }

    public double getCurrentValue2() {
        return currentValue2.get();
    }

    public DoubleProperty currentValue2Property() {
        return currentValue2;
    }

    public void setCurrentValue2(double currentValue2) {
        this.currentValue2.set(currentValue2);
    }
}
