package ch.fhnw.oop2.countryfx.view;

import ch.fhnw.oop2.countryfx.presentationmodel.CountryPM;
import ch.fhnw.oop2.countryfx.presentationmodel.RootPM;
import ch.fhnw.oop2.countryfx.view.util.ViewMixin;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
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

        //css
        setId("countryList");
    }

    @Override
    public void initializeParts() {}

    @Override
    public void layoutParts() {}

    @Override
    public void addValueChangedListeners() {
        getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            pm.setSelectedCountryId(newValue.getId());         // damit ist keine synchrone Nummerierung zwischen der Liste und dem File nötig
            // pm.selectedCountryIdProperty()                  // im gegensatz zum Binding, bei dem die fortlaufende Nummerierung zwingend wird => BAD!
            //  .bind(this.getSelectionModel()
            //  .selectedIndexProperty().add(1));
        });
    }

    @Override
    public void setupBindings() {
        /* #LessonLearned:
        *   [A.bind(B)]
        *   To bind a property to another property, call the bind() method which binds values in one direction.
        *   For instance, when property A binds to property B, the change in property B will update property A,
        *   but not the other way around.
        * */
    }

}

/************************** List Cell *********************************/

//multiple classes can be within one file, but only one as public.
class CountryListCell extends ListCell<CountryPM> {

    @Override
    protected void updateItem(CountryPM item, boolean empty) {
        super.updateItem(item, empty);
        setGraphic(null);
        setText(null);
        if (item != null && !empty) {
            setGraphic(new CustomListItem(item));
            //setGraphic(new TextField("Hallo"));
            //setGraphic();
            //setGraphic(...); <- Kann mit beliebigem JavaFX Node bestückt werden (z.B. GridPane
        }
    }

}

/************************** List Item *********************************/

class CustomListItem extends GridPane implements ViewMixin{

    private static String FLAGS_IMGAGE_PATH = "https://dieterholz.github.io/StaticResources/flags_iso/";
    private static String FLAGS_SIZE = "64/";
    private static String IMAGE_FORMAT_PNG = ".png";
    private CountryPM country;

    private TextField countryNameField;
    private TextField capitalField;

    private ImageView flag;
    private Image img;

    public CustomListItem(CountryPM country){
        this.country = country;
        init();
        //this.setGridLinesVisible(true);  //for debugging (makes gridlines visible)
    }


    @Override
    public void initializeParts() {
        img = new Image(getFlagURL(FLAGS_SIZE), true);
        flag = new ImageView(img);
        countryNameField = new TextField();
        capitalField = new TextField();
    }

    @Override
    public void layoutParts() {
        add(flag, 0,0,1,2);
        add(countryNameField, 1, 0);
        add(capitalField, 1, 1);

        GridPane.setHgrow(countryNameField, Priority.ALWAYS); //TextField wächst zusammen mit der Fensterbreite
        GridPane.setHgrow(capitalField, Priority.ALWAYS);
    }

    @Override
    public void setupBindings() {
        countryNameField.textProperty().bindBidirectional(country.nameProperty());
        capitalField.textProperty().bindBidirectional(country.capitalProperty());
    }

    @Override
    public void addValueChangedListeners() {
        country.iso_2Property().addListener((observable, oldValue, newValue) -> {
            img = new Image(getFlagURL(FLAGS_SIZE), true); //true: backgroundloading
            flag.setImage(img);                       // MUST HAVE !! Sonst wird die Flagge in der ListView nicht aktualisiert
        });
    }

    private String getFlagURL(String flagSize){
        if("".equals(flagSize) || flagSize == null){
            flagSize = "24/";                       //fallback size
        }

        String iso_2 = country.getIso_2();
        if ("".equals(iso_2) || iso_2 == null){     // Falls kein iso2 Wert vorhanden, dann Default Wert
            return "http://icons.iconarchive.com/icons/gosquared/flag/64/United-Nations-icon.png";  // bei CreateNewCountry()
        } else {
            iso_2 = iso_2.toLowerCase(); //from "CH" to "ch"
        }

        String url = FLAGS_IMGAGE_PATH +  // "https://dieterholz.github.io/StaticResources/flags_iso/";
                flagSize +          // "128/" oder "64/" usw.
                iso_2 +             // "ch" oder "de" usw.
                IMAGE_FORMAT_PNG;   // ".png"
        return url;
    }
}


