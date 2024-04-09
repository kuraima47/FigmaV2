package thibault.kuraima.core.awt.listeners;

import thibault.kuraima.core.awt.components.app.DrawingPanel;

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
        drawingPanel.requestFocus();
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
        drawingPanel.requestFocus();
        if (drawingPanel.getNewShape() != null) {
            Point2D oldPos = drawingPanel.getDraggedShape().position();
            Point2D newPos = new Point(e.getX(), e.getY());
            Point2D diff = new Point2D.Double(newPos.getX() - oldPos.getX(), newPos.getY() - oldPos.getY());
            drawingPanel.getNewShape().translate(diff);
            drawingPanel.repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        drawingPanel.requestFocus();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        drawingPanel.requestFocus();
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
                JPopupMenu popup = drawingPanel.getSelectedShape().getMenu(drawingPanel);
                popup.show(drawingPanel, e.getX(), e.getY());
            }
        }
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (drawingPanel.getNewShape() != null) {
                drawingPanel.getNewShape().setNew(false);
                drawingPanel.setDraggedShape(false);
                drawingPanel.unselectShape();
            }
            if (drawingPanel.getSelectedShape() == null) {
                drawingPanel.unselectShape();
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        drawingPanel.requestFocus();
        drawingPanel.repaint();
        if (e.getButton() == MouseEvent.BUTTON3) {
            if (drawingPanel.getDraggedShape() != null && !drawingPanel.getDraggedShape().isNew()) {
                drawingPanel.unselectShape();
            }
        }else if (e.getButton() == MouseEvent.BUTTON1) {
            if (drawingPanel.getDraggedShape() != null && drawingPanel.getDraggedShape().isDragged()) {
                drawingPanel.unselectShape();
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        drawingPanel.requestFocus();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        drawingPanel.requestFocus();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_G && e.isControlDown()){
            drawingPanel.groupSelectedShapes();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
