package ch.fhnw.cuie.cloud_save_control.src.main.java.ch.fhnw.cuie.project.template_simplecontrol;

import ch.fhnw.cuie.cloud_save_control.src.main.java.ch.fhnw.cuie.project.template_simplecontrol.DemoPM.RootPM;
import ch.fhnw.cuie.cloud_save_control.src.main.java.ch.fhnw.cuie.project.template_simplecontrol.view.DemoRootPane.DemoPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * Created by Stefan on 02.04.2017.
 */
public class CloudStarter extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        RootPM pm = new RootPM();
        //When implementing our Custom Control to your program, please remember to readjust import paths to your RootPM.

        //You also might want to Refractor the Class name "RootPM" to your name of choice.

        //In our CloudControl, a method names pm.save() will be called when the user intends to save. Either implement a .save() method in your RootPM
        //or rename our .save() invocations to pm.<yourSaveMethodName>();

        Region rootPane = new DemoPane(pm);

        Scene scene = new Scene(rootPane);

        primaryStage.setTitle("Cloudy Save Button");
        primaryStage.setScene(scene);
        //primaryStage.setWidth(1000);
        //primaryStage.setHeight(1000);

        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}