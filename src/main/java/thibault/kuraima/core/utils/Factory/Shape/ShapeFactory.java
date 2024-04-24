package thibault.kuraima.core.utils.Factory.Shape;

import thibault.kuraima.core.awt.components.shapes.ShapeAwt;
import thibault.kuraima.core.components.Rectangle;
import thibault.kuraima.core.components.Shape;

import java.io.Serializable;

public interface ShapeFactory extends Serializable {
    Rectangle createRectangle(double posX, double posY, double height, double width);
    ShapeAwt createShape(Shape shape);
}
