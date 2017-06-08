package ch.fhnw.cuie.cloud_save_control.src.main.java.ch.fhnw.cuie.project.template_simplecontrol.view.DemoRootPane;


import ch.fhnw.cuie.cloud_save_control.src.main.java.ch.fhnw.cuie.project.template_simplecontrol.DemoPM.RootPM;
import ch.fhnw.cuie.cloud_save_control.src.main.java.ch.fhnw.cuie.project.template_simplecontrol.helpers.Orientation;
import ch.fhnw.cuie.cloud_save_control.src.main.java.ch.fhnw.cuie.project.template_simplecontrol.view.CloudRootPane;
import ch.fhnw.cuie.cloud_save_control.src.main.java.ch.fhnw.cuie.project.template_simplecontrol.view.Mixin;

import javafx.scene.layout.StackPane;

/**
 * Created by degonas on 07.06.2017.
 */
public class DemoPane extends StackPane implements Mixin{

    private RootPM pm;
    CloudRootPane cloudRootPane;

    public DemoPane(RootPM pm){
        this.pm = pm;
        init();
    }

    @Override
    public void initializeParts() {
        cloudRootPane = CloudRootPane.createCloudControlWithOrientationOf(Orientation.VERTICAL);
    }

    @Override
    public void layoutParts() {
        getChildren().addAll(cloudRootPane);
    }

    @Override
    public void setupBindings() {
        pm.saveItProperty().bind(cloudRootPane.saveItProperty());
    }
}
