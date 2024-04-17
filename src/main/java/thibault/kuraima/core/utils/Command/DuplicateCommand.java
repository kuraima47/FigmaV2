package thibault.kuraima.core.utils.Command;

import thibault.kuraima.core.awt.components.app.DrawingPanel;

public class DuplicateCommand implements Command{


    private DrawingPanel panel;

    public void setPanel(DrawingPanel panel) {
        this.panel = panel;
    }

    @Override
    public String getName() {
        return "DuplicateCommand";
    }

    @Override
    public void execute() {
        panel.duplicateSelectedShape();
    }
}
