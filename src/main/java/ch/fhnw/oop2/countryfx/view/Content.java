package ch.fhnw.oop2.countryfx.view;

import ch.fhnw.oop2.countryfx.presentationmodel.CountryPM;
import ch.fhnw.oop2.countryfx.presentationmodel.RootPM;
import ch.fhnw.oop2.countryfx.view.util.ViewMixin;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

// Lessons learned:
// intermediatePane.getchildren().addAll(countryOverview, countryEditor) necessary??? Yes


/**
 * Created by Degonas on 29.04.2017.
 *
 * Reasonable SplitPane tutorial:
 * http://fxexperience.com/2011/06/splitpane-in-javafx-2-0/
 *
 */
public class Content extends SplitPane implements ViewMixin{

    private RootPM pm;

    private CountryList countryList;
    private CountryOverview countryOverview;
    private CountryEditor countryEditor;

    private VBox infoContainer;
    private ScrollPane editorScroller;

    public Content(RootPM pm){
        this.pm = pm;
        init();         //<--- do NOT forget this!
    }

    @Override
    public void initializeSelf() {
        this.setOrientation(Orientation.HORIZONTAL);
        this.setDividerPositions(0.3);
    }

    @Override
    public void initializeParts() {
        countryList = new CountryList(pm);

        countryOverview = new CountryOverview(pm);
        countryEditor = new CountryEditor(pm);

        //intermediary pane
        infoContainer = new VBox();
        VBox.setVgrow(countryOverview, Priority.ALWAYS);
        VBox.setVgrow(countryEditor, Priority.ALWAYS);

        //intermediary scrollpane
        editorScroller = new ScrollPane();
    }

    @Override
    public void layoutParts() {
        editorScroller.setContent(countryEditor);
        editorScroller.setFitToWidth(true);
        editorScroller.setFitToHeight(true);
        infoContainer.getChildren().addAll(countryOverview, editorScroller);
        this.getItems().addAll(countryList, infoContainer);
    }

    @Override
    public void setupBindings() {
        //Begrenzt die Maximalgrösse der linken Splitpaneseite auf die 50% (optional)
        countryList.maxWidthProperty().bind(this.widthProperty().multiply(0.5));
    }

}
