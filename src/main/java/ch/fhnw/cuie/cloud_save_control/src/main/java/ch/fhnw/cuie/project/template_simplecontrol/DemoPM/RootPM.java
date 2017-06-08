package ch.fhnw.cuie.cloud_save_control.src.main.java.ch.fhnw.cuie.project.template_simplecontrol.DemoPM;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Created by Degonas on 05.06.2017.
 */
public class RootPM {

    //This is a placeholder class for demo cases.

    private final BooleanProperty saveIt = new SimpleBooleanProperty(false);

    public RootPM(){
        AddValueChangeListener();
    }

    public boolean isSaveIt() {
        return saveIt.get();
    }

    public BooleanProperty saveItProperty() {
        return saveIt;
    }

    public void setSaveIt(boolean saveIt) {
        this.saveIt.set(saveIt);
    }

    public void AddValueChangeListener(){
        saveItProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                save();
            }
        });

    }

    public void save(){
        System.out.println("The demo RootPM's save() has been triggered. This has been achieved through binding and value change listeners. Target SimpleBooleanProperty: saveItProperty().");
    }
}
