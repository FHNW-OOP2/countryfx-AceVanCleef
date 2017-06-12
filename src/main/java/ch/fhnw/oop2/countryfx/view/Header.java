package ch.fhnw.oop2.countryfx.view;

import ch.fhnw.cuie.cloud_save_control.src.main.java.ch.fhnw.cuie.project.template_simplecontrol.helpers.Orientation;
import ch.fhnw.cuie.cloud_save_control.src.main.java.ch.fhnw.cuie.project.template_simplecontrol.view.CloudRootPane;
import ch.fhnw.oop2.countryfx.presentationmodel.RootPM;
import ch.fhnw.oop2.countryfx.view.util.ViewMixin;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;


/**
 * Created by Degonas on 29.04.2017.
 */
public class Header extends ToolBar implements ViewMixin{
    private static final String SAVE_ICON   = "\uf0c7";
    private static final String PLUS_ICON   = "\uf067";
    private static final String REMOVE_ICON = "\uf00d";
    private static final String UNDO_ICON   = "\uf0e2";
    private static final String REDO_ICON   = "\uf01e";
    private static final String CH_ICON     = "\ue001";
    private static final String UK_ICON     = "\ue000";

    //private Button saveButton;
    private Button createButton;
    private Button removeButton;
    private Button undoButton;
    private Button redoButton;
    private Button languageDEButton;
    private Button languageUKButton;

    /* CUIE custom control (Cloud Save Button */
    private CloudRootPane cloudControl;


    private final RootPM pm;

    public Header(RootPM pm){
        this.pm = pm;
        init();
    }

    @Override
    public void initializeSelf() {
        getStyleClass().add("header");
        setId("header");
    }

    @Override
    public void initializeParts() {
        //cuie custom control "cloud save button"
        cloudControl = CloudRootPane.createCloudControlWithOrientationOf(Orientation.HORIZONTAL);
        cloudControl.getStyleClass().add("cloudControl");

        //saveButton = new Button(SAVE_ICON);
        //saveButton.getStyleClass().add("fontawesome");

        createButton = new Button(PLUS_ICON);
        createButton.getStyleClass().add("fontawesome");

        redoButton = new Button(REDO_ICON);
        redoButton.getStyleClass().add("fontawesome");

        undoButton = new Button(UNDO_ICON);
        undoButton.getStyleClass().add("fontawesome");

        removeButton = new Button(REMOVE_ICON);
        removeButton.getStyleClass().add("fontawesome");

        languageDEButton = new Button(CH_ICON);
        languageDEButton.getStyleClass().add("flaticon");

        languageUKButton = new Button(UK_ICON);
        languageUKButton.getStyleClass().add("flaticon");
    }

    @Override
    public void layoutParts() {
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        //getItems().addAll(saveButton, createButton, removeButton, undoButton, redoButton, spacer, languageDEButton, languageUKButton);
        getItems().addAll(cloudControl, createButton, removeButton, undoButton, redoButton, spacer, languageDEButton, languageUKButton);
    }

    @Override
    public void addEventHandlers() {
        createButton.setOnAction(event -> pm.createNewCountry());
        removeButton.setOnAction(event -> pm.removeSelectedCountry());
        //saveButton.setOnAction(event -> pm.saveToFile());
    }

    @Override
    public void addValueChangedListeners() {
        cloudControl.saveItProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                pm.saveToFile();
            }
        });
    }
}
