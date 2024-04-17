package thibault.kuraima.core.utils.Command;

import thibault.kuraima.core.awt.components.app.DrawingPanel;
import thibault.kuraima.core.awt.components.shapes.ShapeAwt;

import java.awt.geom.Point2D;

public class SizeCommand implements Command{

    private Point2D size;

    private DrawingPanel panel;

    @Override
    public String getName() {
        return "SizeCommand : [" + size.getX() + ", " + size.getY()+"]";
    }


    public void setSize(Point2D size) {
        this.size = size;
    }

    public void setPanel(DrawingPanel panel) {
        this.panel = panel;
    }

    @Override
    public void execute() {
        panel.getSelectedShape().size(size);
        panel.repaint();
    }
}
