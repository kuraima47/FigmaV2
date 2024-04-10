package thibault.kuraima.core.utils.AbstractFactory;

import thibault.kuraima.core.awt.components.shapes.RectangleAwt;
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
    public Shape createShape(Shape shape) {
        return shape.copy();
    }

}
