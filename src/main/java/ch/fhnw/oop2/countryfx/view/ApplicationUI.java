package ch.fhnw.oop2.countryfx.view;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import ch.fhnw.oop2.countryfx.presentationmodel.PresentationModel;

public class ApplicationUI extends StackPane {
    private final PresentationModel model;

    private Button button;

    public ApplicationUI(PresentationModel model) {
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
