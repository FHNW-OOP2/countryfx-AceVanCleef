package ch.fhnw.cuie.population_simplecontrol;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;

public class FigurePane extends GridPane {

  PopulationControl simpleControl;

  private ArrayList<PopulationControl> figureArray = new ArrayList<>();

  private FadeTransition fadeTransition;

  private DoubleProperty maxPopulation = new SimpleDoubleProperty();
  private DoubleProperty currentPopulation = new SimpleDoubleProperty();
  private IntegerProperty figuresWidth = new SimpleIntegerProperty(NUMBER_OF_FIGURES_WIDTH);
  private IntegerProperty figuresHeight = new SimpleIntegerProperty(NUMBER_OF_FIGURES_HEIGHT);
  private BooleanProperty logScaling = new SimpleBooleanProperty();

  private final ObjectProperty<Color> baseColor = new SimpleObjectProperty<>();

  private static final int NUMBER_OF_FIGURES_WIDTH = 8;
  private static final int NUMBER_OF_FIGURES_HEIGHT = 10;
  private static final int FADE_DURATION = 500;

  private static int totalFigures = NUMBER_OF_FIGURES_WIDTH * NUMBER_OF_FIGURES_HEIGHT;


  public FigurePane() {
    initializeSelf();
    layoutControls();
    setupListeners();
  }

  private void initializeSelf() {
    String stylesheet = getClass().getResource("populationStyles.css").toExternalForm();
    getStylesheets().add(stylesheet);
  }

  private void layoutControls() {
    this.setPadding(new Insets(10));

    this.getStyleClass().add("figurePane");

    for (int j = 0; j < getFiguresWidth(); j++) {
      for (int i = 0; i < getFiguresHeight(); i++) {
        simpleControl = new PopulationControl();
        // Binding to change color
        simpleControl.baseColorProperty().bindBidirectional(baseColorProperty());

        GridPane.setHgrow(simpleControl, Priority.ALWAYS);
        GridPane.setVgrow(simpleControl, Priority.ALWAYS);
        GridPane.setValignment(simpleControl, VPos.CENTER);
        GridPane.setHalignment(simpleControl, HPos.CENTER);
        simpleControl.setPadding(new Insets(3, 0, 3, 0));

        this.add(simpleControl, i, j);
        figureArray.add(simpleControl);
      }
    }
    setFigureVisibility(calcVisibleFigures());
  }

  private void setFigureVisibility(int noOfVisibileFigures) {
    totalFigures = getFiguresHeight() * getFiguresWidth();

    for (int i = totalFigures - 1; i > totalFigures - 1 - noOfVisibileFigures; i--) {
      fadeTransition = new FadeTransition(Duration.millis(FADE_DURATION), figureArray.get(i));
      fadeTransition.setToValue(1.0);
      if (!fadeTransition.getStatus().equals(Animation.Status.RUNNING)) {
        fadeTransition.play();
      }
    }

    for (int i = totalFigures - 1 - noOfVisibileFigures; i >= 0; i--) {
      fadeTransition = new FadeTransition(Duration.millis(FADE_DURATION), figureArray.get(i));
      fadeTransition.setToValue(0.0);
      if (!fadeTransition.getStatus().equals(Animation.Status.RUNNING)) {
        fadeTransition.play();
      }
    }
  }

  private int calcVisibleFigures() {
    // check if the user wishes to use logarithmic scaling
    if (logScaling.get()) {
      return (int) ((Math.log(currentPopulationProperty().get()) / Math.log(maxPopulationProperty().get()))
          * getFiguresWidth() * getFiguresHeight());
    }
    // no logarithmic calculation
    return (int) ((currentPopulationProperty().get() / maxPopulationProperty().get())
        * getFiguresWidth() * getFiguresHeight());
  }

  private void setupListeners() {
    maxPopulationProperty().addListener(observable -> setFigureVisibility(calcVisibleFigures()));
    currentPopulationProperty().addListener(observable -> setFigureVisibility(calcVisibleFigures()));

    figuresHeightProperty().addListener((observable, oldValue, newValue) -> {
      this.getChildren().removeAll(figureArray);
      figureArray.clear();
      layoutControls();
    });

    figuresWidthProperty().addListener(observable -> {
      this.getChildren().removeAll(figureArray);
      figureArray.clear();
      layoutControls();
    });
  }

  public double getMaxPopulation() {
    return maxPopulation.get();
  }

  public DoubleProperty maxPopulationProperty() {
    return maxPopulation;
  }

  public void setMaxPopulation(double maxPopulation) {
    this.maxPopulation.set(maxPopulation);
  }

  public double getCurrentPopulation() {
    return currentPopulation.get();
  }

  public DoubleProperty currentPopulationProperty() {
    return currentPopulation;
  }

  public void setCurrentPopulation(double currentPopulation) {
    this.currentPopulation.set(currentPopulation);
  }

  public int getFiguresWidth() {
    return figuresWidth.get();
  }

  public IntegerProperty figuresWidthProperty() {
    return figuresWidth;
  }

  public void setFiguresWidth(int figuresWidth) {
    this.figuresWidth.set(figuresWidth);
  }

  public int getFiguresHeight() {
    return figuresHeight.get();
  }

  public IntegerProperty figuresHeightProperty() {
    return figuresHeight;
  }

  public void setFiguresHeight(int figuresHeight) {
    this.figuresHeight.set(figuresHeight);
  }

  public PopulationControl getSimpleControl() {
    return simpleControl;
  }

  public Color getBaseColor() {
    return baseColor.get();
  }

  public ObjectProperty<Color> baseColorProperty() {
    return baseColor;
  }

  public void setBaseColor(Color baseColor) {
    this.baseColor.set(baseColor);
  }

  public boolean isLogScaling() {
    return logScaling.get();
  }

  public BooleanProperty logScalingProperty() {
    return logScaling;
  }

  public void setLogScaling(boolean logScaling) {
    this.logScaling.set(logScaling);
  }
}

