//package ch.fhnw.oop2.countryfx.view;
//
//import ch.fhnw.oop2.countryfx.presentationmodel.CountryPM;
//import ch.fhnw.oop2.countryfx.presentationmodel.RootPM;
//import ch.fhnw.oop2.countryfx.view.util.ViewMixin;
//import javafx.scene.control.ListCell;
//import javafx.scene.control.ListView;
//import javafx.scene.shape.Circle;
//import javafx.util.Callback;
//
///**
// * Created by Degonas on 29.04.2017.
// */
//public class CountryList<CountryPM> extends ListView<CountryPM> implements ViewMixin{
//
//    private RootPM pm;
//
//    public CountryList(RootPM pm){
//        this.pm = pm;
//        init();
//    }
//
//    @Override
//    public void initializeSelf() {
//        this.setCellFactory(v -> new CountryList.CountryListCell());
//        this.setCellFactory(new Callback<ListView<ch.fhnw.oop2.countryfx.presentationmodel.CountryPM>, CountryList.CountryListCell<ch.fhnw.oop2.countryfx.presentationmodel.CountryPM>>() {
//
//    }
//
//    @Override
//    public void initializeParts() {
//
//    }
//
//    @Override
//    public void layoutParts() {
//
//    }
//
//    public static class CountryListCell<CountryPM> extends ListCell<CountryPM> {
//
//        @Override
//        protected void updateItem(CountryPM item, boolean empty) {
//            super.updateItem(item, empty);
//            setGraphic(null);
//            setText(null);
//            if (item != null && !empty) {
//                setGraphic(new Circle(10, 10, 20, item));
//            }
//        }
//
//    }
//}
