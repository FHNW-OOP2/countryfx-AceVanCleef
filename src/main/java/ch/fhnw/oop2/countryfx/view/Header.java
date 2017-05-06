package ch.fhnw.oop2.countryfx.view;

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

    private Button saveButton;
    private Button plusButton;
    private Button removeButton;
    private Button undoButton;
    private Button redoButton;
    private Button languageDEButton;
    private Button languageUKButton;

    private final RootPM pm;

    public Header(RootPM pm){
        this.pm = pm;
        init();
    }

    @Override
    public void initializeSelf() {
        getStyleClass().add("header");
    }

    @Override
    public void initializeParts() {
        saveButton = new Button(SAVE_ICON);
        saveButton.getStyleClass().add("fontawesome");

        plusButton = new Button(PLUS_ICON);
        plusButton.getStyleClass().add("fontawesome");

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

        getItems().addAll(saveButton, plusButton, removeButton, undoButton, redoButton, spacer, languageDEButton, languageUKButton);
    }

}
