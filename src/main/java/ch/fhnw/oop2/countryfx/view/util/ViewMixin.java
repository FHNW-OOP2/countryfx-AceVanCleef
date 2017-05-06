package ch.fhnw.oop2.countryfx.view.util;

/**
 * Interface methods are always public
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

    default  void addEventHandlers() {
    }

    default void addValueChangedListeners() {
    }

    default void setupBindings() {
    }

}
