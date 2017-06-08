package ch.fhnw.cuie.cloud_save_control.src.main.java.ch.fhnw.cuie.project.template_simplecontrol.view;

import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 * Created by degonas on 04.04.2017.
 */
public class SVGCircle extends Circle {

    private Group svgGraphic;

    /**
     * Creates a new instance of SVGCircle with a specified radius.
     * @param radius the radius of the circle in pixels
     */
    public SVGCircle(double radius, Group svgAsGroup){
        super(radius);
        svgGraphic = svgAsGroup;
    }

    /**
     * Creates a new instance of SVGCircle with a specified radius and fill.
     * @param radius the radius of the circle
     * @param fill determines how to fill the interior of the Circle
     */
    public SVGCircle(double radius, Paint fill, Group svgAsGroup){
        super(radius, fill);
        svgGraphic = svgAsGroup;
    }


    /**
     * Creates a new instance of SVGCircle with a specified position and radius.
     * @param centerX the horizontal position of the center of the circle in pixels
     * @param centerY the vertical position of the center of the circle in pixels
     * @param radius the radius of the circle in pixels
     */
    public SVGCircle(double centerX, double centerY, double radius, Group svgAsGroup) {
        super(centerX, centerY, radius);
        svgGraphic = svgAsGroup;
    }

    /**
     * Creates a new instance of SVGCircle with a specified position, radius and fill.
     * @param centerX the horizontal position of the center of the circle in pixels
     * @param centerY the vertical position of the center of the circle in pixels
     * @param radius the radius of the circle in pixels
     * @param fill determines how to fill the interior of the Circle
     */
    public SVGCircle(double centerX, double centerY, double radius, Paint fill, Group svgAsGroup) {
        super(centerX, centerY, radius, fill);
        svgGraphic = svgAsGroup;
    }

    /**
     * default constructor.
     */
    public SVGCircle(){

    }

    /*******  setters and getters   *******/
    public Group getSvgGraphic() {
        return svgGraphic;
    }

    public void setSvgGraphic(Group svgGraphic) {
        this.svgGraphic = svgGraphic;
    }
}
