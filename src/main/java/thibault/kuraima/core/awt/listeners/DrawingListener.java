package thibault.kuraima.core.awt.listeners;

import thibault.kuraima.core.awt.components.app.DrawingPanel;
import thibault.kuraima.core.utils.Command.MoveCommand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;

public class DrawingListener implements Listener {
    private DrawingPanel drawingPanel;

    public DrawingListener(DrawingPanel drawingPanel) {
        this.drawingPanel = drawingPanel;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        drawingPanel._app.requestFocus();
        if (drawingPanel.getSelectedShape() != null){
            drawingPanel.setDraggedShape(true);
        }
        if (drawingPanel.getDraggedShape() != null) {
            Point2D oldPos = drawingPanel.getDraggedShape().position();
            Point2D newPos = new Point(e.getX(), e.getY());
            Point2D diff = new Point2D.Double(newPos.getX() - oldPos.getX(), newPos.getY() - oldPos.getY());
            drawingPanel.getDraggedShape().translate(diff);
            drawingPanel.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        drawingPanel.getDraggedShape();
        if(drawingPanel.getDraggedShape() != null){
            Point2D oldPos = drawingPanel.getDraggedShape().position();
            Point2D newPos = new Point(e.getX(), e.getY());
            Point2D diff = new Point2D.Double(newPos.getX() - oldPos.getX(), newPos.getY() - oldPos.getY());
            drawingPanel.getDraggedShape().translate(diff);
            drawingPanel.repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        drawingPanel._app.requestFocus();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        drawingPanel._app.requestFocus();
        if (drawingPanel.getDraggedShape() != null) {
            drawingPanel.repaint();
        }
        if (drawingPanel.getSelectedShape() != null && !e.isControlDown()) {
            drawingPanel.unselectShape();
        }
        drawingPanel.getShapeAt(e.getX(), e.getY());
        if (drawingPanel.getSelectedShape() != null) {
            drawingPanel.getSelectedShape().setSelected(true);
            if (e.getButton() == MouseEvent.BUTTON3) {
                JPopupMenu popup = drawingPanel.getSelectedShape().getMenu();
                popup.show(drawingPanel, e.getX(), e.getY());
            }
        }
        if (e.getButton() == MouseEvent.BUTTON1) {
            MoveCommand c = new MoveCommand();
            c.setPanel(drawingPanel);
            c.setEventBtn(e.getButton());
            drawingPanel._app.execute(c);
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        drawingPanel._app.requestFocus();
        drawingPanel.repaint();
        MoveCommand c = new MoveCommand();
        c.setPanel(drawingPanel);
        c.setEventBtn(e.getButton());
        drawingPanel._app.execute(c);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        drawingPanel._app.requestFocus();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        drawingPanel._app.requestFocus();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
