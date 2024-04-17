package thibault.kuraima.core.utils.Command;

import thibault.kuraima.core.awt.components.app.DrawingPanel;

public class UngroupCommand implements Command{

    private DrawingPanel panel;

    @Override
    public String getName() {
        return null;
    }

    public void setPanel(DrawingPanel panel) {
        this.panel = panel;
    }

    @Override
    public void execute() {
        panel.unGroupSelectedShapes();
    }

}
