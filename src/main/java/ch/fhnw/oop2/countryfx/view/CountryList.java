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
public class CountryList<CountryPM> extends ListView<CountryPM> implements ViewMixin{

    private RootPM pm;

    private final ListProperty<CountryPM> listProperty = new SimpleListProperty<>();
    private ObservableList<CountryPM> countries = FXCollections.observableArrayList();


    public CountryList(RootPM pm){
 /*
        names.add((String) "01");
        names.add((String) "02");
        names.add((String) "03");
        names.add((String) "04");
*/
        this.pm = pm;
        init();
        countries = (ObservableList<CountryPM>) pm.getAllCountries();   //Does not work. How to get pm's allCountries?
    }


    @Override
    public void initializeSelf() {
        //this.setCellFactory(v -> new CountryListCell());
    }

    @Override
    public void initializeParts() {

    }

    @Override
    public void layoutParts() {
        //listProperty.set();
        this.setItems(countries);
    }
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


