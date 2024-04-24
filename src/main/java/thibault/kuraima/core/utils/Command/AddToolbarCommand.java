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
        return "AddToolbarCommand";
    }

    @Override
    public void execute() {
        Shape s = panel.getDraggedShape();
        toolbar.addButton(new ShapeButton(s.name(), panel._app._factory.createShape(s)));
        File file = new File(System.getProperty("user.dir") + "/toolbar.ser");
        BackupCommand backup = new BackupCommand();
        backup.setParams(file.getAbsolutePath(), "Toolbar");
        backup.execute();
        panel.deleteSelectedShape();
    }


    public void setPanel(DrawingPanel panel) {
        this.panel = panel;
    }
}
