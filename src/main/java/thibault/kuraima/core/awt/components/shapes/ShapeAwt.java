package thibault.kuraima.core.awt.components.shapes;

import thibault.kuraima.core.awt.components.app.DrawingPanel;
import thibault.kuraima.core.components.Shape;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public interface ShapeAwt extends Shape {
    void draw(Graphics2D g);

    ArrayList<JLabel> getLabels();

    JPopupMenu getMenu();

    void unSelect();

}
