package ch.fhnw.cuie.cloud_save_control.src.main.java.ch.fhnw.cuie.project.template_simplecontrol.view;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * Created by Stefan on 02.04.2017.
 */
public class TargetZone extends StackPane implements Mixin{

    private Circle target;
    private Rectangle skybox;

    public TargetZone(){
        init();
    }

    @Override
    public void initializeSelf() {

    }

    @Override
    public void initializeParts() {
        skybox = new Rectangle(100, 100, Color.SKYBLUE);
        target = new Circle(250, 200, 30,Color.BLACK);

    }

    @Override
    public void layoutParts() {
        this.getChildren().addAll(skybox, target);
        setAlignment(skybox, Pos.CENTER);
        setAlignment(target, Pos.CENTER);
    }

    @Override
    public void addEventHandlers() {

    }

    @Override
    public void addValueChangedListeners() {

    }

    @Override
    public void setupBindings() {

    }
}
