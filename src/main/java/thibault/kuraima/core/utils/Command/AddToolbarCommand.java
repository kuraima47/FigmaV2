package thibault.kuraima.core.utils.Command;

import thibault.kuraima.core.awt.components.app.DrawingPanel;
import thibault.kuraima.core.awt.components.app.Toolbar;
import thibault.kuraima.core.awt.components.buttons.ShapeButton;
import thibault.kuraima.core.components.Shape;

import java.io.File;
import java.io.IOException;

public class AddToolbarCommand implements Command{

    private Toolbar toolbar;
    private DrawingPanel panel;

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void execute() {
        Shape s = panel.getDraggedShape();
        if (s != null && !s.isNew()) {
            toolbar.addButton(new ShapeButton(s.name(), panel._app._factory.createShape(s)));
            File file = new File(System.getProperty("user.dir") + "/toolbar.ser");
            try {
                panel._app.backup(file.getAbsolutePath(), "Toolbar");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            panel.deleteSelectedShape();
        }
    }


    public void setPanel(DrawingPanel panel) {
        this.panel = panel;
    }
}
