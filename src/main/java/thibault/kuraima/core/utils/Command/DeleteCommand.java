package thibault.kuraima.core.utils.Command;

import thibault.kuraima.core.awt.components.app.DrawingPanel;
import thibault.kuraima.core.awt.components.shapes.ShapeAwt;

public class DeleteCommand implements Command {

    private DrawingPanel panel;

    @Override
    public String getName() {
        return "DeleteShapeCommand";
    }

    public void setPanel(DrawingPanel panel) {
        this.panel = panel;
    }

    @Override
    public void execute() {
        panel.deleteSelectedShape();
        panel.repaint();
    }
}
