package thibault.kuraima.core.utils.Command;

import thibault.kuraima.core.awt.components.app.DrawingPanel;
import thibault.kuraima.core.awt.components.shapes.ShapeAwt;

import java.awt.geom.Point2D;

public class RoundCommand implements Command{

    private Point2D roundAngle;

    private DrawingPanel panel;

    public void setRoundAngle(Point2D roundAngle) {
        this.roundAngle = roundAngle;
    }

    public void setPanel(DrawingPanel panel) {
        this.panel = panel;
    }

    @Override
    public String getName() {
        return "RoundCommand : [" + roundAngle.getX() + ", " + roundAngle.getY()+"]";
    }

    @Override
    public void execute() {
        panel.getSelectedShape().setRound(roundAngle);
        panel.repaint();
    }
}
