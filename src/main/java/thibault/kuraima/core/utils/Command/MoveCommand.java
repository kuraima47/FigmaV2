package thibault.kuraima.core.utils.Command;

import thibault.kuraima.core.awt.components.app.DrawingPanel;
import thibault.kuraima.core.awt.components.shapes.ShapeAwt;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class MoveCommand implements Command{


    private DrawingPanel panel;

    private int eventBtn;

    @Override
    public String getName() {
        return "MoveCommand";
    }


    public void setPanel(DrawingPanel panel) {
        this.panel = panel;
    }

    public void setEventBtn(int eventBtn) {
        this.eventBtn = eventBtn;
    }

    @Override
    public void execute() {

        if (eventBtn == MouseEvent.BUTTON1) {
            if (panel.getNewShape() != null) {
                panel.getNewShape().setNewLastPos();
                panel.getNewShape().setNew(false);
                panel.setDraggedShape(false);
                panel.unselectShape();
            }



            if (panel.getSelectedShape() == null) {
                panel.unselectShape();
                throw new RuntimeException("Shape is not moved");
            }


            if (this.compare(panel.getSelectedShape().position(), panel.getSelectedShape().getLastPos())) {
                throw new RuntimeException("Shape is not moved");
            }

            if (panel.getDraggedShape() != null && panel.getDraggedShape().isDragged()) {
                panel.getSelectedShape().setNewLastPos();
                panel.unselectShape();
            }
        }else if (eventBtn == MouseEvent.BUTTON3) {
            if (panel.getDraggedShape() != null && !panel.getDraggedShape().isNew()) {
                panel.getSelectedShape().setNewLastPos();
                panel.unselectShape();
            }
        }
        panel.repaint();
    }

    private boolean compare(Point2D position, Point2D lastPos) {
        return position.getX() == lastPos.getX() && position.getY() == lastPos.getY();
    }
}
