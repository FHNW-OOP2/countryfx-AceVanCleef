package ch.fhnw.oop2.countryfx.view;

import ch.fhnw.oop2.countryfx.view.util.ViewMixin;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import ch.fhnw.oop2.countryfx.presentationmodel.RootPM;


public class RootPane extends BorderPane implements ViewMixin{
    private final RootPM pm;

    private Header menuBar;
    //private Content mainFrame;
    //private Footer continentsInfo;


    public RootPane(RootPM model) {
        this.pm = model;
        init();
    }

    @Override
    public void initializeSelf() {
        String stylesheet = getClass().getResource("style.css").toExternalForm();
        getStylesheets().add(stylesheet);

        String fonts = getClass().getResource("fonts.css").toExternalForm();
        getStylesheets().add(fonts);
    }

    @Override
    public void initializeParts() {
        menuBar = new Header(pm);
    }

    @Override
    public void layoutParts() {
        setTop(menuBar);
    }


}
