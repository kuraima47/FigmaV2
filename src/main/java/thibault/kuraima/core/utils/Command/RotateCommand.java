package thibault.kuraima.core.utils.Command;

import thibault.kuraima.core.awt.components.app.DrawingPanel;
import thibault.kuraima.core.awt.components.shapes.ShapeAwt;

public class RotateCommand implements Command{

    private int angle;
    private DrawingPanel panel;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void execute() {
        panel.getSelectedShape().setRotation(angle);
        panel.repaint();
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public void setPanel(DrawingPanel panel) {
        this.panel = panel;
    }

}
