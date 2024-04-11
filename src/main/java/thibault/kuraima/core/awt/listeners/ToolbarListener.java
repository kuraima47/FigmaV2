package thibault.kuraima.core.awt.listeners;


import thibault.kuraima.core.awt.application.AppAwt;
import thibault.kuraima.core.awt.components.app.CustomActionListener;
import thibault.kuraima.core.awt.components.app.DrawingPanel;
import thibault.kuraima.core.awt.components.app.Toolbar;
import thibault.kuraima.core.awt.components.buttons.ShapeButton;
import thibault.kuraima.core.awt.components.shapes.ShapeAwt;
import thibault.kuraima.core.components.Shape;
import thibault.kuraima.core.utils.AbstractFactory.ShapeFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class ToolbarListener implements Listener {

    public Toolbar toolbar;
    public DrawingPanel panel;

    ShapeFactory factory;

    public ToolbarListener(DrawingPanel panel, ShapeFactory factory) {
        this.panel = panel;
        this.factory = factory;
    }

    public void setToolBar(Toolbar toolbar){
        this.toolbar = toolbar;
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

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Shape s = panel.getDraggedShape();
        if (s != null && !s.isNew()) {
            toolbar.addButton(new ShapeButton(s.name(), (ShapeAwt) factory.createShape(s), (AppAwt) panel._app));
            File file = new File(System.getProperty("user.dir") + "/toolbar.ser");
            try {
                panel._app.backup(file.getAbsolutePath(), "Toolbar");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            panel.deleteSelectedShape();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
