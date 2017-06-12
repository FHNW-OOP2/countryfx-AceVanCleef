package ch.fhnw.cuie.population_simplecontrol;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.StackPane;

public class BackgroundPane extends StackPane {

  private StringProperty backgroundUrl = new SimpleStringProperty("http://www.kohaido.de/wp-content/uploads/2013/02/flag-germany.png");
  private FigurePane figurePane;

  public BackgroundPane() {
    initializeSelf();
    layoutControls();
    setupListeners();
  }

  private void initializeSelf() {
    String stylesheet = getClass().getResource("populationStyles.css").toExternalForm();
    getStylesheets().add(stylesheet);
  }

  private void layoutControls() {
    this.getStyleClass().add("backgroundPane");
    figurePane = new FigurePane();
    // enable logarithmic scaling
    figurePane.setLogScaling(true);
    setBackground();
    this.getChildren().addAll(figurePane);
  }

  private void setupListeners() {
    backgroundUrlProperty().addListener(observable -> setBackground());
  }

  private void setBackground() {
//    this.setStyle("-fx-background-image: url('" + getBackgroundUrl() + "')");
    String backgroundUrl = getBackgroundUrl();
    if(backgroundUrl != null && !backgroundUrl.isEmpty()) {
      this.setStyle("-fx-background-image: url('" + "https://dieterholz.github.io/StaticResources/flags_iso/550/" + backgroundUrl.toLowerCase() + ".png')");
    }
  }

  // All getter and setter

  public String getBackgroundUrl() {
    return backgroundUrl.get();
  }

  public StringProperty backgroundUrlProperty() {
    return backgroundUrl;
  }

  public void setBackgroundUrl(String backgroundUrl) {
    this.backgroundUrl.set(backgroundUrl);
  }

  public FigurePane getFigurePane() {
    return figurePane;
  }

}
