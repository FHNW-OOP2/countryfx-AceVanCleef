package ch.fhnw.cuie.cloud_save_control.src.main.java.ch.fhnw.cuie.project.template_simplecontrol.helpers;

import java.io.InputStream;

import afester.javafx.svg.SvgLoader;
import javafx.scene.Group;

/**
 * Created by degonas on 02.04.2017.
 */
public class SvgLoaderLib {


    public Group getSVGAsGroupFrom(String filename){
        // load the svg file
        InputStream svgFile =
                getClass().getResourceAsStream(filename);
        SvgLoader loader = new SvgLoader();
        Group svgImage = loader.loadSvg(svgFile);

        // Scale the image and wrap it in a Group to make the button
        // properly scale to the size of the image
        svgImage.setScaleX(0.5);
        svgImage.setScaleY(0.5);
        Group graphic = new Group(svgImage);

        return graphic;
    }
}
