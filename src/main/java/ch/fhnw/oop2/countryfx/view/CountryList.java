package ch.fhnw.oop2.countryfx.view;

import ch.fhnw.oop2.countryfx.presentationmodel.CountryPM;
import ch.fhnw.oop2.countryfx.presentationmodel.RootPM;
import ch.fhnw.oop2.countryfx.view.util.ViewMixin;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.util.Callback;

/**
 * Created by Degonas on 29.04.2017.
 */
public class CountryList extends ListView<CountryPM> implements ViewMixin{

    //#lessonLearned:
    //Before: CountryList<CountryPM> extends ListView<CountryPM>
    //After: CountryList extends ListView<CountryPM>
    //Issue was: CountryList<CountryPM> ist überflüssig, da es auch z.B. als CountryList<T> geschrieben werden kann
    //Wenn in der Klassendeklaration (class CountryList) ein Typparameter eingeführt wird , dann kann er heissen, wie er will (z.B. T).
    //Erst wenn eine Instanz von CountryList gemäss "CountryList<CountryPM> list = new CountryList<>()" erzeugt wird, ist die
    //CountryList auf Elemente vom Typ CountryPM begrenzt.
    //In other words: Der Name im Generics hat nichts mit der CountryPM Klasse zu tun, bei der ListView<> jedoch schon, da CountryList von der LiestView erbt
    private RootPM pm;

    public CountryList(RootPM pm){
        this.pm = pm;
        init();
    }

    @Override
    public void initializeSelf() {
        setItems(pm.getAllCountries());
        this.setCellFactory(v -> new CountryListCell());
    }

    @Override
    public void initializeParts() {}

    @Override
    public void layoutParts() {}
}

/************************** List Cell *********************************/

//multiple classes can be within one file, but only one as public.
class CountryListCell<CountryPM> extends ListCell<CountryPM> {

    @Override
    protected void updateItem(CountryPM item, boolean empty) {
        super.updateItem(item, empty);
        setGraphic(null);
        setText(null);
        if (item != null && !empty) {
            //setGraphic(new CustomListItem(item));
            setGraphic(new TextField("Hallo"));
            //setGraphic(...); <- Kann mit beliebigem JavaFX Node bestückt werden (z.B. GridPane
        }
    }

}

/************************** List Item *********************************/

class CustomListItem<CountryPM> extends GridPane implements ViewMixin{

    private static String imagePath = "https://dieterholz.github.io/StaticResources/flags_iso/64/";
    private CountryPM country;

    private TextField countryNameField;
    private TextField capitalField;

    private ImageView flag;
    private Image img;

    public CustomListItem(CountryPM country){
        this.country = country;
        init();
        this.setGridLinesVisible(true);
    }

    @Override
    public void initializeParts() {
        img = new Image(imagePath + "ch.png");
        flag = new ImageView(img);
        countryNameField = new TextField();
        capitalField = new TextField();
    }

    @Override
    public void layoutParts() {
        add(flag, 0,0,1,2);
        add(countryNameField, 1, 0, 0, 0);
        add(capitalField, 1, 1, 0, 0);
    }

    @Override
    public void setupBindings() {
        //countryNameField.textProperty().bindBidirectional(country.nameProperty()); //it can't find nameProperty(). Why?
    }
}


