package thibault.kuraima.core.utils.AbstractFactory;

import thibault.kuraima.core.components.Rectangle;
import thibault.kuraima.core.components.Shape;

import java.io.Serializable;

public interface ShapeFactory extends Serializable {
    Rectangle createRectangle(double posX, double posY, double height, double width);
    Shape createShape(Shape shape);
}
