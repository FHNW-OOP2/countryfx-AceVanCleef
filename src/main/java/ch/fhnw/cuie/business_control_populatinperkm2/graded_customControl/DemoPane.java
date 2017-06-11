package ch.fhnw.cuie.business_control_populatinperkm2.graded_customControl;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;


public class DemoPane extends BorderPane {

    // declare the custom control
    private NumberRange range;

    // Placeholders for properties of the application's PresentationModel
    private final IntegerProperty pmValue1 = new SimpleIntegerProperty(50);
    private final IntegerProperty pmMin1 = new SimpleIntegerProperty(0);
    private final IntegerProperty pmMax1 = new SimpleIntegerProperty(200);

    private final IntegerProperty pmValue2 = new SimpleIntegerProperty(80);
    private final IntegerProperty pmMin2 = new SimpleIntegerProperty(0);
    private final IntegerProperty pmMax2 = new SimpleIntegerProperty(200);

    // all controls you need to show the features of the custom control
    private Slider slider1;
    private TextField minField1;
    private TextField maxField1;

    private Slider slider2;
    private TextField minField2;
    private TextField maxField2;

    public DemoPane() {
        initializeControls();
        layoutControls();
        setupBindings();
    }

    private void initializeControls() {
        setPadding(new Insets(10));

        range = new NumberRange();

        slider1 = new Slider();
        slider1.setShowTickLabels(true);
        minField1 = new TextField();
        maxField1 = new TextField();

        slider2 = new Slider();
        slider2.setShowTickLabels(true);
        minField2 = new TextField();
        maxField2 = new TextField();
    }

    private void layoutControls() {
        HBox hBox = new HBox();
        VBox controlPane = new VBox(new Label("outer bar"),
                new Label(),
                new Label("Value1"),
                slider1,
                new Label("Min1"),
                minField1,
                new Label("Max1"),
                maxField1
        );

        VBox controlPane2 = new VBox(new Label("internal bar"),
                new Label(),
                new Label("Value2"),
                slider2,
                new Label("Min2"),
                minField2,
                new Label("Max2"),
                maxField2
        );

        controlPane.setPadding(new Insets(0, 50, 0, 50));
        controlPane.setSpacing(10);

        controlPane2.setPadding(new Insets(0, 50, 0, 50));
        controlPane2.setSpacing(10);

        setCenter(range);
        hBox.getChildren().addAll(controlPane2,controlPane);
        setRight(hBox);
    }

    private void setupBindings() {
        slider1.valueProperty().bindBidirectional(pmValue1);
        slider1.minProperty().bindBidirectional(pmMin1);
        slider1.maxProperty().bindBidirectional(pmMax1);
        minField1.textProperty().bindBidirectional(pmMin1, new NumberStringConverter());
        maxField1.textProperty().bindBidirectional(pmMax1, new NumberStringConverter());


        slider2.valueProperty().bindBidirectional(pmValue2);
        slider2.minProperty().bindBidirectional(pmMin2);
        slider2.maxProperty().bindBidirectional(pmMax2);
        minField2.textProperty().bindBidirectional(pmMin2, new NumberStringConverter());
        maxField2.textProperty().bindBidirectional(pmMax2, new NumberStringConverter());


        range.minValue1Property().bindBidirectional(pmMin1);
        range.maxValue1Property().bindBidirectional(pmMax1);
        range.currentValue1Property().bindBidirectional(pmValue1);

        range.minValue2Property().bindBidirectional(pmMin2);
        range.maxValue2Property().bindBidirectional(pmMax2);
        range.currentValue2Property().bindBidirectional(pmValue2);
    }


}