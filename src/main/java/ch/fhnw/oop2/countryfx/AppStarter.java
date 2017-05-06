package ch.fhnw.oop2.countryfx;

import ch.fhnw.oop2.countryfx.service.CountryService;
import ch.fhnw.oop2.countryfx.service.serviceimpl.CountryServiceFileBased;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import ch.fhnw.oop2.countryfx.presentationmodel.RootPM;
import ch.fhnw.oop2.countryfx.view.RootPane;

public class AppStarter extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		CountryService service = new CountryServiceFileBased();

		RootPM pm = new RootPM(service);

		Parent rootPanel = new RootPane(pm);

		Scene scene = new Scene(rootPanel);

		primaryStage.titleProperty().bind(pm.applicationTitleProperty());
		primaryStage.setScene(scene);

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
