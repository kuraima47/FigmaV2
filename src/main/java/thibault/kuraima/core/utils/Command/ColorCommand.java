package thibault.kuraima.core.utils.Command;

import thibault.kuraima.core.awt.components.app.DrawingPanel;
import thibault.kuraima.core.awt.components.shapes.ShapeAwt;

import java.awt.*;

public class ColorCommand implements Command{

    Color color;
    DrawingPanel panel;

    @Override
    public String getName() {
        return "ColorCommand -> " + color.toString();
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setPanel(DrawingPanel panel) {
        this.panel = panel;
    }

    @Override
    public void execute() {
        panel.getSelectedShape().setColor(color);
        panel.repaint();
    }
}
