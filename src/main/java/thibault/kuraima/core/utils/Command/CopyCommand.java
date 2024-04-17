package thibault.kuraima.core.utils.Command;

import thibault.kuraima.core.awt.components.app.DrawingPanel;

public class CopyCommand implements Command{

    private DrawingPanel panel;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void execute() {
        panel.copySelectedShape();
    }

    public void setPanel(DrawingPanel drawingPanel) {
        this.panel = drawingPanel;
    }
}
