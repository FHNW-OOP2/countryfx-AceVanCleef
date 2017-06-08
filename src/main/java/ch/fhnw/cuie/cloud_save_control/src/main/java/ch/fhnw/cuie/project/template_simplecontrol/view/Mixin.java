package ch.fhnw.cuie.cloud_save_control.src.main.java.ch.fhnw.cuie.project.template_simplecontrol.view;

/**
 * Created by Stefan on 02.04.2017.
 */
public interface Mixin {

    default void init() {
        initializeSelf();
        initializeParts();
        layoutParts();
        addEventHandlers();
        addValueChangedListeners();
        setupBindings();
    }

    default void initializeSelf() {
    }

    void initializeParts();

    void layoutParts();

    default void addEventHandlers() {
    }

    default void addValueChangedListeners() {
    }

    default void setupBindings() {
    }
}
