package ch.fhnw.oop2.countryfx.view.util;

/**
 * Created by Degonas on 29.04.2017.
 */
public interface ViewMixin {

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
