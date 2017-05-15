package ch.fhnw.oop2.countryfx.view;

import ch.fhnw.oop2.countryfx.presentationmodel.CountryPM;
import ch.fhnw.oop2.countryfx.presentationmodel.RootPM;
import ch.fhnw.oop2.countryfx.view.util.ViewMixin;
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

    public CountryList(RootPM pm){
        this.pm = pm;
        init();
    }


    @Override
    public void initializeSelf() {
        this.setCellFactory(v -> new CountryListCell());
    }

    @Override
    public void initializeParts() {

    }

    @Override
    public void layoutParts() {

    }
}

//multiple classes can be within one file, but only one as public.
class CountryListCell<CountryPM> extends ListCell<CountryPM> {


    @Override
    protected void updateItem(CountryPM item, boolean empty) {
        super.updateItem(item, empty);
        setGraphic(null);
        setText(null);
        if (item != null && !empty) {
            setGraphic(new CustomListItem(item));

            //setGraphic(...); <- Kann mit beliebigem JavaFX Node bestückt werden (z.B. GridPane
        }
    }

}

class CustomListItem<CountryPM> extends GridPane implements ViewMixin{

    private static String imagePath = "https://dieterholz.github.io/StaticResources/flags_iso/64/";


    private CountryPM country;

    private TextField countryField;
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


    }

    @Override
    public void layoutParts() {
        add(flag, 0,0,1,2);

    }
}


