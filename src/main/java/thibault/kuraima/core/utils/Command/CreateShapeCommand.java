package thibault.kuraima.core.utils.Command;

import thibault.kuraima.core.awt.application.AppAwt;
import thibault.kuraima.core.awt.application.AppContext;
import thibault.kuraima.core.awt.components.shapes.ShapeAwt;

public class CreateShapeCommand implements Command{

    private ShapeAwt shape;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void execute() {
        AppAwt appAwt = AppContext.instance().app();
        appAwt.drawingPanel.addShape(appAwt._factory.createShape(shape));
    }

    public void setShape(ShapeAwt shape) {
        this.shape = shape;
    }
}
