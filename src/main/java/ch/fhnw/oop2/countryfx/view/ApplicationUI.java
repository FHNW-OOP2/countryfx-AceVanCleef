package ch.fhnw.oop2.countryfx.view;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import ch.fhnw.oop2.countryfx.presentationmodel.RootPM;

public class ApplicationUI extends BorderPane {
    private final RootPM model;

    private Header menuBar;
    private Content mainFrame;
    private Footer continentsInfo;

    private Button button;

    public ApplicationUI(RootPM model) {
        this.model = model;
        initializeSelf();
        initializeControls();
        layoutControls();
        setupEventHandlers();
        setupValueChangedListeners();
        setupBindings();
    }

    private void initializeSelf() {
        String stylesheet = getClass().getResource("style.css").toExternalForm();
        getStylesheets().add(stylesheet);
    }

    private void initializeControls() {
        button = new Button();
    }

    private void layoutControls() {
        getChildren().add(button);
    }

    private void setupEventHandlers() {
    }

    private void setupValueChangedListeners() {
    }

    private void setupBindings() {
        button.textProperty().bind(model.greetingProperty());
    }
}
