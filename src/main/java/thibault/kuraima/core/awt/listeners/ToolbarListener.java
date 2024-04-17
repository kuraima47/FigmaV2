package thibault.kuraima.core.awt.listeners;


import thibault.kuraima.core.awt.components.app.DrawingPanel;
import thibault.kuraima.core.awt.components.app.Toolbar;
import thibault.kuraima.core.awt.components.buttons.ShapeButton;
import thibault.kuraima.core.components.Shape;
import thibault.kuraima.core.utils.AbstractFactory.ShapeFactory;
import thibault.kuraima.core.utils.Command.AddToolbarCommand;

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
        AddToolbarCommand c = new AddToolbarCommand();
        c.setToolbar(toolbar);
        c.setPanel(panel);
        panel._app.execute(c);
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
