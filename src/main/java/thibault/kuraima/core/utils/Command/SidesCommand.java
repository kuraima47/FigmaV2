package thibault.kuraima.core.utils.Command;

import thibault.kuraima.core.awt.components.app.DrawingPanel;
import thibault.kuraima.core.awt.components.shapes.PolygonAwt;
import thibault.kuraima.core.awt.components.shapes.ShapeAwt;

public class SidesCommand implements Command{

    private int sides;
    private DrawingPanel panel;

    @Override
    public String getName() {
        return null;
    }


    public void setSides(int sides) {
        this.sides = sides;
    }

    public void setPanel(DrawingPanel panel) {
        this.panel = panel;
    }

    @Override
    public void execute() {
        ((PolygonAwt)panel.getSelectedShape()).setSides(sides);
        panel.repaint();
    }
}
