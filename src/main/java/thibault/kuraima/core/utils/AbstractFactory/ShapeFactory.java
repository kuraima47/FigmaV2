package thibault.kuraima.core.utils.AbstractFactory;

import thibault.kuraima.core.components.Rectangle;
import thibault.kuraima.core.components.Shape;

public interface ShapeFactory {
    Rectangle createRectangle(double posX, double posY, double height, double width);
    Shape createShape(Shape shape);
}
