package thibault.kuraima.core.components;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.Serializable;

public interface Shape extends Cloneable, Serializable {

    String name();

    Point2D size();

    Shape size(Point2D vec);

    Point2D position();

    Shape position(Point2D position);

    Shape translate(Point2D vec);

    boolean contains(int x, int y);

    boolean isNew();

    void setNew(boolean isNew);

    void setSelected(boolean selected);

    boolean isSelected();

    boolean isDragged();

    void setDragged(boolean dragged);

    void setId(int count);

    void setColor(Color color);

    Color getColor();

    double rotation();

    Point2D getRound();

    void setRound(Point2D round);

    void setRotation(double v);

    void setRotationCenter(Point2D center);

    Shape clone();

    Shape copy();
}
