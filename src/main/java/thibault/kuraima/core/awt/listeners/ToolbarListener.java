package thibault.kuraima.core.awt.listeners;


import thibault.kuraima.core.awt.components.app.Toolbar;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class ToolbarListener implements Listener {

    public Toolbar toolbar;

    public ToolbarListener(Toolbar toolbar) {
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
        System.out.println("Mouse entered");
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
