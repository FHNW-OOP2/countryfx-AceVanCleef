package ch.fhnw.cuie.population_simplecontrol;

import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleablePropertyFactory;
import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.SVGPath;

import java.util.List;

public class PopulationControl extends Region {

  private static final double ARTBOARD_WIDTH = 640;
  private static final double ARTBOARD_HEIGHT = 360;

  private static final double ASPECT_RATIO = ARTBOARD_WIDTH / ARTBOARD_HEIGHT;

  private static final double MINIMUM_WIDTH = 25;
  private static final double MINIMUM_HEIGHT = MINIMUM_WIDTH / ASPECT_RATIO;

  private static final double MAXIMUM_WIDTH = 800;

  // needed for StyleableProperties
  private static final StyleablePropertyFactory<PopulationControl> FACTORY = new StyleablePropertyFactory<>(Region.getClassCssMetaData());

  @Override
  public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
    return FACTORY.getCssMetaData();
  }

  private static final CssMetaData<PopulationControl, javafx.scene.paint.Color> BASE_COLOR_META_DATA = FACTORY.createColorCssMetaData("-base-color", s -> s.baseColor);

  private final StyleableObjectProperty<javafx.scene.paint.Color> baseColor = new SimpleStyleableObjectProperty<javafx.scene.paint.Color>(BASE_COLOR_META_DATA, this, "baseColor") {
    @Override
    protected void invalidated() {
      setStyle(BASE_COLOR_META_DATA.getProperty() + ": " + (getBaseColor()).toString().replace("0x", "#") + ";");
      applyCss();
    }
  };

  private SVGPath svgPath;

  // needed for resizing
  private Pane drawingPane;

  public PopulationControl() {
    initializeSelf();
    initializeParts();
    layoutParts();
  }

  private void initializeSelf() {
    // load stylesheets
    String fonts = getClass().getResource("fonts.css").toExternalForm();
    getStylesheets().add(fonts);

    String stylesheet = getClass().getResource("populationStyles.css").toExternalForm();
    getStylesheets().add(stylesheet);

    getStyleClass().add("simpleControl");
  }

  private void initializeParts() {
    //todo initialize all parts
    double center = ARTBOARD_WIDTH * 0.5;
    this.setPadding(new Insets(0));

    svgPath = new SVGPath();
    svgPath.setContent("M519.9,339.5c0,2.8,0,5.7,0,8.5c0,0.8-0.2,1.1-1,1c-0.2,0-0.5,0-0.8,0c-120,0-240.1,0-360.1,0c-1.4,0-1.8-0.3-1.7-1.7 c0.1-4.1,0-8.2,0-12.3c0.5-4.1,0.8-8.3,1.4-12.4c1.8-13.1,5.2-25.8,11.5-37.5c5-9.3,11.2-17.5,20.1-23.4 c10.5-6.9,21.7-12.4,33.4-17.2c15.6-6.4,31.7-11.3,47.6-16.7c6.5-2.2,13-4.5,19-7.9c2.5-1.4,4.5-3.4,5.9-5.9 c2.6-4.7,3.6-9.8,4.2-15c0.1-1-0.3-1.8-0.9-2.6c-5.7-7.6-10.5-15.7-14.5-24.3c-14.8-32.4-22.6-66.1-19.3-101.9 c0.9-10.2,3.7-20,8.7-29.1c7.9-14.2,20.3-22.3,35.7-26.5c5.7-1.5,11.5-2.2,17.3-2.9c6.9,0,13.9,0,20.8,0c4.7,0.6,9.4,1.1,14,2.1 c23.6,5.2,38.8,19.2,45.1,42.7c3.8,14.1,4,28.5,2.8,42.9c-1,12.6-3.3,25.1-6.6,37.3c-5.4,19.7-12.8,38.5-24.4,55.4 c-1.5,2.2-1.9,4.3-1.6,6.7c0.2,1.6,0.4,3.2,0.7,4.9c1.5,8.7,5.3,15.7,14.2,18.9c0.7,0.3,1.4,0.6,2.1,0.9c10.7,4.7,22,7.9,33.1,11.7 c17.7,6.1,35.1,12.8,51.5,22c11.6,6.5,21.2,14.8,27.8,26.4c7.7,13.6,11.3,28.5,13,43.8C519.3,331.6,519.6,335.5,519.9,339.5z");
    svgPath.getStyleClass().add("drawingFigure");

    // always needed
    drawingPane = new Pane();
    drawingPane.getStyleClass().add("drawingPane");
    drawingPane.setMaxSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
    drawingPane.setMinSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
    drawingPane.setPrefSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
  }

  private void layoutParts() {
    drawingPane.getChildren().addAll(svgPath);
    getChildren().add(drawingPane);
  }

  //resize by scaling
  @Override
  protected void layoutChildren() {
    super.layoutChildren();
    resize();
  }

  private void resize() {
    Insets padding = getPadding();
    double availableWidth = getWidth() - padding.getLeft() - padding.getRight();
    double availableHeight = getHeight() - padding.getTop() - padding.getBottom();

    double width = Math.max(Math.min(Math.min(availableWidth, availableHeight * ASPECT_RATIO), MAXIMUM_WIDTH), MINIMUM_WIDTH);

    double scalingFactor = width / ARTBOARD_WIDTH;

    if (availableWidth > 0 && availableHeight > 0) {
      drawingPane.relocate((getWidth() - ARTBOARD_WIDTH) * 0.5, (getHeight() - ARTBOARD_HEIGHT) * 0.5);
      drawingPane.setScaleX(scalingFactor);
      drawingPane.setScaleY(scalingFactor);
    }
  }

  // compute sizes

  @Override
  protected double computeMinWidth(double height) {
    Insets padding = getPadding();
    double horizontalPadding = padding.getLeft() + padding.getRight();

    return MINIMUM_WIDTH + horizontalPadding;
  }

  @Override
  protected double computeMinHeight(double width) {
    Insets padding = getPadding();
    double verticalPadding = padding.getTop() + padding.getBottom();

    return MINIMUM_HEIGHT + verticalPadding;
  }

  @Override
  protected double computePrefWidth(double height) {
    Insets padding = getPadding();
    double horizontalPadding = padding.getLeft() + padding.getRight();

    return ARTBOARD_WIDTH + horizontalPadding;
  }

  @Override
  protected double computePrefHeight(double width) {
    Insets padding = getPadding();
    double verticalPadding = padding.getTop() + padding.getBottom();

    return ARTBOARD_HEIGHT + verticalPadding;
  }

  // all getter and setter

  public static StyleablePropertyFactory<PopulationControl> getFACTORY() {
    return FACTORY;
  }

  public static CssMetaData<PopulationControl, javafx.scene.paint.Color> getBaseColorMetaData() {
    return BASE_COLOR_META_DATA;
  }

  public javafx.scene.paint.Color getBaseColor() {
    return baseColor.get();
  }

  public StyleableObjectProperty<javafx.scene.paint.Color> baseColorProperty() {
    return baseColor;
  }

  public void setBaseColor(javafx.scene.paint.Color baseColor) {
    this.baseColor.set(baseColor);
  }
}
