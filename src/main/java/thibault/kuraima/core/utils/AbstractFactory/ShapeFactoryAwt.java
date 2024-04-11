package thibault.kuraima.core.utils.AbstractFactory;

import thibault.kuraima.core.awt.components.shapes.RectangleAwt;
import thibault.kuraima.core.awt.components.shapes.ShapeAwt;
import thibault.kuraima.core.components.Rectangle;
import thibault.kuraima.core.components.Shape;

import java.awt.geom.Point2D;

public class ShapeFactoryAwt implements ShapeFactory {

    public ShapeFactoryAwt() {
    }

    @Override
    public Rectangle createRectangle(double posX, double posY, double height, double width) {
        return new RectangleAwt(new Point2D.Double(posX, posY), new Point2D.Double(width, height));
    }

    @Override
    public ShapeAwt createShape(Shape shape) {
        ShapeAwt s = (ShapeAwt) shape.copy();
        s.setNew(true);
        return s;
    }

}
